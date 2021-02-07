package pl.kurs.java.exception.customMessage;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    String showCustomMessage(Exception ex) {
        return "message: " + ex.getMessage();
    }
}
