package be.syntra.java.advanced.server;

import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test class starts a fully configured Spring Boot Application on a random port and binds that
 * port to a field so it can be used inside the test class.
 * <p>
 * A TestRestTemplate is autowired to be able to perform calls against the running application.
 * TestRestTemplate enables us to easily set up basic authentication and has other useful test features.
 * <p>
 * This is running a full MVC application, so it's hitting each layer and even reaching the database.
 * Consider using SpringBootTest for full end-to-end testing, but definitely not for unit testing.
 * <p>
 * Even if you were to mock @{@link BookService}, it would still load the repository into the application context.
 *
 * @see <a href="https://spring.io/guides/gs/testing-web/">Spring.io - Testing Web</a> (The first part)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testGetAllBooks() {
		// given
		restTemplate = restTemplate.withBasicAuth("homer", "password");

		// when
		final ResponseEntity<BookList> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/books", BookList.class);
		final BookList bookList = responseEntity.getBody();

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(bookList).isNotNull();
		assertThat(bookList.getBooks()).isNotEmpty();
	}
}
