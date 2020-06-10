package cn.aegisa.analyst.screen.capture.schedule;

import cn.aegisa.analyst.screen.capture.push.DingPusher;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sc.cap.width}")
    private Integer width;

    @Value("${sc.cap.height}")
    private Integer height;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private DingPusher dingPusher;

    @Scheduled(cron = "0/10 * * * * ?")
    public void doTimer() throws Exception {
        captureScreen();
    }

    public void captureScreen() throws Exception {
        Dimension screenSize = new Dimension();
        screenSize.setSize(width, height);
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        minioClient.putObject("screen", "current_screen.png", is, "image/png");
        log.info("{}*{}captured,upload success", width, height);
    }

}
