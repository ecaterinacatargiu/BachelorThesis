package com.example.wally.repository;


import com.example.wally.domain.SimpleUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleUserRepository extends SystemRepository<SimpleUser, Long>{

    @Query("SELECT suser FROM SimpleUser suser WHERE suser.email=?1")
    Optional<SimpleUser> findByEmail(String email);

}
