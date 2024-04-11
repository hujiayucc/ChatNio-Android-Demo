package cn.hujiayucc.chatnio.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.StrictMode
import com.hujiayucc.chatnio.android.ChatNio
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val  policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        editor = prefs?.edit()

        prefs?.let {
            key = it.getString("key", null) ?: "sk-e5f947339c890f9c9cade33ec2a2befa4aefcef54ec7049cb721b95910b086a7"
        }
    }

    companion object {
        var chatNio: ChatNio? = null
        var prefs: SharedPreferences? = null
        var editor: Editor? = null
        var key: String? = null
    }
}