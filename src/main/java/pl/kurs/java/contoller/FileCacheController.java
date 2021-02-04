package pl.kurs.java.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.service.FileService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileCacheController {
    private final FileService fileService;

    @RequestMapping("/")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome on my site");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            fileService.save(file);
        } else {
            throw new RuntimeException("Please load a file");
        }
        return ResponseEntity.status(HttpStatus.OK).body("File added");
    }

    @GetMapping("/file/{name}")
    public ResponseEntity<Void> downloadOneFile(@PathVariable("name") String name) throws IOException {
        fileService.getOneFile(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/file/extension/java")
    public ResponseEntity<Iterable<MultipartFile>> getFilesWithJavaExtension() {
        return ResponseEntity.status(HttpStatus.FOUND).body(fileService.getAllFilesWithJavaExtension());
    }
}
