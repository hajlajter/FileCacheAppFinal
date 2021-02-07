package pl.kurs.java.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class UploadFileNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Załaduj plik do przekazania na serwer";
    }
}
