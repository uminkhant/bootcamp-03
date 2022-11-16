package com.jdc.mkt.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.mkt.entity.Category;
import com.jdc.mkt.entity.Item;

public class CategoryTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeAll
	static void createEmf() {
		emf = Persistence.createEntityManagerFactory("05-jpa-test");
		
	}
	
	@AfterAll
	static void closeEmf() {
		
		emf.close();
	}
	
	@BeforeEach
	void createEm() {
		em = emf.createEntityManager();
	}
	
	@AfterEach
	void closeEm() {
		em.close();
	}
	
	@ParameterizedTest
	@CsvSource("T-shirt,polo,SMALL,9500")
	void createCategory(String cName,String iName,String size,String price) {
		Category c=new Category(cName);
		Item i=new Item(iName,price,size);
		
		c.addItem(i);
		
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		
	}
}
