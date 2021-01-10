package com.example.odprojekt.repository;

import com.example.odprojekt.entity.Note;
import com.example.odprojekt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findByUser(User user);
}
