package cn.aegisa.analyst.screen.capture.schedule;

import cn.aegisa.analyst.screen.capture.service.OcrCenter;
import cn.aegisa.analyst.screen.capture.vo.OcrWrapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Scheduled(cron = "0/5 * * * * ?")
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
    }

}
