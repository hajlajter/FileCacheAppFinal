package pl.kurs.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.java.exception.DuplicateFileNameException;
import pl.kurs.java.exception.FilesNotFoundException;
import pl.kurs.java.model.FileModel;
import pl.kurs.java.repository.FileRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Long save(MultipartFile file) throws IOException, DuplicateFileNameException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileModel fileModel = new FileModel(0, fileName, file.getContentType(), file.getBytes());
        if (existInDatabase(fileName)) throw new DuplicateFileNameException();
        else {
            FileModel save = fileRepository.save(fileModel);
            return save.getId();
        }
    }

    public boolean existInDatabase(String name) {
        if (fileRepository.existByName(name) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public FileModel getOneFileByName(String name) throws IOException {
        List<FileModel> byName = fileRepository.findByName(name);
        if (byName.size() < 1) {
            throw new FileNotFoundException("Plik o podanej nazwie nie występuje w bazie");
        } else {
            return byName.get(0);
        }
    }

    public FileModel getOneFileById(Long id) throws FileNotFoundException {
        return Optional.of(fileRepository.findById(id).get()).orElseThrow(FileNotFoundException::new);
    }

    public List<FileModel> getAllFilesWithExtension(String extension) {
        List<FileModel> collect = fileRepository.findAll().stream()
                .filter(x -> x.getName().endsWith("." + extension))
                .collect(Collectors.toList());
        if (collect.size() > 0) {
            return collect;
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