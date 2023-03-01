package com.example.municipality.domain;

import jakarta.persistence.*;

@Entity
@Table(name="municipality_part")
public class MunicipalityPart implements DomainEntity<Long>{

    /*
U obce stačí do DB vložit kód a název, u části obce kód, název a kód obce, ke které část obce patří

 */

    @Id
    private Long part_code;

    @Column(name = "name")
    private String name;

    @Column(name="municipality")
    @ManyToOne
    private Municipality municipality;


    @Override
    public Long getCode() {
        return null;
    }

    @Override
    public void setCode(Long id) {

    }
}
