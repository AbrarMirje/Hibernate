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
        Map<?, ?> props = new HashMap<>();


        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);



        EntityManager em = emf.createEntityManager(); // Represents the Context

        try {
            em.getTransaction().begin();

//            Employee employee1 = em.find(Employee.class, 1);

            // The below lines of code does not update into the existing id, bcoz it's create separate instance outside the Context
            Employee e = new Employee();
            e.setId(1);
            e.setName("Abrar");
            e.setAddress("Something");
            // If I want to replace values into the DB by above values then I need to merge above instance
            em.merge(e);


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