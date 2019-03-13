package com.kyberwara.gonote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<Note> {
    public NotesAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Note note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }

        // Lookup view for data population
        TextView noteTitle = convertView.findViewById(R.id.title);
        TextView noteContent = convertView.findViewById(R.id.content);

        // Populate the data into the template view using the data object
        noteTitle.setText(note.title);
        noteContent.setText(note.content);

        // Start note editing activity on click, and pass ID of note clicked
        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditNote.class);
                intent.putExtra("noteID", note.ID);
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
