package com.hujiayucc.chatnio.android.bean

/**
 * 消息
 * @param role 角色 [Role]
 * @param content 对话内容 [String]
 */
data class Message(val role: Role, val content: String) {
    /**
     * 角色
     *
     * @return 角色
     */
    fun role(): Role {
        return role
    }

    /**
     * 对话内容
     *
     * @return 对话内容
     */
    fun content(): String {
        return content
    }
}