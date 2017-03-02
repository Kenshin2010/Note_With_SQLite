package com.android.manroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.manroid.demosqlite.MainActivity;
import com.android.manroid.demosqlite.R;
import com.android.manroid.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manroid on 1/28/2017.
 */

public class NoteAdapter extends BaseAdapter implements View.OnClickListener {

    List<NoteModel> arr;
    Context context;

    public NoteAdapter(Context context, ArrayList<NoteModel> arr) {
        this.arr = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_layout,parent,false);
        }

        TextView txtNote = (TextView)rowView.findViewById(R.id.txt_name_item);
        TextView txtTime = (TextView)rowView.findViewById(R.id.txt_time_item);
        ImageView btnDelete = (ImageView)rowView.findViewById(R.id.img_delete);

        btnDelete.setTag(arr.get(position));
        btnDelete.setOnClickListener(this);

        txtNote.setText(arr.get(position).getNote());
        txtTime.setText(arr.get(position).getDatetime());

        return rowView;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)this.context).deleteNote((NoteModel)v.getTag());
    }
}
