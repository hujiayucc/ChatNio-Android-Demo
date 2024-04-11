package com.hujiayucc.chatnio.android.bean

import com.alibaba.fastjson2.JSONObject

/**
 * 消息段
 */
class MessageSegment {
    /**
     * 获取消息内容
     * @return 消息内容
     */
    /**
     * 消息内容
     */
    @JvmField
    var message: String? = null
    /**
     * 获取keyword
     * @return keyword
     */
    /**
     * keyword
     */
    var keyword: String? = null
    /**
     * 获取使用金额
     * @return 使用金额
     */
    /**
     * 使用金额
     */
    var quota: Int = 0
    /**
     * 是否已获取完全
     * @return 是否已获取完全
     */
    /**
     * 是否已获取完全
     */
    var isEnd: Boolean = false

    /**
     * @return Json字符串
     */
    override fun toString(): String {
        return JSONObject()
            .fluentPut("message", message)
            .fluentPut("keyword", keyword)
            .fluentPut("quota", quota)
            .fluentPut("end", isEnd)
            .toJSONString()
    }
}