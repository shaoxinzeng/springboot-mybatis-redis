package com.hkd.test;

import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by LiPeng on 2017/8/21.
 */
public class TestA {

    public static void main(String[] args) {
//        "fee": [
//        {
//            "type": "qscservice",
//                "amount": 49381.2,
//                "period": 0,
//                "time": "2017/7/14 0:00:00",
//                "repaytype": "single"
//        },
//        {
//            "type": "service",
//                "amount": 8256,
//                "period": 0,
//                "time": "2017/7/14 0:00:00",
//                "repaytype": "single"
//        }
//        ]
        Map<String, Object> baseMap = new HashMap<>();
        baseMap.put("productno","001");
        baseMap.put("producttype","SYD");
        baseMap.put("repaymenttype","acpi");



        /*fee  json*/
        Map<String, Object> feeMap = new HashMap<>();
        List<Map<String,Object>> feeResult =  new ArrayList<Map<String,Object>>();
        Map<String, Object> map = new HashMap<>();
        map.put("type", "qscservice");
        map.put("amount", 49381.2);
        map.put("period",0);
        map.put("time", "2017/7/14 0:00:00");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("type", "service");
        map1.put("amount", 8256);
        map1.put("period",0);
        map1.put("time", "2017/7/14 0:00:00");

        feeResult.add(map);
        feeResult.add(map1);
        feeMap.put("fee",feeResult);

    /*dtransaction  json*/
        Map<String, Object> dtransactionMap = new HashMap<>();
        List<Map<String,Object>> dtransactionResult =  new ArrayList<Map<String,Object>>();
        Map<String, Object> map2 = new HashMap<>();
        map2.put("type", "repayprincipal");
        map2.put("amount", 4938.2);
        map2.put("period",1);
        map2.put("time", "2017/7/14 0:00:00");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("type", "repayinterest");
        map3.put("amount", 82);
        map3.put("period",1);
        map3.put("time", "2017/7/14 0:00:00");

        dtransactionResult.add(map2);
        dtransactionResult.add(map3);
        dtransactionMap.put("dtransaction",dtransactionResult);

        baseMap.put("dtransaction",dtransactionResult);
        baseMap.put("fee",feeResult);

        JSONObject json = JSONUtil.parseFromMap(baseMap);
//        JSONArray json = JSONArray.fromObject(feeResult);
        System.err.println(json.toString());

    }

}
