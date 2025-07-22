package com.example.notestaker.utils.validator;

import com.example.notestaker.model.entity.Note;
import com.example.notestaker.model.response.NoteResponse;

public class NoteValidator {

    public static void validate(Note note, Integer noteUserId) {

        if (note == null) {
            throw new IllegalArgumentException("Note not found");
        }

        if (!note.getNoteUserId().equals(noteUserId)) {
            throw new IllegalArgumentException("You are not allowed to access this note");
        }
    }

    public static void validate(NoteResponse note, Integer noteUserId) {

        if (note == null) {
            throw new IllegalArgumentException("Note not found");
        }

        if (!note.getNoteUserId().equals(noteUserId)) {
            throw new IllegalArgumentException("You are not allowed to access this note");
        }
    }
}
