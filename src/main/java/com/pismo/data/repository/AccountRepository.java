package com.pismo.data.repository;

import com.pismo.data.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsAccountEntitiesByDocumentNumber(String documentNumber);

}
