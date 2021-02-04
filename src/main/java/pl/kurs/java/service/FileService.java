package pl.kurs.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {


    public void save(MultipartFile file) {

    }

    public void getOneFile(String name) throws IOException {
    }

    public List<MultipartFile> getAllFilesWithJavaExtension(){
        return null;
    }
}
