package pl.kurs.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.Model;
import pl.kurs.java.repository.FileRepository;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file) {
        Model savedModel = fileRepository.save(new Model(file));
    }

    public void getOneFile(String name) throws IOException {
    }

    public Iterable<Model> getAllFilesWithJavaExtension() {
        return fileRepository.findAll();
    }
}