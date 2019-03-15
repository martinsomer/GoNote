package com.kyberwara.gonote;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Menu menu;
    private Database db;
    public static int categoryID;
    private Fragment fragment;
    public static boolean showEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the nav drawer button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

        // Get database
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();

        openHomeCategory();

        // Get drawer_layout from layout file
        drawerLayout = findViewById(R.id.drawer_layout);

        // Handle navigation click events
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    drawerLayout.closeDrawers();

                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    categoryID = menuItem.getItemId();

                    switch (categoryID) {
                        case R.id.home:
                            openHomeCategory();
                            break;

                        case R.id.add_new:
                            Intent intent = new Intent(getApplicationContext(), AddNewCategory.class);
                            startActivity(intent);
                            break;

                        default:
                            setTitle(menuItem.toString());

                            if (db.NotesDAO().getNumberOfNotesInCategory(categoryID) == 0) {
                                fragment = new EmptyCategoryFragment();
                            } else {
                                fragment = new CategoryFragment();
                            }
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                            // Show edit button on toolbar
                            showEditButton = true;
                            invalidateOptionsMenu();
                    }

                    return true;
                }
            });

        // Get menu of navigationView
        menu = navigationView.getMenu();

        // Disable tint of icons in navigation drawer
        navigationView.setItemIconTintList(null);
    }

    // Update categories list in Navigation Drawer
    // when this activity becomes visible to user
    @Override
    protected void onStart() {
        super.onStart();

        // Reload toolbar when activity becomes visible to update icons
        invalidateOptionsMenu();

        // Get the category from database when activity starts
        CategoriesEntity category = db.CategoriesDAO().getCategory(categoryID);
        if(category != null) {
            // Category still exists, update name and load category fragment
            setTitle(category.getCategory());

            // Check if category is empty
            if (db.NotesDAO().getNumberOfNotesInCategory(categoryID) == 0) {
                fragment = new EmptyCategoryFragment();
            } else {
                fragment = new CategoryFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            // Category has been deleted, go to "Home"
            openHomeCategory();
        }

        // Clear current menu to avoid duplication
        menu.clear();

        // Append "Home" menu resource
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_home_view, menu);

        // Append "Add Category" menu resource
        inflater.inflate(R.menu.drawer_add_new_category_view, menu);

        // Get categories from database and append to menu
        List<CategoriesEntity> categories = db.CategoriesDAO().getCategories();
        //for (CategoriesEntity c : categories) {
        for (int i=0; i<categories.size(); i++) {
            CategoriesEntity c = categories.get(i);
            menu.add(1, c.getID(), Menu.NONE, c.getCategory());
        }
    }

    // Open the drawer when the button is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.edit:
                Intent intent = new Intent(getApplicationContext(), EditCategory.class);
                intent.putExtra("categoryID", categoryID);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categegory_toolbar_view, menu);

        // If edit button on toolbar should be shown
        if (!showEditButton) {
            menu.findItem(R.id.edit).setVisible(false);
        }

        return true;
    }

    // Helper function to open home category
    private void openHomeCategory() {
        setTitle("Home");
        // Check if there are any categories
        //if (db.AddNewCategoryDAO().getNumberOfCategories() == 0) {
            //fragment = new NoCategoriesFragment();
        //} else {
            // Check if there are any notes
            if (db.NotesDAO().getNumberOfNotes() == 0) {
                fragment = new NoNotesFragment();
            } else {
                fragment = new AllNotesFragment();
            }
        //}
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Hide edit button on toolbar
        showEditButton = false;
        invalidateOptionsMenu();
    }
}
