package com.hujiayucc.chatnio.android.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.android.bean.MessageSegment;
import com.hujiayucc.chatnio.android.bean.Token;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import static com.hujiayucc.chatnio.android.ChatNio.API;

/**
 * 自定义WS监听器
 */
public class CustomListener extends WebSocketListener implements Listener {
    /** WS */
    protected WebSocket webSocket;
    /** messageStringBuilder */
    public final StringBuilder builder = new StringBuilder();
    /** ws */
    public final Queue<CompletableFuture<MessageSegment>> pendingMessages = new LinkedBlockingQueue<>();
    /** Token信息 */
    public final Token token;

    /**
     * 自定义WS监听器
     * @param token Token信息
     */
    public CustomListener(Token token) {
        this.token = token;
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String data) {
        MessageSegment message = JSONObject.parseObject(data, MessageSegment.class);
        if (message.isEnd()) {
            CompletableFuture<MessageSegment> future = pendingMessages.poll();
            if (future != null) {
                future.complete(message);
            }
        }
        builder.append(message.message);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
        while (!pendingMessages.isEmpty()) {
            CompletableFuture<MessageSegment> future = pendingMessages.poll();
            if (future != null) {
                future.completeExceptionally(throwable);
            }
        }
    }

    @Override
    public CompletableFuture<MessageSegment> sendMessage(String message, String model, boolean enableWeb) {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        webSocket = client.newWebSocket(new Request.Builder().url(getPath()).build(), this);
        webSocket.send(token.toString());
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        pendingMessages.add(futureResponse);
        webSocket.send(body);
        return futureResponse;
    }

    private String getPath() {
        return API.replaceFirst("^http", "ws") + "/chat";
    }

    private String getBody(String message, String model, boolean enableWeb) {
        return new JSONObject()
                .fluentPut("type", "chat")
                .fluentPut("model", model)
                .fluentPut("message", message)
                .fluentPut("web", enableWeb).toJSONString();
    }
}

interface Listener {
    CompletableFuture<MessageSegment> sendMessage(String message, String model, boolean enableWeb);
}
