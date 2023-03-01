package cz.schrodlm.municipality.dao;

import cz.schrodlm.municipality.domain.Municipality;
import cz.schrodlm.municipality.domain.MunicipalityPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
}
