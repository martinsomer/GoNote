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

public class AddNewCategory extends AppCompatActivity {

    EditText categoryName;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        setTitle("Add category");

        // Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the back button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24px);

        // Get content of text field
        categoryName = findViewById(R.id.categoryName);

        // Show keyboard by default
        categoryName.requestFocus();

        // Show keyboard
        imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    // Listener for toolbar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "Entry discarded.", Toast.LENGTH_SHORT).show();

                // Hide keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                finish();
                return true;

            case R.id.done:
                // Get database
                Database db = Room.databaseBuilder(getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();

                // Get categories entity
                CategoriesEntity category = new CategoriesEntity();

                String categoryNameText = categoryName.getText().toString();
                category.setCategory(categoryNameText);

                // Insert to database
                if(categoryNameText.trim().length() != 0) {
                    db.AddNewCategoryDAO().addCategory(category);
                    Toast.makeText(getApplicationContext(), "Entry added.", Toast.LENGTH_SHORT).show();

                    // Switch to new category in main activity
                    MainActivity.categoryID = db.AddNewCategoryDAO().getLastCategory().getID();

                    // Show edit button when going back to category
                    MainActivity.showEditButton = true;

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
        Toast.makeText(getApplicationContext(), "Entry discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item_toolbar_view, menu);
        return true;
    }
}