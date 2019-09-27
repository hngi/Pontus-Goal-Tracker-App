package com.project.pontusgoaltracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pontusgoaltracker.models.Goal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GoalListActivity extends AppCompatActivity {
    Context context = this;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    private RecyclerView goalRecyclerView;
    TextView goalTitle ;
    private GoalAdapter mAdapter;
    List<Goal> userGoals ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database= FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference userGoalsPath  = database.getReference("users/"+user.getUid()+"/userGoals");

        setContentView(R.layout.activity_goal_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Goals List");

        ProgressBar progressBar3 = findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.VISIBLE);
        userGoals = new ArrayList<>();
        goalRecyclerView = findViewById(R.id.goal_list_recycler);
        goalRecyclerView.setLayoutManager(new LinearLayoutManager(GoalListActivity.this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GoalListActivity.this,NewGoals.class);
                startActivity(i);
            }
        });
        Log.w("*********************", "before entering" + user.getUid());


        userGoalsPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Goal object and use the values to update the UI
                userGoals = new ArrayList<Goal>();
                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {
                    userGoals.add(snapshotNode.getValue(Goal.class));
                }
                progressBar3.setVisibility(View.GONE);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadGoals:onCancelled", databaseError.toException());
                // ...
            }
        });



    }


    private void updateUI() {
        //GET LIST OF GOALS ATTACHED TO THIS USER and add it to recycler view
        mAdapter = new GoalAdapter(userGoals);
        goalRecyclerView.setAdapter(mAdapter);
    }



    public void launchSignInPage(){
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
        finish();
    }

    private class GoalAdapter extends RecyclerView.Adapter<GoalHolder> {
        private List<Goal> goals;

         GoalAdapter(List<Goal> goals) {
            this.goals = goals;
        }

        @Override
        public GoalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            return new GoalHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(GoalHolder holder, int position) {
            Goal goal = goals.get(position);
            holder.bind(goal);
        }
        @Override
        public int getItemCount() {
            return goals.size();
        }


    }

    private class GoalHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        GoalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.goal_list_item, parent, false));
            goalTitle= itemView.findViewById(R.id.goal_Title);
            itemView.setOnClickListener(this);
        }

        private Goal goal;

        void bind(Goal goal) {
            this.goal = goal;
            goalTitle.setText(goal.getTitle());

        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view, "snackbar: the goal detail activity would launch", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if( user==null){
            Intent intent = new Intent(this , SignIn.class);
            startActivity(intent);
            finish();

        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.goal_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout_item:
                mAuth.signOut();
                launchSignInPage();
                return super.onOptionsItemSelected(item);

            default: return super.onOptionsItemSelected(item);

        }
    }




}
