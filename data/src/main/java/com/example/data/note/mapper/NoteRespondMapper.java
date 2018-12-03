package com.example.data.note.mapper;

import com.example.data.note.NoteEntity;
import com.example.data.note.repository.source.preference.response.NoteResponse;
import com.example.domain.note.NoteResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoteRespondMapper {

    @Inject
    public NoteRespondMapper() {

    }

    public List<NoteResult> transformFromList(List<NoteResponse> responseList) {
        List<NoteResult> resultList = null;
        if (!responseList.isEmpty()) {
            resultList = new ArrayList<NoteResult>();
            for (NoteResponse temp : responseList) {
                resultList.add(new NoteResult(temp.mId, temp.mTitle, temp.mDescription));
            }
        }
        return resultList;
    }

    public NoteResult transform(NoteResponse response) {
        NoteResult result = null;
        if(response !=null)
        {
            result = new NoteResult(response.mId, response.mTitle, response.mDescription);
        }
        return result;
    }

}
