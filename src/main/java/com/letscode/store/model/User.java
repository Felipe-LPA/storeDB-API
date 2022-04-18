package com.letscode.store.model;

import com.letscode.store.dto.UserDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enable;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();

    public static User convert(UserDTO userDTO) {
        return User.builder()
                .userName(userDTO.getUsername())
                .password(userDTO.getPassword())
                .enable(userDTO.getEnable())
                .build();
    }


}
