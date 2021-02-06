package pl.kurs.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.model.FileModel;
import pl.kurs.java.repository.FileRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Long save(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileModel fileModel = new FileModel(0, fileName, file.getContentType(), file.getBytes());
        FileModel save = fileRepository.save(fileModel);
        return save.getId();
    }

    public FileModel getOneFile(String name) throws IOException {
        return fileRepository.findById(Long.parseLong(name)).get();
    }

    public List<FileModel> getAllFilesWithExtension(String extension) {
        return fileRepository.findAll().stream()
                .filter(x -> x.getName().endsWith("." + extension))
                .collect(Collectors.toList());
    }
}