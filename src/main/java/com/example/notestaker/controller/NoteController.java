package com.example.notestaker.controller;

import com.example.notestaker.model.request.CreateNoteRequest;
import com.example.notestaker.model.request.UpdateNoteRequest;
import com.example.notestaker.model.response.NoteResponse;
import com.example.notestaker.model.response.NoteAPIResponse;
import com.example.notestaker.service.NoteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<NoteAPIResponse<String>> createNote(@Valid @RequestBody CreateNoteRequest request,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            NoteAPIResponse<String> errorResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("400")
                    .noteAPIResponseMessage("Validation failed")
                    .noteAPIResponseData(errors.toString())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            noteService.createNote(request, userId);

            NoteAPIResponse<String> successResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Note created successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            NoteAPIResponse<String> errorResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<NoteAPIResponse<String>> updateNote(@Valid @RequestBody UpdateNoteRequest request,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            NoteAPIResponse<String> errorResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("400")
                    .noteAPIResponseMessage("Validation failed")
                    .noteAPIResponseData(errors.toString())
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }
//            System.out.println(userId);

            noteService.updateNote(request, userId);

            NoteAPIResponse<String> successResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Note updated successfully")
                    .build();

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            NoteAPIResponse<String> errorResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-notes")
    public ResponseEntity<NoteAPIResponse<Object>> getNotes() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            List<NoteResponse> notes = noteService.getNote(userId);

            NoteAPIResponse<Object> response = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Notes retrieved successfully")
                    .noteAPIResponseData(notes)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            NoteAPIResponse<Object> errorResponse = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-note-by-id/{noteId}")
    public ResponseEntity<NoteAPIResponse<Object>> getNoteById(@PathVariable Integer noteId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            NoteResponse note = noteService.getNoteById(userId, noteId);

            NoteAPIResponse<Object> response = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Notes retrieved successfully")
                    .noteAPIResponseData(note)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            NoteAPIResponse<Object> errorResponse = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<NoteAPIResponse<Object>> searchNotes(@RequestParam String query) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            List<NoteResponse> notes = noteService.getNoteByQuery(userId, query);

            NoteAPIResponse<Object> response = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Search completed")
                    .noteAPIResponseData(notes)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            NoteAPIResponse<Object> errorResponse = NoteAPIResponse.<Object>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<NoteAPIResponse<String>> deleteNote(@PathVariable Integer noteId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId;

            try {
                userId = Integer.parseInt(principal.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid user ID in token");
            }

            noteService.deleteNote(userId, noteId);

            NoteAPIResponse<String> response = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("200")
                    .noteAPIResponseMessage("Note deleted successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            NoteAPIResponse<String> errorResponse = NoteAPIResponse.<String>builder()
                    .noteAPIResponseCode("500")
                    .noteAPIResponseMessage("ERROR")
                    .noteAPIResponseData(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
