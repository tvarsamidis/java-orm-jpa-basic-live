package gr.codehub.jpa.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees4")
@Data
@NoArgsConstructor
public class Employee4 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
        name = "employee4_department4",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department4> departments;

    public Employee4(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Implement here to avoid recursion of @ToString
    @Override
    public String toString() {
        return "Employee4{" + "id=" + id + ", firstName=" + firstName + ", lastName=" 
                + lastName + ", email=" + email + ", departments=" + departments.size() + '}';
    }
}

