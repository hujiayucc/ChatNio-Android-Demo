package com.hujiayucc.chatnio.android.data;

import android.util.Log;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hujiayucc.chatnio.android.bean.Package;
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
 * 余额
 */
public class Pets {
    private final String key;

    /**
     * 余额
     * @param key key
     */
    public Pets(String key) {
        this.key = key;
    }

    /**
     * 查询余额
     * @return 余额
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public float getQuota() throws AuthException, FieldException, IOException {
        GetClient quota;
        quota = new GetClient("/quota", key);

        if (quota.statusCode() == 401) throw new AuthException("Unauthorized");
        if (quota.statusCode() == 200) {
            JSONObject quotaJson = JSON.parseObject(quota.body());
            return quotaJson.getFloat("quota");
        }
        throw new FieldException("Quota select failed.");
    }

    /**
     * 购买余额
     * @param quota 介于 1 ~ 99999 之间的整数
     * @return 购买是否成功
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     * @throws BuyException 购买失败
     */
    public boolean buy(int quota) throws AuthException, FieldException, BuyException {
        if (quota < 1 || quota > 99999) throw new FieldException("购买金额在 1-99999 之间");
        PostClient buy;
        Map<String, Object> map = new TreeMap<>();
        map.put("quota", quota);
        buy = new PostClient("/buy", map, key);
        if (buy.statusCode() == 401) throw new AuthException("Unauthorized");
        if (buy.statusCode() == 200) {
            JSONObject buyJson = JSON.parseObject(buy.body());
            boolean buyStatus = buyJson.getBoolean("status");
            if (buyStatus) return true;
            throw new BuyException(buyJson.getString("error"));
        }
        throw new FieldException("Buy quota failed.");
    }

    private GetClient getPackageClient() {
        return new GetClient("/package", key);
    }

    /**
     * 实名认证即可获得 50 Nio 点数
     * @return 查询实名认证即可获得 50 Nio 点数
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public boolean getCert() throws AuthException, FieldException {
        GetClient cert = getPackageClient();
        if (cert.statusCode() == 401) throw new AuthException("Unauthorized");
        if (cert.statusCode() == 200) {
            JSONObject certJson = JSON.parseObject(cert.body());
            return certJson.getJSONObject("data").getBoolean("cert");
        }
        throw new FieldException("Get cert failed.");
    }

    /**
     * 未成年（学生）可额外获得 150 Nio 点数
     * @return 未成年（学生）可额外获得 150 Nio 点数
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public boolean getTeenager() throws AuthException, FieldException {
        GetClient teenager = getPackageClient();
        if (teenager.statusCode() == 401) throw new AuthException("Unauthorized");
        if (teenager.statusCode() == 200) {
            JSONObject certJson = JSON.parseObject(teenager.body());
            return certJson.getJSONObject("data").getBoolean("teenager");
        }
        throw new FieldException("Get teenager failed.");
    }

    /**
     * 查询礼包
     * @return 礼包信息
     * @throws AuthException 认证失败
     * @throws FieldException 字段异常
     */
    public Package getPackage() throws AuthException, FieldException {
        GetClient packageClient = getPackageClient();
        if (packageClient.statusCode() == 401) throw new AuthException("Unauthorized");
        if (packageClient.statusCode() == 200) {
            JSONObject data = JSON.parseObject(packageClient.body()).getJSONObject("data");
            return new Package(data.getBoolean("cert"), data.getBoolean("teenager"));
        }
        throw new FieldException("Get package failed.");
    }
}
