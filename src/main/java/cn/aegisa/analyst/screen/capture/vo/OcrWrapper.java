package cn.aegisa.analyst.screen.capture.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2020/5/18 2:03 下午
 */
@Data
public class OcrWrapper {
    private List<OcrNode> words_result;
}
