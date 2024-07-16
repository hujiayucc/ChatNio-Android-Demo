package com.hujiayucc.chatnio.android.utils;

import com.hujiayucc.chatnio.android.bean.MessageSegment;
import com.hujiayucc.chatnio.android.bean.Token;
import java.util.concurrent.CompletableFuture;

/**
 * WS
 */
public class ChatAsync extends CustomListener {
    /**
     * 创建WS连接
     * @param token Token信息
     */
    public ChatAsync(Token token) {
        super(token);
    }

    /**
     * 发送消息
     * @param message 消息内容
     * @param model 模型
     * @param enableWeb 是否开启WEB
     * @return 异步回调 {@link CompletableFuture}
     */
    public CompletableFuture<MessageSegment> sendMessage(String message, String model, boolean enableWeb) {
        return super.sendMessage(message, model, enableWeb);
    }
}