package cn.hujiayucc.chatnio.ui.activity

import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.hujiayucc.chatnio.application.BaseApplication.Companion.chatNio
import cn.hujiayucc.chatnio.application.BaseApplication.Companion.key
import cn.hujiayucc.chatnio.databinding.ActivityMainBinding
import com.hujiayucc.chatnio.android.ChatNio
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)

        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            chatNio = key?.let { ChatNio(it) }
            chatNio?.apply {
                Looper.prepare()
                Toast.makeText(this@MainActivity, "${Pets().quota}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${Pets().buy(1)}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${Pets().cert}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, Tasks().getTask(Tasks().taskList[1].id).messages[0].content, Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }
    }
}