package cn.aegisa.analyst.screen.capture;

import cn.hutool.core.io.IoUtil;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2020/6/8 10:25 下午
 */
public class TestMinio extends BaseTester {

    @Autowired
    private MinioClient minioClient;

    @Test
    public void test01() throws Exception {
        boolean test = minioClient.bucketExists("test2");
        System.out.println("test = " + test);
    }

    @Test
    public void test02() throws Exception {
        File file = new File("/Users/xianyingda/Downloads/WechatIMG45.png");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IoUtil.copy(fis, bos);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        minioClient.putObject("screen", "current_screen.png", is, "image/png");
    }

    @Test
    public void test03() throws Exception{
        String screen = minioClient.presignedGetObject("screen", "current_screen.png");
        System.out.println("screen = " + screen);
    }
}
