package com.example.notestaker.service;

import com.example.notestaker.mapper.NoteRequestMapper;
import com.example.notestaker.model.entity.Note;
import com.example.notestaker.model.request.CreateNoteRequest;
import com.example.notestaker.model.request.UpdateNoteRequest;
import com.example.notestaker.model.response.NoteResponse;
import com.example.notestaker.model.response.UserResponse;
import com.example.notestaker.repository.database.interfaces.NoteRepository;
import com.example.notestaker.repository.database.interfaces.UserRepository;
import com.example.notestaker.utils.validator.NoteValidator;
import com.example.notestaker.utils.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteRequestMapper noteRequestMapper;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository, NoteRequestMapper noteRequestMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteRequestMapper = noteRequestMapper;
    }

    public void createNote(CreateNoteRequest request, Integer userId) {
        if (request == null) throw new IllegalArgumentException("Note Request cannot be null");

//        Integer noteUserId = request.getNoteUserId();
        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        Note note = noteRequestMapper.toEntity(request);
        note.setNoteUserId(userId);
        noteRepository.createNote(note);
    }

    public void updateNote(UpdateNoteRequest request, Integer userId) {
        if (request == null) throw new IllegalArgumentException("Note Request cannot be null");

        UserResponse user = userRepository.getUserById(userId);
        UserValidator.validate(user);

        NoteResponse response = noteRepository.getNoteById(userId, request.getNoteId());
        NoteValidator.validate(response, userId);

        Note note = noteRequestMapper.toEntity(request);
        note.setNoteUserId(userId);
        noteRepository.updateNote(note);
    }

    public List<NoteResponse> getNote(Integer noteUserId) {
        if (noteUserId == null) throw new IllegalArgumentException("User Id cannot be null");

        UserResponse user = userRepository.getUserById(noteUserId);
        UserValidator.validate(user);

        List<NoteResponse> responseList = noteRepository.getNote(noteUserId);
        for (NoteResponse response : responseList) {
            NoteValidator.validate(response, noteUserId);
        }
        return responseList;
    }

    public NoteResponse getNoteById(Integer noteUserId, Integer noteId) {
        if (noteUserId == null) throw new IllegalArgumentException("User Id cannot be null");
        if (noteId == null) throw new IllegalArgumentException("Note Id cannot be null");

        UserResponse user = userRepository.getUserById(noteUserId);
        UserValidator.validate(user);

        NoteResponse response = noteRepository.getNoteById(noteUserId, noteId);
        NoteValidator.validate(response, noteUserId);

        return response;
    }

    public List<NoteResponse> getNoteByQuery(Integer noteUserId, String query) {
        if (noteUserId == null) throw new IllegalArgumentException("User Id cannot be null");

        UserResponse user = userRepository.getUserById(noteUserId);
        UserValidator.validate(user);

        // If query is empty, return all notes instead of searching
        if (query == null || query.trim().isEmpty()) {
            return getNote(noteUserId); // fallback to full list
        }

        List<NoteResponse> responseList = noteRepository.getNoteByQuery(query, noteUserId);
        for (NoteResponse response : responseList) {
            NoteValidator.validate(response, noteUserId);
        }
        return responseList;
    }

    public void deleteNote(Integer noteUserId, Integer noteId) {
        if (noteUserId == null) throw new IllegalArgumentException("User Id cannot be null");

        UserResponse user = userRepository.getUserById(noteUserId);
        UserValidator.validate(user);

        NoteResponse note = noteRepository.getNoteById(noteUserId, noteId);
        NoteValidator.validate(note, noteUserId);

        noteRepository.deleteNote(noteUserId,  noteId);
    }


}
