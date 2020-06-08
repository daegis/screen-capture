package cn.aegisa.analyst.screen.capture.schedule;

import cn.aegisa.analyst.screen.capture.push.DingPusher;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2020/5/18 12:35
 */
@Component
@Slf4j
public class ScreenTimer {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private DingPusher dingPusher;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void doTimer() throws Exception {
        captureScreen();
    }

    public void captureScreen() throws Exception {
        Dimension screenSize = new Dimension();
        screenSize.setSize(1920, 1080);
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        minioClient.putObject("screen", "current_screen.png", is, "image/png");
    }

}
