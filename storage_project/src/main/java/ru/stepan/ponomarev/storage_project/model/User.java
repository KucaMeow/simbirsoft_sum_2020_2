package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * User class
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    /**
     * User id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "username")
    String username;
    @Column(name = "password_hash")
    String hashedPassword;

    /**
     * Current user balance
     */
    @Column(name = "balance")
    double balance;

    /**
     * All saver orders of user
     */
    @OneToMany(mappedBy = "customer")
    List<Order> orders;
}
