package gr.codehub.jpa;

import gr.codehub.jpa.model.Department4;
import gr.codehub.jpa.model.Employee4;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

//SELECT * FROM EMPLOYEES4;
//SELECT * FROM DEPARTMENTS4;
//SELECT * FROM EMPLOYEE4_DEPARTMENT4;

public class Main4 {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Department4 engineering = new Department4("Engineering " + Math.random());
        Department4 marketing = new Department4("Marketing " + Math.random());
        entityManager.persist(engineering);
        entityManager.persist(marketing);

        Employee4 employee1 = new Employee4("John", "Doe", Util.email("john.doe4"));
        Employee4 employee2 = new Employee4("Jane", "Smith", Util.email("jane.smith4"));

        List<Department4> johnDepartments = new ArrayList<>();
        johnDepartments.add(engineering);
        employee1.setDepartments(johnDepartments);

        List<Department4> janeDepartments = new ArrayList<>();
        janeDepartments.add(engineering);
        janeDepartments.add(marketing);
        employee2.setDepartments(janeDepartments);

        List<Employee4> engineeringEmployees = new ArrayList<>();
        engineeringEmployees.add(employee1);
        engineeringEmployees.add(employee2);
        engineering.setEmployees(engineeringEmployees);

        List<Employee4> marketingEmployees = new ArrayList<>();
        marketingEmployees.add(employee2);
        marketing.setEmployees(marketingEmployees);

        entityManager.persist(employee1);
        entityManager.persist(employee2);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
