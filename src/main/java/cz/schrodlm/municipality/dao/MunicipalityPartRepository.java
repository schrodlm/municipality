package cz.schrodlm.municipality.dao;

import cz.schrodlm.municipality.domain.Municipality;
import cz.schrodlm.municipality.domain.MunicipalityPart;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityPartRepository extends JpaRepository<MunicipalityPart, Long> {

}
