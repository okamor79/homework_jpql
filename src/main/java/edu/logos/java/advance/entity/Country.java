package edu.logos.java.advance.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true, exclude = "cityList")
public class Country extends BaseEntity {

    @Column(name = "country_name")
    private String name;

    @OneToMany(mappedBy = "country")
    private List<City> cityList = new ArrayList<>();
}
