package pl.kurs.java.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.exception.UploadFileNotFoundException;
import pl.kurs.java.model.FileModel;
import pl.kurs.java.service.FileService;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileCacheController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.homePage());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("File name in database: " + fileService.save(file));
        } else {
            throw new UploadFileNotFoundException();
        }

    }

    @GetMapping("/file/{name}")
    public ResponseEntity<byte[]> downloadOneFileByName(@PathVariable("name") String name) throws IOException {
        FileModel oneFile = fileService.getOneFileByName(name);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + oneFile.getName() + "\"")
                .body(oneFile.getData());
    }

    @GetMapping("/file/id/{id}")
    public ResponseEntity<byte[]> downloadOneFileById(@PathVariable("id") Long id) throws IOException {
        FileModel oneFile = fileService.getOneFileById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + oneFile.getName() + "\"")
                .body(oneFile.getData());
    }

    @GetMapping("/file/extension/{extension}")
    public ResponseEntity<ByteArrayResource> getFilesWithExtension(@PathVariable("extension") String extension) throws IOException {
        ByteArrayResource fileOutputStream = fileService.getAllFilesWithExtension(extension);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"").body(fileOutputStream);
    }

    @GetMapping("/file/all")
    public ResponseEntity<List<String>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.FOUND).body(fileService.getAllFiles());
    }

    @DeleteMapping("/chuj")
    public ResponseEntity<String> deleteAllFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.deleteAllFiles());
    }

    @DeleteMapping("/chuj/{name}")
    public ResponseEntity<String> deleteOneFile(@PathVariable("name") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.deleteOneFile(id));
    }
}