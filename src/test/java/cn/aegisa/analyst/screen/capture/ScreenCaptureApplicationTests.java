package cn.aegisa.analyst.screen.capture;

import cn.aegisa.analyst.screen.capture.service.OcrCenter;
import cn.aegisa.analyst.screen.capture.vo.OcrWrapper;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScreenCaptureApplicationTests {

    @Autowired
    private OcrCenter ocrCenter;

    @Value("${screen.cap.folder}")
    private String sysFolder;

    @Test
    void contextLoads() {
        OcrWrapper ocr = ocrCenter.doOCR(sysFolder + "/20200518133600.jpg");
        System.out.println(JSON.toJSONString(ocr));
    }

}
