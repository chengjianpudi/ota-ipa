package com.raspberry.ota.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.client.exception.OceanException;
import com.raspberry.ota.model.ipaDetail;
import com.umeng.uapp.param.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: ota
 * @description: 友盟数据埋点
 * @author: Wangshihai
 * @create: 2020-02-06 11:12
 **/
@Controller
@RequestMapping("/umeng")
@Slf4j
@Data
public class UmengDataControlle {

    @RequestMapping("/show")
    public void  show()
    {

        ApiExecutor apiExecutor = new ApiExecutor("4489159", "6OJyZ2K5Nn");
        apiExecutor.setServerHost("gateway.open.umeng.com");
//        UmengUappGetAllAppDataParam param = new UmengUappGetAllAppDataParam();
//        try {
//            UmengUappGetAllAppDataResult result = apiExecutor.execute(param);
//            System.out.println(JSONObject.toJSONString(result));
//        } catch (OceanException e) {
//            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
//        }

        //===========
//        UmengUappEventCreateParam param = new UmengUappEventCreateParam();
//        param.setAppkey("4489159");
//        param.setEventName("xikang_city_fuc");
//        param.setEventDisplayName("功能点击测试");
//        param.setEventType(false);
//
//        try {
//            UmengUappEventCreateResult result = apiExecutor.execute(param);
//            System.out.println(JSONObject.toJSONString(result));
//        } catch (OceanException e) {
//            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
//        }
        UmengUappGetAppCountParam param = new UmengUappGetAppCountParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);

        try {
            UmengUappGetAppCountResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }

    }
}
