package com.journalapp.tjohnn.journalapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Tjohn on 6/30/18.
 */

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalViewHolder> {

    private List<JournalEntry> journalEntries;
    private JournalItemListener itemListener;

    public JournalListAdapter(List<JournalEntry> journalEntries, JournalItemListener itemListener) {
        this.journalEntries = journalEntries;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public JournalListAdapter.JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal_list, parent, false);
        return new JournalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalListAdapter.JournalViewHolder holder, int position) {
        holder.bind(journalEntries.get(position), position);
    }

    @Override
    public int getItemCount() {
        return journalEntries.size();
    }

    public void updateItems(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;

        notifyDataSetChanged();
    }


    class JournalViewHolder extends RecyclerView.ViewHolder {
        JournalEntry journalEntry;

        ImageView editView;
        ImageView deleteView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView timeTextView;
        int pos;

        JournalViewHolder(View itemView) {
            super(itemView);
            editView = itemView.findViewById(R.id.edit_view);
            deleteView = itemView.findViewById(R.id.delete_view);
            titleTextView = itemView.findViewById(R.id.tv_title);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            timeTextView = itemView.findViewById(R.id.tv_date);

            editView.setOnClickListener(e -> itemListener.onEditJournalClicked(journalEntry));
            deleteView.setOnClickListener(e -> itemListener.onDeleteJournalClicked(journalEntry, pos));
            itemView.setOnClickListener(e -> itemListener.onJournalItemClicked(journalEntry));
        }

        void bind(JournalEntry journalEntry, int position) {
            this.journalEntry = journalEntry;
            pos = position;
            titleTextView.setText(journalEntry.getTitle());
            descriptionTextView.setText(journalEntry.getDescription());
            timeTextView.setText(String.format("%1$td %1$tb, %1$tY %1$tH:%1$tM", journalEntry.getUpdatedAt()));
        }
    }

    public interface JournalItemListener{
        void onJournalItemClicked(JournalEntry journalEntry);
        void onEditJournalClicked(JournalEntry journalEntry);
        void onDeleteJournalClicked(JournalEntry journalEntry, int position);
    }

}
