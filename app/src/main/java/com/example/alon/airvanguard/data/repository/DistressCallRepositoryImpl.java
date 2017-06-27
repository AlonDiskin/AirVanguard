package com.example.alon.airvanguard.data.repository;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * {@link DistressCallRepository} implementation class.
 */

public class DistressCallRepositoryImpl implements DistressCallRepository {

    private static final String LOG_TAG = DistressCallRepositoryImpl.class.getSimpleName();
    private DatabaseReference mDatabase;
    private Map<DistressCallListener,ChildEventListener> mListenersMap = new HashMap<>();

    @Inject
    public DistressCallRepositoryImpl(@NonNull DatabaseReference database) {
        mDatabase = database;
    }

    @Override
    public void addDistressCallListener(@NonNull DistressCallListener listener) {
        if (!mListenersMap.containsKey(listener)) {
            ChildEventListener childEventListener = createChildEventListener(listener);
            mListenersMap.put(listener,childEventListener);
            mDatabase.addChildEventListener(childEventListener);
        }
    }

    @Override
    public void removeDistressCallListener(@NonNull DistressCallListener listener) {
        if (mListenersMap.containsKey(listener)) {
            mDatabase.removeEventListener(mListenersMap.get(listener));
            mListenersMap.remove(listener);
        }
    }

    @Override
    public void getAll(@NonNull LoadCallsSuccess successCb, @NonNull LoadCallsFailure failureCb) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DistressCall> calls = new ArrayList<>((int)dataSnapshot.getChildrenCount());

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    calls.add(dsp.getValue(DistressCall.class));
                }

                successCb.onLoad(calls);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                failureCb.onFailure(databaseError.toException());
            }
        });
    }

    private ChildEventListener createChildEventListener(@NonNull DistressCallListener listener) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listener.onCallAdded(dataSnapshot.getValue(DistressCall.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listener.onCallRemoved(dataSnapshot.getValue(DistressCall.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
