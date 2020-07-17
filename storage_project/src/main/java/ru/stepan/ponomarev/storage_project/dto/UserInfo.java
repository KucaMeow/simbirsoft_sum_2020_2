package ru.stepan.ponomarev.storage_project.dto;

import lombok.*;

/**
 * Info of user accounts to be shown for user. Gets by jpa custom query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserInfo {
    /**
     * Username of user
     */
    String username;
    /**
     * Balance of user
     */
    double balance;
}
