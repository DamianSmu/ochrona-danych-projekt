package com.example.odprojekt.repository;

import com.example.odprojekt.entity.PublicNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicNoteRepository extends JpaRepository<PublicNote, String> {
}
