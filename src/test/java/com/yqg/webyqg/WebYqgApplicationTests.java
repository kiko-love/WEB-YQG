package com.yqg.webyqg;

import com.yqg.mapper.UserMapper;
import com.yqg.messaging.MessageProducer;
import com.yqg.service.impl.UserArticleOperationServiceImpl;
import com.yqg.service.impl.UserServiceImpl;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebYqgApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserArticleOperationServiceImpl userArticleOperationService;
    @Autowired
    MessageProducer messageProducer;

    @Test
    public void testSelect() throws TasteException, InterruptedException {
        System.out.println(userService.getUserList());
        // 生产者发送消息
//        messageProducer.send("hello world");
//        Thread.sleep(1000);

//        List<Long> list = userArticleOperationService.recommend(10003,10);
//        String s = userArticleOperationService.genRecArticlesIntegration(list);
//        System.out.println("----- selectAll method test ------");
//        System.out.println(s);


//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        userList.forEach(System.out::println);
//        Map<String, Object> map = new HashMap<>(16) {
//            @Serial
//            private static final long serialVersionUID = 1L;
//            {
//                put("uid", Integer.parseInt("123"));
//                //过期时间为60s
//                put(JWTPayload.EXPIRES_AT, System.currentTimeMillis() + 1000 * 60 * 5);
//            }
//        };
//        String rightToken = JWTUtil.createToken(map, "1234".getBytes());
//        final JWT jwt = JWTUtil.parseToken(rightToken);
//
//        System.out.println(jwt.getHeader(JWTHeader.TYPE));
//        System.out.println(jwt.getPayload("uid"));
//        System.out.println(JWTUtil.verify(rightToken, "1234".getBytes()));
    }

}
