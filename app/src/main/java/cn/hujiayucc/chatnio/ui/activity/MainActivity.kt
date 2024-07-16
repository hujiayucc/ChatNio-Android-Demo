package cn.hujiayucc.chatnio.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import cn.hujiayucc.chatnio.R
import cn.hujiayucc.chatnio.application.BaseApplication.Companion.KEY
import cn.hujiayucc.chatnio.databinding.ActivityMainBinding
import com.hujiayucc.chatnio.android.bean.Models
import com.hujiayucc.chatnio.android.bean.Token
import com.hujiayucc.chatnio.android.utils.ChatAsync
import okhttp3.Response
import okhttp3.WebSocket
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    private fun sendM() {
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val chatAsync: ChatAsync = object : ChatAsync(Token(KEY)) {
                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    Log.d("Task", text)
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    Log.d("Task", "code: $code reason: $reason")
                    super.onClosed(webSocket, code, reason)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    t.message?.let { Log.e("Task", it) }
                    super.onFailure(webSocket, t, response)
                }
            }
            chatAsync.sendMessage("你好", Models.default, false).join()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_task -> {
                sendM()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}