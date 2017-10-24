package com.hkd;


import com.hkd.service.UserDemoService;
import com.qiyuesuo.sdk.SDKClient;
import com.qiyuesuo.sdk.api.RemoteSignService;
import com.qiyuesuo.sdk.api.SealService;
import com.qiyuesuo.sdk.impl.RemoteSignServiceImpl;
import com.qiyuesuo.sdk.impl.SealServiceImpl;
import com.qiyuesuo.sdk.signer.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HkdApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(HkdApplicationTests.class);

//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
//
//
//    @Test
//    public void redisTest() {
//        String key = "redisTestKey";
//        String value = "I am test value";
//
//        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
//
//        //数据插入测试：
//        opsForValue.set(key, value);
//        String valueFromRedis = opsForValue.get(key);
//        System.out.println("===========================redis value after set: {}"+ valueFromRedis);
//       // assertThat(valueFromRedis, is(value));
//
//        //数据删除测试：
//        redisTemplate.delete(key);
//        valueFromRedis = opsForValue.get(key);
//        logger.info("redis value after delete: {}", valueFromRedis);
//        //assertThat(valueFromRedis, equalTo(null));
//    }



    @Test
    public void contextLoads() {
    }


    @Autowired
    private UserDemoService userDemoService;



    @Test
    public void delectUserDemo() {
        userDemoService.deleteUserById(5);

    }


}
