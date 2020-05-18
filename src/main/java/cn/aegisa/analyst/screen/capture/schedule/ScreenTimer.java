package cn.aegisa.analyst.screen.capture.schedule;

import cn.aegisa.analyst.screen.capture.push.DingPusher;
import cn.aegisa.analyst.screen.capture.service.OcrCenter;
import cn.aegisa.analyst.screen.capture.vo.OcrNode;
import cn.aegisa.analyst.screen.capture.vo.OcrWrapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2020/5/18 12:35
 */
@Component
@Slf4j
public class ScreenTimer {

    @Value("${screen.cap.folder}")
    private String sysFolder;

    @Autowired
    private OcrCenter ocrCenter;

    @Autowired
    private DingPusher dingPusher;

    private String currentLevel = "";

    private String currentExp = "";

    @Scheduled(cron = "0 0/5 9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?")
    public void doTimer() throws Exception {
        captureScreen(sysFolder, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png");
    }

    public void captureScreen(String FOLDER, String fileName) throws Exception {
        Dimension screenSize = new Dimension();
        screenSize.setSize(1920, 1080);
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //保存路径
        File screenFile = new File(FOLDER);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, fileName);
        ImageIO.write(image, "png", f);
        log.info("saved:{}", f.getName());
        OcrWrapper ocrWrapper = ocrCenter.doOCR(sysFolder + "/" + fileName);
        log.info(JSON.toJSONString(ocrWrapper));
        List<OcrNode> wordsResult = ocrWrapper.getWords_result();
        if (wordsResult != null) {
            for (OcrNode ocrNode : wordsResult) {
                String words = ocrNode.getWords();
                if (!StringUtils.isEmpty(words)) {
                    if (words.contains("当前等级")) {
                        if (!words.equals(currentLevel)) {
                            // 和目前存的不一样 发送提醒+更新
                            currentLevel = words;
                            dingPusher.push(words);
                        }
                    }
                }
            }
        }
    }

}
