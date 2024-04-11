package com.hujiayucc.chatnio.android.bean

/**
 * 查询礼包
 */
data class Package(val cert: Boolean, val teenager: Boolean) {
    /**
     * 实名认证即可获得 50 Nio 点数
     *
     * @return [true, false]
     */
    fun cert(): Boolean {
        return cert
    }

    /**
     * 未成年（学生）可额外获得 150 Nio 点数
     *
     * @return [true, false]
     */
    fun teenager(): Boolean {
        return teenager
    }
}
