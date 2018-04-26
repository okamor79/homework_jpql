package edu.logos.java.advance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "full_name", length = 150)
    private String fullName;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "city_id")
    private City city;
}
