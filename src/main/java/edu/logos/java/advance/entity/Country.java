package edu.logos.java.advance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
public class Country extends BaseEntity {

    @Column(name = "country_name")
    private String name;

    @OneToMany(mappedBy = "country", cascade = { CascadeType.ALL })
    private List<City> cityList = new ArrayList<>();
}
