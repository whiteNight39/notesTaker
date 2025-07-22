package com.example.notestaker.mapper;

import com.example.notestaker.model.entity.Note;
import com.example.notestaker.model.request.CreateNoteRequest;
import com.example.notestaker.model.request.UpdateNoteRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteRequestMapper {

    Note toEntity(CreateNoteRequest createNoteRequest);
    Note toEntity(UpdateNoteRequest updateNoteRequest);
}
