package ru.stepan.ponomarev.storage_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Info of user accounts to be shown for user
 */
public class UserInfo {

    String username;
    float balance;
}
