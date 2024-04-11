package com.hujiayucc.chatnio.android

import com.hujiayucc.chatnio.android.bean.Models
import com.hujiayucc.chatnio.android.data.Pets
import com.hujiayucc.chatnio.android.data.Subscribe
import com.hujiayucc.chatnio.android.data.Tasks

/**
 * ChatNio
 */
class ChatNio {
    private val pets: Pets
    private val tasks: Tasks
    private val models: Models
    private val subscribe: Subscribe

    /**
     * 创建ChatNio实例，官网默认API
     * @param key 密钥
     */
    constructor(key: String) {
        pets = Pets(key)
        tasks = Tasks(key)
        models = Models()
        subscribe = Subscribe(key)
    }

    /**
     * 创建ChatNio实例，自定义API节点
     * @param key 密钥
     * @param point API节点
     */
    constructor(key: String, point: String) {
        API = point
        pets = Pets(key)
        tasks = Tasks(key)
        models = Models()
        subscribe = Subscribe(key)
    }

    /**
     * 余额
     * @return [com.hujiayucc.chatnio.android.data.Pets]
     */
    fun Pets(): Pets {
        return pets
    }

    /**
     * 对话
     * @return [com.hujiayucc.chatnio.android.data.Tasks]
     */
    fun Tasks(): Tasks {
        return tasks
    }

    /**
     * 订阅和礼包
     * @return [com.hujiayucc.chatnio.android.data.Subscribe]
     */
    fun Subscribe(): Subscribe {
        return subscribe
    }

    /**
     * 模型
     * @return [com.hujiayucc.chatnio.android.bean.Models]
     */
    fun Models(): Models {
        return models
    }

    companion object {
        /** 默认API地址，后期会考虑自行更改节点  */
        @JvmField
        var API: String = "https://api.chatnio.net"
    }
}
