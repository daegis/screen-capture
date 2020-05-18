package cn.aegisa.analyst.screen.capture.push;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xianyingda@gmail.com
 * @serial
 * @since 2019-03-15 16:13
 */
@Data
@NoArgsConstructor
public class DingTextMessage {
    private String msgtype = "text";
    private Text text;

    @Data
    public static class Text {
        private String content;
    }

    public DingTextMessage(String message) {
        this.text = new Text();
        this.text.setContent(message);
    }

}
