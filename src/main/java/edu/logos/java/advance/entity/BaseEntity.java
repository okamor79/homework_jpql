package edu.logos.java.advance.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @CreationTimestamp
//    @Column(name = "created")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date created;
}
