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

public class EditCategory extends AppCompatActivity {

    int categoryID;
    Database db;
    CategoriesEntity category;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        setTitle("Edit category");

        categoryID = getIntent().getIntExtra("categoryID", 0);

        // Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the back button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24px);

        // Get database
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();
        category = db.CategoriesDAO().getCategory(categoryID);

        // Get category name
        EditText categoryName = findViewById(R.id.categoryName);

        categoryName.setText(category.getCategory());

        // Show keyboard
        imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    // Listener for toolbar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Finish activity
                Toast.makeText(getApplicationContext(), "Changes discarded.", Toast.LENGTH_SHORT).show();

                // Hide keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                finish();
                return true;

            case R.id.delete:
                // Delete category from database
                db.CategoriesDAO().deleteCategory(category);
                Toast.makeText(getApplicationContext(), "Category deleted.", Toast.LENGTH_SHORT).show();

                // Hide keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                finish();
                return true;

            case R.id.done:
                // Get content of text field
                EditText categoryName = findViewById(R.id.categoryName);
                String categoryNameText = categoryName.getText().toString();

                category.setCategory(categoryNameText);

                // Update entry in database
                db.CategoriesDAO().updateCategory(category);
                Toast.makeText(getApplicationContext(), "Category updated.", Toast.LENGTH_SHORT).show();

                // Hide keyboard
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Override back button press
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Changes discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item_toolbar_view, menu);
        return true;
    }
}
