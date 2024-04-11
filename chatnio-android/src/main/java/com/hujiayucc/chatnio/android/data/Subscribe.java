package com.hujiayucc.chatnio.android.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hujiayucc.chatnio.android.enums.SubLevel;
import com.hujiayucc.chatnio.android.exception.AuthException;
import com.hujiayucc.chatnio.android.exception.BuyException;
import com.hujiayucc.chatnio.android.exception.FieldException;
import com.hujiayucc.chatnio.android.utils.GetClient;
import com.hujiayucc.chatnio.android.utils.PostClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 订阅
 */
public class Subscribe {
    private final String key;

    /**
     * 订阅
     * @param key key
     */
    public Subscribe(String key) {
        this.key = key;
    }

    /**
     * 查询是否订阅
     * @return 订阅情况
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     */
    public boolean isSubscribed() throws AuthException, FieldException, IOException {
        GetClient client;
        client = new GetClient("/subscription", key);
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            return JSON.parseObject(client.body()).getBoolean("is_subscribed");
        }
        throw new FieldException("Subscription select failed.");
    }

    /**
     * 查询订阅剩余时间
     * @return 剩余时间
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     */
    public int expired() throws AuthException, FieldException, IOException {
        GetClient client;
        client = new GetClient("/subscription", key);
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            return JSON.parseObject(client.body()).getInteger("expired");
        }
        throw new FieldException("Subscription select failed.");
    }

    /**
     * 订阅
     * @param month 订阅月数,介于 0 ~ 999 之间的整数
     * @param level 订阅等级
     * @return 是否订阅成功
     * @throws AuthException 认证失败
     * @throws FieldException 字段错误
     * @throws BuyException 订阅失败
     */
    public boolean subscribe(int month, SubLevel level) throws AuthException, FieldException, BuyException, IOException {
        if (month < 1 || month > 999) throw new FieldException("购买月数 介于 0 ~ 999 之间的整数");
        if (level == SubLevel.Normal) throw new FieldException("订阅级别不能为 普通用户");
        PostClient client;
        Map<String, Object> map = new TreeMap<>();
        map.put("month", month);
        map.put("level", level.getLevel());
        client = new PostClient("/subscribe", map, key);
        if (client.statusCode() == 401) throw new AuthException("Unauthorized");
        if (client.statusCode() == 200) {
            JSONObject json = JSON.parseObject(client.body());
            if (json.getBoolean("status")) return true;
            throw new BuyException(json.getString("error"));
        }
        throw new FieldException("Subscription buy failed.");
    }
}
