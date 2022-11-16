package com.jdc.mkt.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.event.ListSelectionEvent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.mkt.Car;
import com.jdc.mkt.Person;
import com.jdc.mkt.School;

public class CarTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeAll
	static void createEMF() {
		emf=Persistence.createEntityManagerFactory("test");
	}
	
	@BeforeEach
	void createEM() {
		em=emf.createEntityManager();
	}
	
	@AfterEach
	void closeEM() {
		em.close();
	}
	
	@ParameterizedTest
	@CsvSource("Toyota_96,ygn_59993")
	void createCar(String model,String number) {
		Car car = new Car(model,number);
		Person p = new Person("Aung Aung","092342342");
		School s1=new School("JDC");
		School s2=new School("JDC");
		List<School>list=List.of(s1,s2);
		p.setSchools(list);
		car.setPerson(p );
		
		em.getTransaction().begin();
		
		em.persist(s1);
		em.persist(s2);
		
		em.persist(p);
		em.persist(car);
		
		assertTrue(em.contains(car));
		
		assertEquals(1, car.getId());
		
		em.detach(car);
		
		assertFalse(em.contains(car));
		
		
		
		em.getTransaction().commit();
	}
	
	@AfterAll
	static void closeEMF() {
		if(emf.isOpen()|| null!=emf) {
			emf.close();
		}
	}
}
