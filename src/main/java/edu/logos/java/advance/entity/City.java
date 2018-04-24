package edu.logos.java.advance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true, exclude = {"country","userList"})
public class City extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany
    @JoinTable(name = "user_city", joinColumns = @JoinColumn(name = "city_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList = new ArrayList<>();

}
