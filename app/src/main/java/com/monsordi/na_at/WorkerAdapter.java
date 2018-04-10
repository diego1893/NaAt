package com.monsordi.na_at;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monsordi.na_at.sqlite.FeedReaderContract.*;
import com.monsordi.na_at.sqlite.FeedReaderContract;
/**
 * Created by diego on 06/04/18.
 */

public class WorkerAdapter extends CursorAdapter {

    public WorkerAdapter(Context context, Cursor cursor){
        super(context, cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.row_main,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //UI References
        TextView nameText = (TextView) view.findViewById(R.id.row_name);
        TextView emailText = (TextView) view.findViewById(R.id.row_email);
        TextView jobText = (TextView) view.findViewById(R.id.row_job);
        ImageView imageUrl = (ImageView) view.findViewById(R.id.row_image);

        Worker currentWorker = new Worker(cursor);

        // Setup.
        nameText.setText(currentWorker.getName());
        emailText.setText(currentWorker.getEmail());
        jobText.setText(currentWorker.getJob());
        Glide.with(context)
                .load(currentWorker.getImageUrl())
                .into(imageUrl);
    }
}
