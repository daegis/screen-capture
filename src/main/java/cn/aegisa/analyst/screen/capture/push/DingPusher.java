package cn.aegisa.analyst.screen.capture.push;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-15 16:11
 */
@Component
@Slf4j
public class DingPusher {

    public static final String QIUMIAO = "https://oapi.dingtalk.com/robot/send?access_token=5aa53311a62b63fa08a886c0d1e459ab5f5df919768919ff178612142cdfdd0e";

    private void pushTextMessage(DingTextMessage message) {
        String payload = JSON.toJSONString(message);
        log.info("发送的信息：{}", payload);
        HttpRequest post = HttpUtil.createPost(QIUMIAO);
        post.body(payload);
        post.contentType(MediaType.APPLICATION_JSON_VALUE);
        HttpResponse response = post.execute();
        int status = response.getStatus();
        String body = response.body();
        log.info("响应结果：{}-{}", status, body);
    }

    public void push(String msg) {
        pushTextMessage(new DingTextMessage("喵喵提醒：\n" + msg));
    }
}
