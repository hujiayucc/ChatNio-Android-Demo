package cn.hujiayucc.chatnio.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.StrictMode
import com.hujiayucc.chatnio.android.ChatNio


class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val  policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        appContext = this
        prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        editor = prefs.edit()!!

        prefs.getString("key", null)?.let {
            chatNio = ChatNio(it)
        }
    }

    companion object {
        lateinit var chatNio: ChatNio
        lateinit var prefs: SharedPreferences
        lateinit var editor: Editor
        lateinit var KEY: String
        lateinit var appContext: Context
    }
}