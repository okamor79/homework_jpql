package edu.logos.java.advance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true,exclude = "cityList")
public class User extends BaseEntity {

    @Column(name = "full_name", length = 150)
    private String fullName;

    private int age;

    @ManyToMany
    @JoinTable(name = "user_city", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
    private List<City> cityList = new ArrayList<>();
}
