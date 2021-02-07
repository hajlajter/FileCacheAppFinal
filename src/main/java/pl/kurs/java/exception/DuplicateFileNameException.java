package pl.kurs.java.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateFileNameException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Plik o tej nazwie występuje już w bazie, zmień nazwę lub zaktualizuj plik";
    }
}
