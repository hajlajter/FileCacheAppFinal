package pl.kurs.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.exception.DuplicateFileNameException;
import pl.kurs.java.exception.FilesNotFoundException;
import pl.kurs.java.model.FileModel;
import pl.kurs.java.repository.FileRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public String homePage() {
        return "Welcome to my archive. Poniżej instrukcja użytkowania.<br>" +
                "Strona główna: https://file-cache-app-final.herokuapp.com<br>" +
                "Dodawanie pliku do archiwum (tylko poprzez programy typu Postman) POST: https://file-cache-app-final.herokuapp.com/upload + plik do wrzucenia, KEY=file<br>" +
                "Pobranie pliku po nazwie (tylko poprzez przeglądarkę) GET: https://file-cache-app-final.herokuapp.com/file/NAZWA_PLIKU<br>" +
                "Pobranie pliku po id (tylko poprzez przeglądarkę) GET: https://file-cache-app-final.herokuapp.com/file/id/ID<br>" +
                "Wyswietlenie listy plików o podanym rozszerzeniu GET: https://file-cache-app-final.herokuapp.com/file/extension/ROZSZERZENIE<br>" +
                "Wyświetlenie listy wszystkich plików GET: https://file-cache-app-final.herokuapp.com/file/all<br>" +
                "Usuwać też się da.";
    }

    public String save(MultipartFile file) throws IOException, DuplicateFileNameException {
        int dotPlace = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(dotPlace);
        String fileName = UUID.randomUUID().toString() + extension;
        FileModel fileModel = FileModel.builder().name(fileName).type(file.getContentType()).data(file.getBytes()).build();
        if (existInDatabase(fileName)) throw new DuplicateFileNameException();
        else {
            FileModel save = fileRepository.save(fileModel);
            return save.getName();
        }
    }

    public boolean existInDatabase(String name) {
        return fileRepository.existsByName(name);
    }

    public FileModel getOneFileByName(String name) throws IOException {
        List<FileModel> byName = fileRepository.findByName(name);
        if (byName.size() < 1) {
            throw new FileNotFoundException("Plik o podanej nazwie nie występuje w bazie");
        } else {
            return byName.get(0);
        }
    }

    public String zipMultipleFiles(List<FileModel> fileModels) throws IOException {
        String name = UUID.randomUUID().toString();
        FileOutputStream fos = new FileOutputStream(name);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (FileModel fileModel : fileModels) {
            InputStream fis = new ByteArrayInputStream(fileModel.getData());
            ZipEntry zipEntry = new ZipEntry(fileModel.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int lenght;
            while ((lenght = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, lenght);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        return name;
    }

    public FileModel getOneFileById(Long id) throws FileNotFoundException {
        return Optional.of(fileRepository.findById(id).get()).orElseThrow(FileNotFoundException::new);
    }

    public ZipInputStream getAllFilesWithExtension(String extension) throws IOException {
        List<FileModel> collect = fileRepository.findAll().stream()
                .filter(x -> x.getName().endsWith("." + extension))
                .collect(Collectors.toList());
        if (collect.size() > 0) {
            String name = zipMultipleFiles(collect);
            FileInputStream fileInputStream = new FileInputStream(name);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            return zipInputStream;
        } else {
            throw new FilesNotFoundException();
        }
    }

    public List<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }

    public String deleteAllFiles() {
        if (fileRepository.findAll().size() == 0) {
            return "Baza jest pusta";
        } else {
            fileRepository.deleteAll();
            return "Wszystkie pliki zostały usunięte";
        }
    }

    public String deleteOneFile(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
            return "Plik usunięty";
        } else {
            return "Plik o podanym id nie istnieje";
        }
    }

}