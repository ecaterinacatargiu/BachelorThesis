package com.example.wally.repository;


import com.example.wally.domain.SimpleUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleUserRepository extends SystemRepository<SimpleUser, Long>{

    @Query("SELECT suser FROM SimpleUser suser WHERE suser.email=?1")
    Optional<SimpleUser> findByEmail(String email);

    @Query("SELECT u.ID from SimpleUser u where u.email=?1")
    Long getIdByEmail(String email);

    @Query("SELECT su.balance from SimpleUser su where su.ID=?1")
    Double getBalanceForUser(Long id);

}
