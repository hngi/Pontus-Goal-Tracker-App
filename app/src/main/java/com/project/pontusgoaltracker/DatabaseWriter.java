package com.project.pontusgoaltracker;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.Goal;
import com.project.pontusgoaltracker.models.GoalType;
import com.project.pontusgoaltracker.models.Task;
import com.project.pontusgoaltracker.models.User;


public abstract class DatabaseWriter {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    static FirebaseUser user = auth.getCurrentUser();
    static DatabaseReference userGoalsPath  = database.getReference("users/"+user.getUid()+"/userGoals");

    static final Goal DEFAULT_GOAL = new Goal("Default Goal",
            "this is the default goal created for all users of Pontus goal tracker",
            GoalType.GENERAL);



    static void writeUserToDatabase(User user) {
        //addDefaultGoalToUser(user);
        database.getReference().child("users").child( user.getuId() ).setValue(user);


    }
    static void writeGoalToUser(FirebaseUser user, Goal goal){

        userGoalsPath.child(goal.getGoalIdString()).setValue(goal);

    }
    private static void addDefaultGoalToUser(User user){
        //TODO: Create a class for constants like defaultGoal below
        user.addGoal(DEFAULT_GOAL);

    }

    static void deleteGoalFromUser(FirebaseUser user, Goal goal) {

       userGoalsPath.child(goal.getGoalIdString()).setValue(null);
    }

    static void deleteTaskFromGoal(Goal goal , Task task, int index){
        userGoalsPath.child(goal.getGoalIdString()).child("tasks")
                .child(String.valueOf(index)).setValue(null);
    }

}
