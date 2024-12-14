package life.mabi.demo.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private String timeStamp;

    public String getTimeStamp() {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss E a", Locale.KOREA).format(ldt);
    }
}
