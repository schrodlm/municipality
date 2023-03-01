package cz.schrodlm.municipality.domain;

public interface DomainEntity<T> {

    T getCode();
    void setCode(T id);
}
