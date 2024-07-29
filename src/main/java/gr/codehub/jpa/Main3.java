package gr.codehub.jpa;

import gr.codehub.jpa.model.Department3;
import gr.codehub.jpa.model.Employee3;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main3 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Department3 department = new Department3("Engineering " + Math.random());
        entityManager.persist(department);

        Employee3 employee1 = new Employee3("John", "Doe", Util.email("john.doe3"));
        employee1.setDepartment(department);

        Employee3 employee2 = new Employee3("Jane", "Smith", Util.email("jane.smith3"));
        employee2.setDepartment(department);

        List<Employee3> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        department.setEmployees(employees);

        entityManager.persist(employee1);
        entityManager.persist(employee2);

        entityManager.getTransaction().commit();
        
        Department3 firstDepartment = entityManager.find(Department3.class, 1L);
        System.out.println("First department:\n" + firstDepartment);
        
        entityManager.close();
        entityManagerFactory.close();
    }
}
