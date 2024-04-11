package com.hujiayucc.chatnio.android.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.android.bean.MessageSegment;
import com.hujiayucc.chatnio.android.bean.Token;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义WS监听器
 */
public class CustomListener extends WebSocketListener {
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

    /**
     * 开启连接
     * @param webSocket ws
     */
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
