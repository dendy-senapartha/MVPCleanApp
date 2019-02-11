package com.example.data.note.repository.source.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.example.data.Serializer;
import com.example.data.base.UnInitializedFirebaseException;
import com.example.data.base.UnInitializedSecuredPreferencesException;
import com.example.data.note.NoteEntity;
import com.example.data.note.repository.source.preference.response.NoteResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;


/*
 * Created by dendy-prtha on 04/02/2019.
 * Network source of note table using firebase as example.
 */

@Singleton
public class NoteNetwork {
    private final Context context;

    private final Serializer serializer;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Inject
    public NoteNetwork(Context context, Serializer serializer) {
        this.context = context;
        this.serializer = serializer;
    }

    public void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void saveNote(ObservableEmitter<NoteResponse> emitter, NoteEntity noteEntity)
            throws UnInitializedFirebaseException {
        //add new note
        final String userId = mAuth.getCurrentUser().getUid();
        String key = mDatabase.child("user-notes").child("user-notes").child(userId).child(noteEntity.getId()).push().getKey();

        mDatabase.child("user-notes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*search the notes if available. by default avalible key is set as a key*/
                String availableKey = key;
                for (DataSnapshot notes : dataSnapshot.getChildren()) {
                    NoteEntity note = notes.getValue(NoteEntity.class);
                    if (note != null && note.getId().equalsIgnoreCase(noteEntity.getId())) {
                        availableKey = notes.getKey();
                        break;
                    }
                }
                Exception exception = mDatabase.child("user-notes").child(userId).child(availableKey).
                        setValue(noteEntity).getException();
                if (exception == null) {
                    emitter.onNext(new NoteResponse(noteEntity.getId(), noteEntity.getTitle(), noteEntity.getDescription()));
                } else {
                    emitter.onError(exception);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        });

    }

    public void getNotes(ObservableEmitter<List<NoteResponse>> emitter) throws UnInitializedFirebaseException {
        final String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("user-notes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NoteResponse> listResponse = new ArrayList<>();
                for (DataSnapshot notes : dataSnapshot.getChildren()) {
                    NoteEntity note = notes.getValue(NoteEntity.class);
                    listResponse.add(new NoteResponse(note.getId(), note.getTitle(), note.getDescription()));
                }
                emitter.onNext(listResponse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        });
    }

    public void getNote(ObservableEmitter<NoteResponse> emitter, String mId) throws UnInitializedFirebaseException {
        final String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("user-notes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NoteResponse noteResponse = null;
                for (DataSnapshot notes : dataSnapshot.getChildren()) {
                    NoteEntity note = notes.getValue(NoteEntity.class);
                    if (note.getId().equalsIgnoreCase(mId)) {
                        noteResponse = new NoteResponse(note.getId(), note.getTitle(), note.getDescription());
                        break;
                    }
                }
                emitter.onNext(noteResponse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        });
    }

    public void deleteNote(ObservableEmitter<ArrayList<String>> emitter, ArrayList<String> listIdNotes)
            throws UnInitializedFirebaseException {
        final String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("user-notes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> removedMidNotes = new ArrayList<>();
                for (DataSnapshot notes : dataSnapshot.getChildren()) {
                    String key = notes.getKey();
                    NoteEntity note = notes.getValue(NoteEntity.class);
                    for (String idNote : listIdNotes) {
                        if (note.getId().equalsIgnoreCase(idNote)) {
                            Exception exception = mDatabase.child("user-notes").child(userId).child(key).removeValue().getException();
                            if (exception == null)
                            {
                                removedMidNotes.add(idNote);
                            }
                            else
                            {
                                emitter.onError(exception);
                            }
                        }
                    }
                }
                emitter.onNext(removedMidNotes);
            }

            //emitter.onNext(noteResponse);
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        });
    }

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnected());
        }

        return isConnected;
    }
}