package cn.aegisa.analyst.screen.capture.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2020/6/8 10:13 下午
 */
@Configuration
public class MinioConfig {

    public static final String URL = "http://oss.ihna.cn/";
    public static final String KEY = "admin";
    public static final String SEC = "08031024";

    @Bean
    public MinioClient minioClient() throws Exception {
        return new MinioClient(URL, KEY, SEC);
    }
}
