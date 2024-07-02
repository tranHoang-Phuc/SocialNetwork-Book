package com.phucth42.identity_service.repository;

import com.phucth42.identity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String>{
}
