package com.kyberwara.gonote;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CategoryFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_category, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Add listener to floating action button
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddNewNote.class);
                intent.putExtra("categoryID", ((MainActivity)getActivity()).categoryID);
                startActivity(intent);
            }
        });
    }

    // Update notes list in the fragment
    // this fragment becomes visible to user
    @Override
    public void onStart() {
        super.onStart();
        // Get database
        Database db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();
        List<NotesEntity> notes = db.AddNewNoteDAO().getNotes(((MainActivity)getActivity()).categoryID);

        for (NotesEntity n : notes) {

            String title = n.getTitle();
            String content = n.getContent();

            Log.i("title", "" + title);
            Log.i("content", "" + content);

            // todo insert element with these fields
        }
    }
}