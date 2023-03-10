package cz.schrodlm.municipality.domain;

import jakarta.persistence.*;

@Entity
@Table(name="municipality_part")
public class MunicipalityPart implements DomainEntity<Long>{

    public MunicipalityPart(Long code, String name, String municipality_code) {
        this.code = code;
        this.name = name;
        this.municipality_code = municipality_code;
    }

    public MunicipalityPart(){};

    @Id
    private Long code;

    @Column(name = "name")
    private String name;

    @Column(name = "municipality_code")
    private String municipality_code;


    @Override
    public Long getCode() {return code;}

    @Override
    public void setCode(Long code) {this.code = code;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality_code() {
        return municipality_code;
    }

    public void setMunicipality_code(String municipality_code) {
        this.municipality_code = municipality_code;
    }
}
