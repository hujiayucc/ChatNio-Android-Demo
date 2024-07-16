package cn.hujiayucc.chatnio

import android.util.Log
import com.hujiayucc.chatnio.android.ChatNio
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * GET请求
 * @param url 请求url
 * @param key key
 */
class GetClient(url: String, key: String) {
    private var response: Response? = null

    init {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        val request: Request = Request.Builder()
            .header("Authorization", key)
            .url(ChatNio.API + url)
            .get().build()

        try {
            response = client.newCall(request).execute()
            println(response?.code)
            println(response?.body?.string())
        } catch (e: IOException) {
            Log.e("GetClient", e.message!!)
        }
    }

    /**
     * GET请求返回内容
     * @return GET数据
     */
    fun body(): String {
        if (response?.body != null) {
            try {
                return response?.body?.string() ?: ""
            } catch (e: IOException) {
                Log.e("GetClient", e.message, e)
            }
        }
        return ""
    }

    /**
     * 请求状态码
     * @return 状态码
     */
    fun statusCode(): Int {
        return response?.code ?: -1
    }
}