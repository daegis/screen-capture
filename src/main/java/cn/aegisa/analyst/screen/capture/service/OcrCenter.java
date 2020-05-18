package cn.aegisa.analyst.screen.capture.service;

import cn.aegisa.analyst.screen.capture.vo.OcrWrapper;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2020/5/18 1:41 下午
 */
@Service
@Slf4j
public class OcrCenter {

    private AipOcr client;

    @Value("${baidu.ocr.appId}")
    private String appId;
    @Value("${baidu.ocr.appKey}")
    private String appKey;
    @Value("${baidu.ocr.appSecret}")
    private String appSecret;

    @PostConstruct
    public void init() {
        client = new AipOcr(appId, appKey, appSecret);
    }

    public OcrWrapper doOCR(String filePath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");


        // 参数为本地图片路径
        JSONObject res = client.basicAccurateGeneral(filePath, options);
        String jsonBody = res.toString(2);
        return JSON.parseObject(jsonBody, OcrWrapper.class);
    }
}
