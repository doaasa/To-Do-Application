package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText todoEdt, todoIdEdt;
    Button saveBtn, deleteBtn;
    TextView todoListTxt;
    ToDoListDBAdapter toDoListDBAdapter;
    List<Todo> todoList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
                case R.id.refresh :
                    finish();
                    startActivity(getIntent());
                  break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoEdt = findViewById(R.id.create_edt);
        todoIdEdt = findViewById(R.id.todo_id_text);

        saveBtn = findViewById(R.id.create_btn);
        deleteBtn = findViewById(R.id.delete_btn);

        todoListTxt = findViewById(R.id.retrieve_txt);

        toDoListDBAdapter = ToDoListDBAdapter.getToDoListDBAdapterInstance(getApplicationContext());
        todoList = toDoListDBAdapter.getALLTodos();
        updateTextView();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toDoListDBAdapter.insertNewDataInsideTable(todoEdt.getText().toString())) {
                    updateTextView();
                    Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int todoId = Integer.valueOf(todoIdEdt.getText().toString());
                toDoListDBAdapter.delete(todoId);
                updateTextView();

            }
        });




     final ImageButton btnToggleDark;
        btnToggleDark
                = findViewById(R.id.btnToggleDark);
        final SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
              }

        btnToggleDark.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {

                        if (isDarkModeOn) {

                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);

                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                        }
                        else {


                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                               editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();


                        }
                    }
                });
    }


      private String getToDoListString ()
    {
        todoList = toDoListDBAdapter.getALLTodos();
        if (todoList != null && todoList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Todo todo : todoList) {
                stringBuilder.append(todo.getId() + " . " + todo.getToDo() + "\n");
            }
            return stringBuilder.toString();
        } else {
            return "Database is Empty!!";
        }
    }
    private void updateTextView () {
        todoListTxt.setText(getToDoListString());
    }





}