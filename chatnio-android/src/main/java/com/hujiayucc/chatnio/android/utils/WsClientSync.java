package com.hujiayucc.chatnio.android.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.android.bean.MessageSegment;
import com.hujiayucc.chatnio.android.bean.Token;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.hujiayucc.chatnio.android.ChatNio.API;

/**
 * WS
 */
public class WsClientSync {
    /**
     * WS
     */
    protected WebSocket webSocket;
    /**
     * Token信息
     */
    protected Token token;
    private final Queue<CompletableFuture<MessageSegment>> pendingMessages = new LinkedBlockingQueue<>();
    /**
     * MessageStringBuilder
     */
    public final StringBuilder builder = new StringBuilder();

    private class CustomListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, @NotNull Response response) {
            JSONObject body = new JSONObject().fluentPut("token", token.toString());
            webSocket.send(body.toJSONString());
            super.onOpen(webSocket, response);
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
            super.onMessage(webSocket, data);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
            while (!pendingMessages.isEmpty()) {
                CompletableFuture<MessageSegment> future = pendingMessages.poll();
                if (future != null) {
                    future.completeExceptionally(throwable);
                }
            }
            super.onFailure(webSocket, throwable, response);
        }
    }

    /**
     * 创建WS连接
     * @param token Token信息
     */
    public WsClientSync(Token token) {
        this.token = token;
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        this.webSocket = client.newWebSocket(new Request.Builder().url(getPath()).build(), new CustomListener());
    }
    private String getBody(String message, String model, boolean enableWeb) {
        JSONObject body = new JSONObject()
               .fluentPut("type", "chat")
               .fluentPut("model", model)
               .fluentPut("message", message)
               .fluentPut("web", enableWeb);
        return body.toJSONString();
    }

    /**
     * 发送消息
     * @param message 消息内容
     * @param model 模型
     * @param enableWeb 是否开启WEB
     * @return 同步回调 {@link CompletableFuture}
     */
    public CompletableFuture<MessageSegment> sendMessageAsync(String message, String model, boolean enableWeb) {
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        pendingMessages.add(futureResponse);
        webSocket.send(body);
        return futureResponse;
    }

    private String getPath() {
        return API.replaceFirst("^http", "ws") + "/chat";
    }
}
