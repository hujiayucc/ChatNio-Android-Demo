package com.hujiayucc.chatnio.android.utils

import android.util.Log
import com.alibaba.fastjson2.JSONObject
import com.hujiayucc.chatnio.android.ChatNio
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * POST请求
 * @param url 请求URL
 * @param requestBody request
 * @param key key
 */
class PostClient(url: String, requestBody: Map<String, Any>, key: String) {
    private var response: Response? = null
    init {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        val request: Request = Request.Builder()
            .url(ChatNio.API + url)
            .header("Authorization", key)
            .post(JSONObject(requestBody).toJSONString().toRequestBody("application/json".toMediaType())).build()
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            Log.e("PostClient", e.message!!)
        }
    }

    /**
     * 返回内容
     * @return 返回内容
     */
    fun body(): String {
        if (response?.body != null) {
            try {
                return response?.body?.string() ?: ""
            } catch (e: IOException) {
                Log.e("PostClient", e.message, e)
            }
        }
        return ""
    }

    /**
     * 状态码
     * @return 状态码
     */
    fun statusCode(): Int {
        return response?.code ?: -1
    }
}