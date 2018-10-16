package com.example.zagne_000.teachhelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.model.Group;

import java.util.ArrayList;

public class GroupAdapter extends ArrayAdapter<Group> {
    // declaring our ArrayList of items
    public ArrayList<Group> objects;

    /* here we must override the constructor for ArrayAdapter
     * the only variable we care about now is ArrayList<Item> objects,
     * because it is the list of objects we want to display.
     */
    public GroupAdapter(Context context, int textViewResourceId, ArrayList<Group> objects) {
        super(context, textViewResourceId, objects);
        this.objects = new ArrayList<>();
        this.objects = objects;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.activity_item, null);
        }

        /*
         * Recall that the variable position is sent in as an argument to this method.
         * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
         * iterates through the list we sent it)
         *
         * Therefore, i refers to the current Item object.
         */
        Group i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView id = (TextView) v.findViewById(R.id.textView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (id != null){
                id.setText(i.getId_group());
            }
        }
        return v;
    }
    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.activity_item, null);
        }

        /*
         * Recall that the variable position is sent in as an argument to this method.
         * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
         * iterates through the list we sent it)
         *
         * Therefore, i refers to the current Item object.
         */
        Group i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView id = (TextView) v.findViewById(R.id.textView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (id != null){
                id.setText(i.getId_group());
            }
        }
        return v;
    }
}
