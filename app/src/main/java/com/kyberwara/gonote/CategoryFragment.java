package com.kyberwara.gonote;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
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

    // Update notes list the fragment when it becomes visible
    @Override
    public void onStart() {
        super.onStart();

        // Construct the data source
        ArrayList<Note> arrayOfNotes = new ArrayList<Note>();

        // Create the adapter to convert the array to views
        NotesAdapter adapter = new NotesAdapter(this.getContext(), arrayOfNotes);

        // Attach the adapter to a ListView
        final ListView listView = getView().findViewById(R.id.notes_container);
        listView.setAdapter(adapter);

        // Get database
        Database db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "notesdb").allowMainThreadQueries().build();
        List<NotesEntity> notes = db.AddNewNoteDAO().getNotes(((MainActivity)getActivity()).categoryID);

        // Add items to adapter
        for (NotesEntity n : notes) {
            Note newNote = new Note(n.getTitle(), n.getContent(), n.getID());
            adapter.add(newNote);
        }
    }
}