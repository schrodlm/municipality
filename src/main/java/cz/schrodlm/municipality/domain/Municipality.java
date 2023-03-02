package cz.schrodlm.municipality.domain;

import jakarta.persistence.*;

@Entity
@Table(name="municipality")
public class Municipality implements DomainEntity<Long>{

    public Municipality(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Municipality(){};

    @Id
    private Long code;

    @Column(name="name")
    private String name;

    @Override
    public Long getCode() {
        return code;
    }

    @Override
    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

}
