package com.example.userService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public ERole getName() {
        return name;
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role() { }

    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
