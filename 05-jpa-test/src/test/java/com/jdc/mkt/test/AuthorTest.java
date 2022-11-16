package com.jdc.mkt.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jdc.mkt.entity.Author;
import com.jdc.mkt.entity.Book;

@TestMethodOrder(OrderAnnotation.class)
public class AuthorTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void createEMF() {
		emf = Persistence.createEntityManagerFactory("05-jpa-test");
	}

	@BeforeEach
	void createEM() {
		em = emf.createEntityManager();
	}

	@AfterEach
	void closeEM() {
		em.close();
	}

	@Order(1)
	@ParameterizedTest
	@CsvSource("Aung Aung,True Stroy")
	void createAuthor(String aName, String bName) {
		Author au = new Author(aName);
		Book b = new Book(bName, LocalDate.now());

		au.setBooks(List.of(b));
		b.setAuthor(au);
		// au.addBook(b);

		em.getTransaction().begin();
		em.persist(au);
		em.getTransaction().commit();

	}

	@Order(2)
	@ParameterizedTest
	@CsvSource("SoeSan,Ghost Story")
	void testLifeCycle(String aName, String bName) {

		Author au = new Author(aName);
		Book b = new Book(bName, LocalDate.now());

		au.addBook(b);

		em.getTransaction().begin();

		// To be managed
		em.persist(au);
		assertEquals(2, au.getId());

		// au states =managed states
		assertTrue(em.contains(au));

		au.setName("ToeToe");
		assertTrue(em.contains(au));

		// to be deach and not in managed
		em.detach(au);
		assertFalse(em.contains(au));

		au.setName("Mahar");
		// to be managed
		var an = em.merge(au);

		au.setName("Wanna");
		assertTrue(em.contains(an));

		em.getTransaction().commit();
	}

	@Order(3)
	@ParameterizedTest
	@ValueSource(ints = 2)
	void testFindMethod(int id) {
		
		//em.getTransaction().begin();
		Author au = em.find(Author.class, id);
		assertEquals("Mahar", au.getName());
		assertTrue(em.contains(au));
		
		//au.setName("Kaung Kyaw");
		
		
		
		//em.getTransaction().commit();
		
	}

	@AfterAll
	static void closeEMF() {
		if (emf.isOpen() || null != emf) {
			emf.close();
		}
	}
}
