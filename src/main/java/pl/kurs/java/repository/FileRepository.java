package pl.kurs.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kurs.java.model.FileModel;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {

    @Query("SELECT u FROM FileModel u WHERE u.name = ?1")
    List<FileModel> findByName(String name);

    @Query("SELECT COUNT(u) FROM FileModel u WHERE u.name = ?1")
    Long existByName(String name);
}
