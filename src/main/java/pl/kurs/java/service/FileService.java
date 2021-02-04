package pl.kurs.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.configuration.DatabaseConfig;
import pl.kurs.java.repository.FileRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    public void save(MultipartFile file) {
        fileRepository.save(file);
    }

    public void getOneFile(String name) throws IOException {
    }

    public Iterable<MultipartFile> getAllFilesWithJavaExtension(){
        return fileRepository.findAll();
    }
}
