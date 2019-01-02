package com.kenechi.journalapp.main;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenechi.journalapp.R;
import com.kenechi.journalapp.data.RecordEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>{

    private static final String DATE_FORMAT = "dd/MM/yyy";
    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    private List<RecordEntry> mRecordEntries;

    private Context mContext;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public RecordAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    public void setTasks(List<RecordEntry> recordEntries) {
        mRecordEntries = recordEntries;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_item, parent, false);

        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.RecordViewHolder holder, int position) {

        RecordEntry recordEntry = mRecordEntries.get(position);
        String title = recordEntry.getTitle();
        int priority = recordEntry.getTag();
        String updatedAt = dateFormat.format(recordEntry.getUpdatedAt());

        //Set values
        holder.recordTitleView.setText(title);
        holder.updatedAtView.setText(updatedAt);
        holder.tagView.setBackgroundColor(getPriorityColor(priority));

    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }
    public List<RecordEntry> getRecords() {
        return mRecordEntries;
    }


    @Override
    public int getItemCount() {
        if (mRecordEntries == null) {
            return 0;
        }
        return mRecordEntries.size();

    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Class variables for the task description and priority TextViews
        TextView recordTitleView;
        TextView updatedAtView;
        ImageView tagView;


        public RecordViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recordTitleView = itemView.findViewById(R.id.recordTitle);
            updatedAtView = itemView.findViewById(R.id.recordUpdatedAt);
            tagView = itemView.findViewById(R.id.recordTag);

        }
        @Override
        public void onClick(View view) {
            int elementId = mRecordEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
