package org.demo1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.demo1.entity.Product;
import org.demo1.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String puName = "my-persistence-unit";
        Map<?, ?> props = new HashMap<>();


        // 1) For CustomPersistenceUnitInfo (Not an XML file)
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        // 2) For persistence.xml file
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");


        EntityManager em = emf.createEntityManager(); // Represents the Context

        try {
            em.getTransaction().begin();

            Product p = new Product();
            p.setId(2L);
            p.setName("JPA");
            em.persist(p); // add this to the context => persist is NOT an INSERT query

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}