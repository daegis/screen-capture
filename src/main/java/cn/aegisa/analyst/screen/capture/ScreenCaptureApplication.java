package cn.aegisa.analyst.screen.capture;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScreenCaptureApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ScreenCaptureApplication.class);
        builder.headless(false)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
