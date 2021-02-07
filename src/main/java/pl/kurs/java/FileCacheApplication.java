package pl.kurs.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileCacheApplication {

    /*
    Stworz aplikacje, ktora bedzie miala ponizsze metody
    @POST /upload
    MultipartFile: a tutaj sciezka do pliku (bedziemy to uruchamiac w postmanie)
    ta metoda ma wrzucac plik (np java) na heroku

    @GET /file/{name}
    ta metoda ma sciagac plik o nazwie name na urządzenie

    @GET /file/extension/java
    ta metoda ma ściągać wszystkie pliki o rozszerzeniu .java

DODATKOWO:
Napisz testy do wszystkiego
     */
    public static void main(String[] args) {
        SpringApplication.run(FileCacheApplication.class, args);
    }
}
