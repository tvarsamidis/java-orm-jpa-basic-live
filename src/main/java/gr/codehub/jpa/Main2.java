package gr.codehub.jpa;

import gr.codehub.jpa.model.Employee1;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Main2 {

    private static final EntityManagerFactory emf;
    private static final EntityManager entityManager;
    private static final EntityTransaction transaction;

    static {
        emf = Persistence.createEntityManagerFactory("CompanyPU");
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    private static void print(String text) {
        System.out.println(text);
    }

    private static void section(String text) {
        print("\n*************************");
        print(text);
    }

    private static void step01Persist(Employee1 employee) {
        transaction.begin();
        entityManager.persist(employee);
        transaction.commit();
    }

    private static Employee1 step02FindById(Employee1 employee) {
        Employee1 foundEmp = entityManager.find(Employee1.class, employee.getId());
        return foundEmp;
    }

    private static void step03UpdateExisting(Employee1 employee) {
        transaction.begin();
        employee.setEmail(Util.email("john.doer", "updated_domain.com"));
        transaction.commit();
    }

    private static void step04Remove(Employee1 foundEmp) {
        transaction.begin();
        entityManager.remove(foundEmp);
        transaction.commit();
    }

    private static Employee1 step05PersistWithId(Long id, Employee1 employee) {
        Employee1 idEmployee = new Employee1(employee.getFirstName(),
                employee.getLastName(), Util.email("duplicate"));
        idEmployee.setId(id);
        transaction.begin();
        try {
            print("idEmployee to persist: " + idEmployee);
            entityManager.persist(idEmployee);
            transaction.commit();
            print("Successful persist for idEmployee with id: " + id);
        } catch (Exception e) {
            print("Failed persist for idEmployee with id: " + id);
            transaction.rollback();
        } finally {
            return idEmployee;
        }
    }

    private static void step06DetachedUpdate(Employee1 employee) {
        entityManager.detach(employee);
        transaction.begin();
        try {
            entityManager.persist(employee);
            transaction.commit();
            print("Detached update succeeded: " + employee);
        } catch (Exception e) {
            print("Detached update failed: " + e.getMessage());
            transaction.rollback();
        }
    }

    private static Employee1 step07UpdateExisting(Employee1 employee) {
        transaction.begin();
        try {
            print("Employee to update before: " + employee);
            employee.setEmail(Util.email("merged_email"));
            print("Employee to update changed before merge: " + employee);
            entityManager.merge(employee);
            transaction.commit();
            print("Successful merge for employee: " + employee);
        } catch (Exception e) {
            print("Failed merged for employee: " + employee);
            transaction.rollback();
        } finally {
            return employee;
        }
    }

    public static void main(String[] args) {

        try {
            // Create a new employee
            Employee1 employee = new Employee1();
            employee.setFirstName("Jim");
            employee.setLastName("Doer");
            employee.setEmail("jim.doer@example.com");

            section("Employee has this id before persist1 = " + employee.getId());
            step01Persist(employee);
            print("Employee has this id after persist1 = " + employee.getId());

            Employee1 foundEmp = step02FindById(employee);
            section("Found employee: " + foundEmp);

            section("Attempting to update email of foundEmp: " + foundEmp);
            step03UpdateExisting(foundEmp);
            print("Updated employee foundEmp: " + foundEmp);

            // Delete the employee the second time round. First time leave to see the update
            boolean secondTime = false;
            if (secondTime) {
                step04Remove(foundEmp);
                section("Removed employee: " + foundEmp);
            }

            Employee1 employee2 = new Employee1();
            employee2.setFirstName("Tom");
            employee2.setLastName("Keeper");
            employee2.setEmail("tom.keeper" + 0 + "@example.com");

            section("Employee 2 has this id before persist2 = " + employee2.getId());
            step01Persist(employee2);
            print("Employee 2 has this id after persist2 = " + employee2.getId());

            section("Attempting to update employee2 last name and email");
            employee2.setLastName("Second Keeper " + employee2.getId());
            employee2.setEmail("tom.keeper_v" + employee2.getId() + "@example.com");
            step01Persist(employee2);
            print("Employee 2 has this id after persist3 = " + employee2.getId());

            section("Attempt to create a new copy of an existing employee with no id");
            Employee1 copy1 = step05PersistWithId(null, employee2);
            print("Copy employee copy1 is this: " + copy1);

            section("Attempt to create a new copy of an existing employee with an existing id");
            Employee1 copy2 = step05PersistWithId(copy1.getId(), copy1);
            print("Copy employee copy2 is this: " + copy1);

            section("Attempt to save a detached employee");
            step06DetachedUpdate(employee);

            section("Attempt to update an existing, non-detached employee");
            step07UpdateExisting(employee2);

        } catch (Exception e) { // initially run the program with no 'catch' to demo try..finally
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            emf.close();
        }
    }

}
