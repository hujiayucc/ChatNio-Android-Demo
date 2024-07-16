package cn.hujiayucc.chatnio

import com.alibaba.fastjson.JSONObject
import okhttp3.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        GetClient("/quota", "sk-111")
        println("\n")
        GetClient("/quota", "1")
    }
}