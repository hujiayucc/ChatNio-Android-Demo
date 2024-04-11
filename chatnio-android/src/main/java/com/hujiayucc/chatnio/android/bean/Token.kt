package com.hujiayucc.chatnio.android.bean

import com.alibaba.fastjson2.JSONObject

/**
 * Token信息
 */
class Token {
    /**
     * 获取 Token
     * @return Token
     */
    private val token: String
    private val id: Int

    /**
     * 聊天验证 Token
     * @param token JwT Token/API Key
     * @param id 对话ID， -1 为新对话
     */
    constructor(token: String, id: Int) {
        this.token = token
        this.id = id
    }

    /**
     * 聊天验证 Token
     * @param token JwT Token/API Key
     * @param task 对话
     */
    constructor(token: String, task: TaskBean) {
        this.token = token
        this.id = task.id
    }

    /**
     * 转换成JSON字符串
     */
    override fun toString(): String {
        return JSONObject().fluentPut("token", token).fluentPut("id", id).toJSONString()
    }

    /**
     * 获取对话ID
     * @return 对话ID
     */
    fun id(): Int {
        return id
    }

    companion object {
        /**
         * 匿名 Token
         * @param id 对话ID, -1 为新对话
         * @return 匿名Token
         */
        fun Anonymous(id: Int): Token {
            return Token("anonymous", id)
        }

        /**
         * 匿名Token
         * @param task 对话
         * @return 匿名Token
         */
        fun Anonymous(task: TaskBean): Token {
            return Token("anonymous", task.id)
        }

        /**
         * 匿名 Token
         */
        const val Anonymous: String = "anonymous"

        /**
         * 新对话 ID
         */
        const val NewTaskId: Int = -1
    }
}
