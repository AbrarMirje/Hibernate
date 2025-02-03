package org.demo1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.demo1.entity.Employee;
import org.demo1.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String puName = "my-persistence-unit";
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update"); // none, create, update


        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);



        EntityManager em = emf.createEntityManager(); // Represents the Context

        try {
            em.getTransaction().begin();

//            var e1 = new Employee();
//            e1.setId(1);
//            e1.setName("Stephen");
//            e1.setAddress("Dubai");
//
//            em.persist(e1);
//
//            e1 = em.find(Employee.class, 1);
//            System.out.println(e1);


            // find vs getReference
            //1)find is Eager loading in nature,
//            var e1 = em.find(Employee.class, 1);

            // 2) getReference is Lazy in nature, until you won't call it, it will not been in used.
//            var e2 = em.getReference(Employee.class,1);
//            System.out.println(e2); // now getReference will be in used
//            e2.setName("Alex");


            var e1 = em.getReference(Employee.class, 1);
            System.out.println(e1); // Alex

            e1.setName("Abrar"); // Abrar

            System.out.println("Before => " + e1); // Abrar

            em.refresh(e1); // It refreshes to its original value i.e. Alex

            System.out.println("After => " + e1); // Alex





            // Operations we do with entity
            // 1) em.persist() => Persisting the instance into the Context
            // 2) em.find() => Find the instance from the Context first using Primary Key, if not present in Context then get it from the DB
            // 3) em.remove() => Marking entity as remove, but it stays in the Context;
            // 4) em.merge() => Merges an entity to the Context from the outside of the Context.(Entities must be exists)
            // 5) em.refresh() => Mirror the Context from the DB.
            // 6) em.detach() => Taking the entity out of the Context.
            // 7) em.getReference()

            em.getTransaction().commit(); // End of the Transaction
        } finally {
            em.close();
        }
    }
}