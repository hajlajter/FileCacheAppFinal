package pl.kurs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.java.model.FileModel;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {

    FileModel findByName(String name);

    Long deleteByName(String name);

    boolean existsByName(String name);

}
