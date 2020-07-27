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
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password_hash")
    private String hashedPassword;

    /**
     * Current user balance
     */
    @Column(name = "balance")
    private double balance;

    /**
     * All saver orders of user
     */
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    /**
     * Role of user
     */
    @Enumerated(EnumType.STRING)
    private Role role;
}
