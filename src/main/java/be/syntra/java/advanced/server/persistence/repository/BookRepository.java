package be.syntra.java.advanced.server.persistence.repository;

import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Transactional(Transactional.TxType.MANDATORY)
    Optional<BookEntity> findByIsbn(String isbn);
}
