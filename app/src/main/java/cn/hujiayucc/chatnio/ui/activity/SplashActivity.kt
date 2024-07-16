package cn.hujiayucc.chatnio.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.hujiayucc.chatnio.R
import cn.hujiayucc.chatnio.application.BaseApplication
import cn.hujiayucc.chatnio.application.BaseApplication.Companion.chatNio
import cn.hujiayucc.chatnio.application.BaseApplication.Companion.KEY
import cn.hujiayucc.chatnio.databinding.AlertAccountBinding
import cn.hujiayucc.chatnio.dialog.ProgressDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hujiayucc.chatnio.android.ChatNio
import com.hujiayucc.chatnio.android.exception.AuthException
import com.hujiayucc.chatnio.android.exception.FieldException
import com.hujiayucc.chatnio.android.exception.InvalidException

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setFullScreen()

        if (chatNio == null) {
            val alertBinding = AlertAccountBinding.inflate(layoutInflater)
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("请先设置秘钥")
                .setView(alertBinding.root)
                .setCancelable(false)
                .create()

            alertBinding.go.setOnClickListener {
                KEY = alertBinding.key.text.toString()
                if (KEY.isEmpty()) {
                    Toast.makeText(this, "请输入秘钥", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (alertBinding.remember.isChecked) BaseApplication.editor?.apply {
                    putString("key", KEY)
                    apply()
                }

                val progressDialog = ProgressDialog(this, layoutInflater)
                    .setTitle("正在验证秘钥")
                    .create()
                progressDialog.show()

                Thread {
                    try {
                        chatNio = ChatNio(KEY!!)
                        chatNio?.Pets()?.quota.let {
                            runOnUiThread {
                                progressDialog.dismiss()
                                alertDialog.dismiss()
                                startMainActivity(0)
                            }
                        }
                    } catch (e: AuthException) {
                        runOnUiThread {
                            Toast.makeText(this, "秘钥错误", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    } catch (e: FieldException) {
                        runOnUiThread {
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    } catch (e: InvalidException) {
                        runOnUiThread {
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
                }.start()
            }

            alertDialog.show()
        } else {
            KEY = chatNio!!.key
            startMainActivity(3000)
        }
    }

    /** 设置全屏 */
    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Android P之后使用这种方式来实现全屏
            val layoutParams = window.attributes
            layoutParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = layoutParams
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return false
    }

    /** 跳转到主页 */
    private fun startMainActivity(wait: Long) {
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, wait)
    }
}