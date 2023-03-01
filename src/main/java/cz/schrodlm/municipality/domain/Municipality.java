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





    /*
    U obce stačí do DB vložit kód a název, u části obce kód, název a kód obce, ke které část obce patří

     */

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
