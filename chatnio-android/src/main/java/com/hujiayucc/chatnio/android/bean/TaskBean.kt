package com.hujiayucc.chatnio.android.bean

/**
 * 对话Bean
 */
data class TaskBean(
    @JvmField val id: Int,
    val userId: Int,
    val name: String,
    val messages: List<Message>,
    val model: String,
    val enableWeb: Boolean
) {
    /**
     * 对话ID
     *
     * @return id
     */
    fun id(): Int {
        return id
    }

    /**
     * UserID
     *
     * @return userId
     */
    fun userId(): Int {
        return userId
    }

    /**
     * 对话名称
     *
     * @return name
     */
    fun name(): String {
        return name
    }

    /**
     * 消息列表
     *
     * @return message
     */
    fun messages(): List<Message> {
        return messages
    }

    /**
     * AI模型
     *
     * @return model
     */
    fun model(): String {
        return model
    }

    /**
     * 是否开启联网搜索
     *
     * @return enableWeb
     */
    fun enableWeb(): Boolean {
        return enableWeb
    }
}
