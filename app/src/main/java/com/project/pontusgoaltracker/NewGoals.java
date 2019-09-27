package com.project.pontusgoaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.Goal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewGoals extends AppCompatActivity {



    EditText Dates;
    Calendar myCalendar;


    EditText titleEdit;


    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goals);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_times_solid);
        getSupportActionBar().setTitle(null);


        final EditText edittext = new EditText(this);
        edittext.setPaddingRelative(8, 8, 8, 8);

        //Array that holds names for the list
        final ArrayList<String> items = new ArrayList<>();
        //Populating the task Spinner
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Goal Type");
        spinnerArray.add("General");
        spinnerArray.add("Religious");

       titleEdit =  findViewById(R.id.goal_title_edit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);
        Spinner goaltypes = findViewById(R.id.goal_type);
        goaltypes.setAdapter(adapter);
        myCalendar = Calendar.getInstance();


        final ListView list = findViewById(R.id.task);

        //Setting the adapter for list view
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.task_menu, R.id.checkedTextView, items);
        list.setAdapter(listAdapter);

        //First Alert Dialogue for FAB
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("New Task");
        alert.setTitle("Enter New Task");

        alert.setView(edittext);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String newTask = edittext.getText().toString();
                items.add(newTask);
                adapter.notifyDataSetChanged();

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
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView) view).getText().toString();
                Toast.makeText(NewGoals.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        //on long click for deleting items
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                        items.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert2.show();

                return true;
            }
        });


        Button button = findViewById(R.id.button2);
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

        Dates = findViewById(R.id.date);
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
        Dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewGoals.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        Dates.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.confirm:
                   Goal goal= new Goal(titleEdit.getText().toString() ) ;
                   DatabaseWriter.writeGoalToUser(user,goal);
                   finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}