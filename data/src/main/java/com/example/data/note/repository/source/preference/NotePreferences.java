package com.example.data.note.repository.source.preference;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.data.BuildConfig;
import com.example.data.Druther;
import com.example.data.Serializer;
import com.example.data.base.UnInitializedSecuredPreferencesException;
import com.example.data.note.NoteEntity;
import com.example.data.note.repository.source.preference.response.NoteResponse;
import com.example.domain.note.NoteResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
/**
 * Class used to save note preferences using SharedPreferences engine
 * **/
@Singleton
public class NotePreferences {

    private static final String NOTE_PREFERENCES = "NotePreferences" +
            BuildConfig.FLAVOR;
    private final Context context;

    private final Serializer serializer;

    private Druther druther;

    @Inject
    public NotePreferences(Context context, Serializer serializer) {
        this.context = context;
        this.serializer = serializer;
    }

    public void init(String key) {
        druther = new Druther.Builder(context)
                .setPreferenceGroup(NOTE_PREFERENCES)
                .setSerializerFacade(serializer)
                .setPassword(key)
                .build();
    }

    public void saveNote(String mid, String mTitle, String mDescription)
            throws UnInitializedSecuredPreferencesException {
        NoteEntity noteEntity = new NoteEntity(mid, mTitle, mDescription);
        saveNote(noteEntity);
    }

    public void saveNote(NoteEntity noteEntity) throws UnInitializedSecuredPreferencesException {
        initChecking();
        //get available notes list first
        List<JSONObject> noteList= druther.getObject(Key.NOTES, ArrayList.class);
        List<NoteResponse> listResponse = null;
        boolean noteIsAvailable = false;
        //parse it
        if(noteList != null)
        {
            TypeReference<List<NoteResponse>> typeRef = new TypeReference<List<NoteResponse>>() {};
            listResponse = JSON.parseObject(noteList.toString(), typeRef);
            //check if noteEntity already in DB
            for(int i=0; i<listResponse.size(); i++)
            {
                if(listResponse.get(i).mId.equalsIgnoreCase(noteEntity.getId()))
                {
                    //if yes, then add it
                    noteIsAvailable = true;
                    listResponse.get(i).mTitle = noteEntity.getTitle();
                    listResponse.get(i).mDescription = noteEntity.getDescription();
                }
            }
        }

        //if not, then add it
        if(!noteIsAvailable)
        {
            if(listResponse==null)
            {
                listResponse = new ArrayList<NoteResponse>();
            }
            //append note to the list
            listResponse.add(new NoteResponse(noteEntity.getId(),noteEntity.getTitle(), noteEntity.getDescription()));
        }
        //save again
        druther.saveData(Key.NOTES, listResponse);
    }

    public List<NoteResponse> getNotes() throws UnInitializedSecuredPreferencesException {
        initChecking();
        List<JSONObject> listJson = druther.getObject(Key.NOTES, ArrayList.class);
        //parse it into actual noteresponse
        TypeReference<List<NoteResponse>> typeRef = new TypeReference<List<NoteResponse>>() {};
        //https://github.com/alibaba/fastjson/wiki/UserGuid
        List<NoteResponse> listResponse= JSON.parseObject(listJson.toString(), typeRef);
        return listResponse;
    }

    private void initChecking() throws UnInitializedSecuredPreferencesException {
        if (druther == null) {
            throw new UnInitializedSecuredPreferencesException();
        }
    }

    public NoteResponse getNote(String mId)  throws UnInitializedSecuredPreferencesException  {
        initChecking();
        NoteResponse response = null;
        List<JSONObject> listJson = druther.getObject(Key.NOTES, ArrayList.class);
        TypeReference<List<NoteResponse>> typeRef = new TypeReference<List<NoteResponse>>() {};
        for (NoteResponse temp : JSON.parseObject(listJson.toString(), typeRef)) {
            if(temp.mId.toString().equalsIgnoreCase(mId))
            {
                response = new NoteResponse(temp.mId.toString(),temp.mTitle.toString(),temp.mDescription.toString());
            }
        }
        return response;
    }

    public ArrayList<String>  deleteNote(ArrayList<String>  midNotes)  throws UnInitializedSecuredPreferencesException  {
        initChecking();
        //get available notes list first
        List<JSONObject> noteList= druther.getObject(Key.NOTES, ArrayList.class);
        List<NoteResponse> listResponse = null;
        boolean noteIsAvailable = false;
        ArrayList<String>  removedMidNotes = new ArrayList<>();
        //parse it
        if(noteList != null)
        {
            TypeReference<List<NoteResponse>> typeRef = new TypeReference<List<NoteResponse>>() {};
            listResponse = JSON.parseObject(noteList.toString(), typeRef);
            //check if noteEntity already in DB
            for(int i=0; i<midNotes.size(); i++)
            {
                for(int j=0; j<listResponse.size(); j++)
                {
                    if(listResponse.get(j).mId.equalsIgnoreCase(midNotes.get(i)))
                    {
                        //if yes, then add it
                        noteIsAvailable = true;
                        //send message the id if note are found and ready to delete
                        removedMidNotes.add(listResponse.get(j).mId);
                        listResponse.remove(j);
                    }
                }
            }
        }

        //if not, then add it
        if(!noteIsAvailable)
        {
            if(listResponse==null)
            {
                listResponse = new ArrayList<NoteResponse>();
            }
            //this is the error message
            //removedMidNotes = "not found";
        }
        //save again
        druther.saveData(Key.NOTES, listResponse);
        return removedMidNotes;
    }

    private static class Key {

        private static final String NOTES = "notes";
    }
}
