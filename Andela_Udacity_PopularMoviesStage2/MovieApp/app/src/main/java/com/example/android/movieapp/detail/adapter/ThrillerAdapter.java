package com.example.android.movieapp.detail.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieapp.R;
import com.example.android.movieapp.detail.model.ThrillerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThrillerAdapter extends ArrayAdapter<ThrillerItem> {

    private Context mCtx;
    private int mResource;
    private List<ThrillerItem> mList;


    public ThrillerAdapter(@NonNull Context context, int resource, @NonNull List<ThrillerItem> list) {
        super(context, resource, list);
        this.mCtx = context;
        this.mResource = resource;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(mResource, null);

        TextView name = view.findViewById(R.id.nameTV);
        TextView site = view.findViewById(R.id.siteTV);
        TextView size = view.findViewById(R.id.sizeTV);
        ImageView thumbnail = view.findViewById(R.id.imageView);

        final ThrillerItem item = mList.get(position);



        Picasso.with(mCtx)
                .load(generateYoutubeThumbnailURL(item.getId()))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(thumbnail);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(item.getId());
            }
        });

        name.setText(item.getName());
        site.setText(item.getSite());
        size.setText(item.getSize());


        return view;
    }

    public String generateYoutubeThumbnailURL(String key) {
        String url = "http://img.youtube.com/vi/" + key + "/mqdefault.jpg";
        return url;
    }



    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            mCtx.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            mCtx.startActivity(intent);
        }
    }

}
