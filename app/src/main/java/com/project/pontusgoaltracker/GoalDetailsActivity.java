package com.project.pontusgoaltracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.Goal;
import com.project.pontusgoaltracker.models.GoalType;
import com.project.pontusgoaltracker.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class GoalDetailsActivity extends AppCompatActivity {
    Intent intent;
    Goal goal ;
    EditText goalTitle ,goalDescription, dateTextView;
    TextView check;
    Button deleteGoalButton;
    ImageView calendarImage;
    Spinner goalTypeSpinner;
    ListView taskListView;
    ArrayList<Boolean> checkedTasks;
    Calendar myCalendar;
    CheckBox CheckBox1;
    ArrayList<String> taskItems;

//    String goalType= GoalType.G
    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference goalPath  = database.getReference("users/"+user.getUid()+"/userGoals/"+GoalListActivity.clickedGoal.getGoalIdString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_j_goal_details);
       Toolbar toolbar = findViewById(R.id.toolbar2);
       setSupportActionBar(toolbar);
       toolbar.setNavigationIcon(R.drawable.ic_times_solid);
       getSupportActionBar().setTitle(null);

        myCalendar = Calendar.getInstance();
        goal= GoalListActivity.clickedGoal;
        deleteGoalButton = findViewById(R.id.delete_goal_button);
        goalTitle= findViewById(R.id.title_edit_text);
        goalDescription = findViewById(R.id.description_edit_text);
        goalTypeSpinner = findViewById(R.id.goal_type_spinner);
        dateTextView= findViewById(R.id.date_text_view);
        check = findViewById(R.id.check);
        taskListView = findViewById(R.id.task_list_view);
        taskListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        deleteGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoal();
            }
        });

        fillGoalDetails();
        goal.setTaskSize(taskItems.size());
    }

    void fillGoalDetails(){
        populateGoalTypeSpinner();
        goalTitle.setText(goal.getTitle());
        goalDescription.setText((goal.getDescription()));
        dateTextView.setText(goal.getDeadline());
        populateTaskList();
        populateDateTextView();

    }

    void deleteGoal(){
       DatabaseWriter.deleteGoalFromUser(user,goal);
       finish();
    }

    void populateGoalTypeSpinner(){
        List<String> spinnerArray = new ArrayList<String>();
        int positionOfType=0;
        int count =0;

        for(String types : GoalType.allGoalTypes){

            if (goal.getType().equals(types))positionOfType=count;
            spinnerArray.add(types);
            count++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, spinnerArray);


        goalTypeSpinner.setAdapter(adapter);
        goalTypeSpinner.setSelection(positionOfType);

    }

    void populateTaskList(){
        //Array that holds tasks for the list
        taskItems = new ArrayList<>();
        for(Task task :goal.getTasks()){
                taskItems.add(task.getTitle());
        }

        //Setting the adapter for list view
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this, R.layout.task_list_item,
                R.id.checkbox,
                taskItems);
        CheckBox1 = findViewById(R.id.checkbox);


        taskListView.setAdapter(listAdapter);
        taskListView.setMinimumHeight(4);

        final EditText edittext = new EditText(this);
        edittext.setPaddingRelative(8, 8, 8, 8);




        //First Alert Dialogue for FAB
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("New Task");
        alert.setTitle("Enter New Task");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newTask = edittext.getText().toString();
                taskItems.add(newTask);
                listAdapter.notifyDataSetChanged();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        //Second Alert Dialogue for Deleting

        final AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
        alert2.setMessage("Delete Task?");
        alert2.setTitle("Delete");


        alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });


        //On click for list items
        taskListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView) view).getText().toString();
                //goalType=selectedItem;
                Toast.makeText(GoalDetailsActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        //on long click for deleting items
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                alert2.setMessage("Delete Task?");
                alert2.setTitle("Delete");


                alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        taskItems.remove(i);
                        listAdapter.notifyDataSetChanged();
                    }
                });
                alert2.show();

                return true;
            }
        });


        Button button = findViewById(R.id.add_task_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext.getParent() != null) {
                    ((ViewGroup) edittext.getParent()).removeView(edittext);
                    edittext.setText("");//
                }
                alert.show();
            }
        });

    }

    void populateDateTextView(){

        myCalendar = Calendar.getInstance();
        dateTextView = findViewById(R.id.date_text_view);
        // Creating a Date Picker Dialog
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GoalDetailsActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }



    private void updateLabel() {
        String myFormat = "dd-MMMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_new, menu);
        return super.onCreateOptionsMenu(menu);
   }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.confirm:

                String title = goalTitle.getText().toString();
                String description = goalDescription.getText().toString();
                String deadline = dateTextView.getText().toString();

                goal.setTitle(title);
                goal.setDeadline(deadline);
                goal.setDescription(description);
                //save tasks
                int count = 0;
                taskItems.size();
                int size = taskItems.size();
                goal.setTaskSize(size);
                for (int i = 0; i < taskListView.getChildCount(); i++) {
                    View v = taskListView.getChildAt(i);
                    if (v instanceof CheckBox) {
                            if (((CheckBox) v).isChecked()){
                                count ++;

                            }
                    }
                }
                goal.setCompletedTaskCount(count);

                //empty previous tasks
                goal.emptyTasks();
                //search through task items, and create task objects with each task string
                for(int x=0; x<taskItems.size();x++){
                    String taskString= taskItems.get(x);
                    goal.addTask(new Task(taskString));
                }

                goalPath.setValue(goal);

                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}






