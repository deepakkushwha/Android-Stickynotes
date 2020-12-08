package com.example.stickynotes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    LayoutInflater inflater;
    List<Note> notes;

    Adapter(Context context, List<Note> notes) {
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String data = notes.get(position).getDate();
        String time = notes.get(position).getTime();
        holder.nTitle.setText(title);
        holder.nDate.setText(data);
        holder.nTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView nTitle, nDate, nTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Details.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }
}
