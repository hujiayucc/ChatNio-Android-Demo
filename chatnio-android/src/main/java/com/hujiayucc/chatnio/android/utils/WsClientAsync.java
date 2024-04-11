package com.hujiayucc.chatnio.android.utils;

import com.alibaba.fastjson.JSONObject;
import com.hujiayucc.chatnio.android.bean.MessageSegment;
import com.hujiayucc.chatnio.android.bean.Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.hujiayucc.chatnio.android.ChatNio.API;

/**
 * WS
 */
public class WsClientAsync {
    /**
     * WS
     */
    protected WebSocket webSocket;
    /**
     * Token信息
     */
    protected Token token;
    /**
     * 自定义监听器
     */
    protected CustomListener listener;

    /**
     * 创建WS连接
     * @param token Token信息
     * @param listener 监听器
     */
    public WsClientAsync(Token token, CustomListener listener) {
        this.token = token;
        this.listener = listener;
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        this.webSocket = client.newWebSocket(new Request.Builder().url(getPath()).build(), listener);
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
     * @return 异步回调 {@link CompletableFuture}
     */
    public CompletableFuture<MessageSegment> sendMessage(String message, String model, boolean enableWeb) {
        String body = getBody(message, model, enableWeb);
        CompletableFuture<MessageSegment> futureResponse = new CompletableFuture<>();
        listener.pendingMessages.add(futureResponse);
        webSocket.send(body);
        return futureResponse;
    }

    private String getPath() {
        return API.replaceFirst("^http", "ws") + "/chat";
    }
}