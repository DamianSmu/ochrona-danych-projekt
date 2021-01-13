package com.example.odprojekt.controller;

import com.example.odprojekt.entity.Note;
import com.example.odprojekt.entity.PublicNote;
import com.example.odprojekt.entity.User;
import com.example.odprojekt.payload.request.NoteRequest;
import com.example.odprojekt.repository.NoteRepository;
import com.example.odprojekt.repository.PublicNoteRepository;
import com.example.odprojekt.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final PublicNoteRepository publicNoteRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository, PublicNoteRepository publicNoteRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.publicNoteRepository = publicNoteRepository;
    }

    @GetMapping(path = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllByAuthenticated(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(noteRepository.findByUser(user));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping(path = "/", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<?> getAll(Authentication authentication) {
//        return ResponseEntity.ok(noteRepository.findAll());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(Authentication authentication, @PathVariable String id) {
        String username = authentication.getName();
        Note note = noteRepository.findById(id).get();
        if (!note.getUser().getUsername().equals(username)) {
            return ResponseEntity.badRequest().body("Parcel does not belong to authenticated user.");
        }
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getOne(Authentication authentication, @PathVariable String id) {
        String username = authentication.getName();
        Note note = noteRepository.findById(id).get();
        if (!note.getUser().getUsername().equals(username)) {
            return ResponseEntity.badRequest().body("Parcel does not belong to authenticated user.");
        }
        return ResponseEntity.ok(note);
    }

    @PostMapping(path = "/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> addNote(Authentication authentication, @RequestBody NoteRequest parcelRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(noteRepository.save(new Note(user, parcelRequest.getBody())));
    }

    //PUBLIC NOTES

    @GetMapping(path = "/public", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getPublicAll(Authentication authentication) {
        return ResponseEntity.ok(publicNoteRepository.findAll());
    }

    @DeleteMapping("/public/{id}")
    public ResponseEntity<?> deletePublicNote(Authentication authentication, @PathVariable String id) {
        String username = authentication.getName();
        PublicNote note = publicNoteRepository.findById(id).get();
        if (!note.getUser().getUsername().equals(username)) {
            return ResponseEntity.badRequest().body("Parcel does not belong to authenticated user.");
        }
        publicNoteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/public/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getPublicOne(Authentication authentication, @PathVariable String id) {
        PublicNote note = publicNoteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
        return ResponseEntity.ok(note);
    }

    @PostMapping(path = "/public", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> addPublicNote(Authentication authentication, @RequestBody NoteRequest parcelRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(publicNoteRepository.save(new PublicNote(user, parcelRequest.getBody())));
    }

}