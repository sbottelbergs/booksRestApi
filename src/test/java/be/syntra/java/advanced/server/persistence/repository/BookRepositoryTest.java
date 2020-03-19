package be.syntra.java.advanced.server.persistence.repository;

import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    /**
     * This test creates a new BookEntity and uses the injected entity manager to
     * persist it to the database to control our "given" situation.
     */
    @Test
    void testFindByIsbn_entityManager() {
        // given
        final BookEntity bookEntity = BookEntity.builder()
                .author("An Author")
                .title("A Book title")
                .price(new BigDecimal("15.0"))
                .isbn("test-isbn")
                .build();
        final BookEntity persistedEntity = testEntityManager.persist(bookEntity);

        // when
        Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn("test-isbn");

        // then
        Assertions.assertThat(optionalBookEntity).isPresent();
        Assertions.assertThat(optionalBookEntity).hasValue(persistedEntity);
    }

    /**
     * This test is dependent on the seeding data inside of the data.sql file.
     * This makes the test brittle because changes in the data.sql file can break tests.
     * <p>
     * A second downside is that data for all test cases has to be put into a single file,
     * making it probable that this file will grow very large and become less maintainable
     */
    @Test
    void testFindByIsbn_dataSql() {
        // given - the data in data.sql
        BookEntity expected = BookEntity.builder()
                .id(1L)
                .isbn("1")
                .author("J.K. Rowling")
                .title("Harry Potter And The Philosopher's Stone")
                .price(new BigDecimal("10.00"))
                .build();

        // when
        Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn("2");

        // then
        Assertions.assertThat(optionalBookEntity).isPresent();
        Assertions.assertThat(optionalBookEntity).hasValue(expected);
    }

    /**
     * The SQL script 'createBook.sql' will be launched before running this test,
     * allowing us to take more granular control over the "given" state of the data source.
     * <p>
     * You can find 'createBook.sql' inside the test/resources folder
     */
    @Test
    @Sql("classpath:createBook.sql")
    void testFindByIsbn_createBookSql() {
        // given
        BookEntity expected = BookEntity.builder()
                .id(100L)
                .isbn("test-isbn")
                .author("A Test Author")
                .title("A Test Title")
                .price(new BigDecimal("123.45"))
                .build();

        // when
        Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn("test-isbn");


        // then
        Assertions.assertThat(optionalBookEntity).isPresent();
        Assertions.assertThat(optionalBookEntity).hasValue(expected);
    }

}
