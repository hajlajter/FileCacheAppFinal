package pl.kurs.java.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.FileModel;
import pl.kurs.java.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileCacheController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome on my site");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            fileService.save(file);
        } else {
            throw new RuntimeException("Please load a file");
        }
        return ResponseEntity.status(HttpStatus.OK).body("File added");
    }

    @GetMapping("/file/{name}")
    public ResponseEntity<byte[]> downloadOneFile(@PathVariable("name") String name) throws IOException {
        FileModel oneFile = fileService.getOneFile(name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + oneFile.getName() + "\"")
                .body(oneFile.getData());
    }

    @GetMapping("/file/extension/java")
    public ResponseEntity<List<FileModel>> getFilesWithJavaExtension() {
        return ResponseEntity.status(HttpStatus.FOUND).body(fileService.getAllFilesWithJavaExtension());
    }
}