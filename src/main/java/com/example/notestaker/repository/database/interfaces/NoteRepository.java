package com.example.notestaker.repository.database.interfaces;

import com.example.notestaker.model.entity.Note;
import com.example.notestaker.model.response.NoteResponse;

import java.util.List;

public interface NoteRepository {

    void createNote(Note note);
    void updateNote(Note note);
    List<NoteResponse> getNote(Integer noteUserId);
//    List<NoteResponse> getUserNoteById(Integer noteUserId, Integer noteId);
    NoteResponse getNoteById(Integer noteUserId, Integer noteId);
    List<NoteResponse> getNoteByQuery(String query, Integer noteUserId);
    void deleteNote(Integer noteUserId, Integer noteId);
}