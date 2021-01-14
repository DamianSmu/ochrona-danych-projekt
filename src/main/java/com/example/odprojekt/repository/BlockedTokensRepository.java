package com.example.odprojekt.repository;

import com.example.odprojekt.entity.BlockedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlockedTokensRepository extends JpaRepository<BlockedToken, String> {
    List<BlockedToken> findByUsernameAndDateAfter(String username, Date date);
}
