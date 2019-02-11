package com.example.data.note.repository.source;

import com.example.data.AbstractEntityDataFactory;
import com.example.data.Source;
import com.example.data.note.repository.source.network.NetworkNoteEntityData;
import com.example.data.note.repository.source.network.NoteNetwork;
import com.example.data.note.repository.source.preference.NotePreferences;
import com.example.data.note.repository.source.preference.PreferenceNoteEntityData;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Note data factory. Used to retrieve or push data to database.
 * In this example, we use Shared Preferences only
 **/
@Singleton
public class NoteEntityDataFactory extends AbstractEntityDataFactory<NoteEntityData> {
    private final NotePreferences notePreferences;
    private final NoteNetwork noteNetwork;

    @Inject
    public NoteEntityDataFactory(NotePreferences notePreferences, NoteNetwork noteNetwork) {
        this.notePreferences = notePreferences;
        this.noteNetwork = noteNetwork;
    }

    @Override
    public NoteEntityData createData(String source) {
        NoteEntityData noteEntityData = null;
        switch (source) {
            case Source.LOCAL:
                noteEntityData = new PreferenceNoteEntityData(notePreferences);
                break;
            case Source.NETWORK:
                noteEntityData = new NetworkNoteEntityData(noteNetwork);
                break;
            case Source.MOCK:
                break;
        }
        return noteEntityData;
    }
}
