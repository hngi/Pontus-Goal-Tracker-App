package com.project.pontusgoaltracker;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pontusgoaltracker.models.Goal;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

 abstract class DatabaseReader {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseUser user = auth.getCurrentUser();

    private static DatabaseReference userGoalsPath  = database.getReference("users/"+user.getUid()+"/userGoals");



     static void readGoalsFromUser(){
        userGoalsPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                HashMap<String , Object> map = new HashMap<>();
                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {
                    map.put(snapshotNode.getKey(), snapshotNode.getValue(Goal.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }







}
