package com.kyberwara.gonote;

import android.arch.persistence.room.Room;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewNote extends AppCompatActivity {

    int categoryID;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        categoryID = getIntent().getIntExtra("categoryID", 0);

        setTitle("Add note");

        // Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the back button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24px);

        // Show keyboard
        imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    // Listener for toolbar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "Note discarded.", Toast.LENGTH_SHORT).show();

                // Hide keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                finish();
                return true;

            case R.id.done:
                // Get database
                Database db = Room.databaseBuilder(getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();

                // Get notes entity
                NotesEntity note = new NotesEntity();

                // Get content of text field
                EditText noteTitle = findViewById(R.id.noteTitle);
                EditText noteContent = findViewById(R.id.noteContent);
                String noteTitleText = noteTitle.getText().toString();
                String noteContentText = noteContent.getText().toString();

                note.setCategoryID(categoryID);
                note.setTitle(noteTitleText);
                note.setContent(noteContentText);

                // Insert to database
                if(noteTitleText.trim().length() != 0 && noteContentText.trim().length() != 0) {
                    db.NotesDAO().addNote(note);
                    Toast.makeText(getApplicationContext(), "Note added.", Toast.LENGTH_SHORT).show();

                    // Hide keyboard
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid entry.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override back button press
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Note discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item_toolbar_view, menu);
        return true;
    }
}