package com.example.notestaker.repository.database.implementation;

import com.example.notestaker.mapper.NoteMapper;
import com.example.notestaker.model.entity.Note;
import com.example.notestaker.model.response.NoteResponse;
import com.example.notestaker.repository.database.interfaces.NoteRepository;
import com.example.notestaker.repository.database.query.NoteQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteRepositoryImpl implements NoteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NoteRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createNote(Note note) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("noteTitle", note.getNoteTitle())
                .addValue("noteContent", note.getNoteContent())
                .addValue("noteUserId", note.getNoteUserId());

        jdbcTemplate.update(NoteQuery.CREATE_NOTE, params);
    }

    @Override
    public void updateNote(Note note) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("noteId", note.getNoteId())
                .addValue("noteTitle", note.getNoteTitle())
                .addValue("noteUserId", note.getNoteUserId())
                .addValue("noteContent", note.getNoteContent());

        jdbcTemplate.update(NoteQuery.UPDATE_NOTE, params);
    }

    @Override
    public List<NoteResponse> getNote(Integer noteUserId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("noteUserId", noteUserId);

        return jdbcTemplate.query(NoteQuery.GET_MY_NOTES, params,
                new NoteMapper());
    }

    @Override
    public NoteResponse getNoteById(Integer noteUserId, Integer noteId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("noteId", noteId)
                .addValue("noteUserId", noteUserId);

        return jdbcTemplate.queryForObject(NoteQuery.GET_NOTE_BY_ID, params,
                new NoteMapper());
    }

    @Override
    public List<NoteResponse> getNoteByQuery(String query, Integer noteUserId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("query", query)
                .addValue("noteUserId", noteUserId);

        return jdbcTemplate.query(NoteQuery.GET_MY_NOTES_BY_QUERY, params,
                new NoteMapper());
    }

    @Override
    public void deleteNote(Integer noteUserId, Integer noteId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("noteUserId", noteUserId)
                .addValue("noteId", noteId);

        jdbcTemplate.update(NoteQuery.DELETE_NOTE, params);
    }
}
