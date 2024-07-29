package gr.codehub.jpa;

import gr.codehub.jpa.model.Employee1;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main1 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        entityManager.getTransaction().begin();
        
        Employee1 employee = new Employee1("John", "Doe", "john.doe@example.com");
        employee.setId(1002L);
        entityManager.persist(employee);
        
        entityManager.getTransaction().commit();
        
        entityManager.close();
        entityManagerFactory.close();
        
        System.out.println("Goodbye!");
    }
}
