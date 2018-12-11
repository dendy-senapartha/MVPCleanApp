package com.example.data.note.repository.source;

import com.example.data.AbstractEntityDataFactory;
import com.example.data.note.repository.source.preference.NotePreferences;
import com.example.data.note.repository.source.preference.PreferenceNoteEntityData;

import javax.inject.Inject;
import javax.inject.Singleton;
/**
 * Note data factory. Used to retrieve or push data to database.
 * In this example, we use Shared Preferences only
 * **/
@Singleton
public class NoteEntityDataFactory extends AbstractEntityDataFactory<NoteEntityData>{
    private final NotePreferences notePreferences;

    @Inject
    public NoteEntityDataFactory(NotePreferences notePreferences) {
        this.notePreferences = notePreferences;
    }

    @Override
    public NoteEntityData createData(String source) {
        return new PreferenceNoteEntityData(notePreferences);
    }
}
