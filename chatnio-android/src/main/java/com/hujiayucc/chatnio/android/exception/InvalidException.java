package com.hujiayucc.chatnio.android.exception;

/**
 * 参数无效
 */
public class InvalidException extends Exception {
    /**
     * 参数无效
     * @param message 报错信息
     */
    public InvalidException(String message) {
        super(message);
    }
}
