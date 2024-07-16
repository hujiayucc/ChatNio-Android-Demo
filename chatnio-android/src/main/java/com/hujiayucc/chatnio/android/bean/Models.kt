package com.hujiayucc.chatnio.android.bean

import com.hujiayucc.chatnio.android.utils.GetClient
import java.io.IOException
import java.net.URISyntaxException

/**
 * 模型
 */
class Models {
    /**
     * 获取所有模型
     * @return 所有模型
     */
    private val all: Array<String>

    /** AI模型  */
    init {
        val input: String
        try {
            input = GetClient("/v1/models", "").body().replace("\"", "")
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        all = input.substring(1, input.length - 1).split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
    }

    val size: Int
        /**
         * 获取模型总数
         * @return 模型总数
         */
        get() = all.size

    /**
     * 获取指定模型
     * @param index 模型序号
     * @return 模型
     */
    fun getModel(index: Int): String {
        return all[index]
    }

    private fun getModels(name: String): Array<String?> {
        val models = arrayOfNulls<String>(all.size)
        for (i in all.indices) {
            if (all[i].contains(name)) {
                models[i] = all[i]
            }
        }
        return models
    }

    /**
     * 获取全部GPT模型
     * @return GPT模型
     */
    fun GPT(): Array<String?> {
        return getModels("gpt")
    }

    /**
     * 获取全部Claude模型
     * @return Claude模型
     */
    fun Claude(): Array<String?> {
        return getModels("claude")
    }

    /**
     * 获取全部Azure模型
     * @return Azure模型
     */
    fun Azure(): Array<String?> {
        return getModels("azure")
    }

    /**
     * 获取全部Bing模型
     * @return Bing模型
     */
    fun Bing(): Array<String?> {
        return getModels("bing")
    }

    /**
     * 获取全部ZhiPu模型
     * @return ZhiPu模型
     */
    fun ZhiPu(): Array<String?> {
        return getModels("zhipu")
    }

    /**
     * 获取全部Skylark模型
     * @return Skylark模型
     */
    fun Skylark(): Array<String?> {
        return getModels("skylark")
    }

    /**
     * 获取全部Spark模型
     * @return Spark模型
     */
    fun Spark(): Array<String?> {
        return getModels("spark")
    }

    /**
     * 获取全部Gemini模型
     * @return Gemini模型
     */
    fun Gemini(): Array<String?> {
        return getModels("gemini")
    }

    companion object {
        val default: String
            /**
             * 获取默认模型
             * @return 默认模型
             */
            get() = "gpt-3.5-turbo-16k-0613"
    }
}