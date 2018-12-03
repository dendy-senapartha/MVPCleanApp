package com.example.dendy_s784.mvccleanapptemplate.mapper;


import com.example.dendy_s784.mvccleanapptemplate.model.Note;
import com.example.domain.note.NoteResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NotesResultMapper {

    @Inject
    public NotesResultMapper() {

    }

    public List<Note>  transformFromList (List<NoteResult> resultList) {
        List<Note> noteList = null;
        if (!resultList.isEmpty()) {
            noteList = new ArrayList<Note>();
            for (NoteResult temp : resultList) {
                noteList.add(new Note(temp.getmId(), temp.getmTitle(), temp.getmDescription()));
            }
        }
        return noteList;
    }

    public Note transform (NoteResult result) {
        Note note = null;
        if (result !=null) {
            note = new Note(result.getmId(), result.getmTitle(), result.getmDescription());
        }
        return note;
    }

}
