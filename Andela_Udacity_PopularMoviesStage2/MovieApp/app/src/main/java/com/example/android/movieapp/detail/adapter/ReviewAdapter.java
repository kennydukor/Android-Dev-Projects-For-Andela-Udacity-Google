package com.example.android.movieapp.detail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.movieapp.R;
import com.example.android.movieapp.detail.model.ReviewItem;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<ReviewItem> {

    private Context mCtx;
    private int mResource;
    private List<ReviewItem> mList;

    public ReviewAdapter(@NonNull Context context, int mResource, List<ReviewItem> mList) {
        super(context, mResource, mList);
        this.mCtx = context;
        this.mResource = mResource;
        this.mList = mList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(mResource, null);

        TextView author = view.findViewById(R.id.reviewAuthor);
        TextView content = view.findViewById(R.id.reviewContent);
        ReviewItem item = mList.get(position);

        author.setText(item.getAuthor());
        content.setText(item.getContent());

        return view;
    }

}
