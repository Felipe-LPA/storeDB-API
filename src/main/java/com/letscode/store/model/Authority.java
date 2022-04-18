package com.letscode.store.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "authorities")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

    @EmbeddedId
    private AuthorityKey authorityKey;

    @ManyToOne
    @MapsId("userName")
    @JoinColumn(name = "username")
    private User user;


}
