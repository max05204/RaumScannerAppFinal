package de.fhbielefeld.swe.raumscannerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    Activity activity;

    private ArrayList room_ID, room_Roomnumber, room_Seatcount, room_Tablecount, room_Alias;



    RoomAdapter(Activity activity, Context context, ArrayList room_ID, ArrayList room_Roomnumber, ArrayList room_Alias, ArrayList room_Seatcount, ArrayList room_Tablecount){
        this.activity = activity;
        this.context = context;
        this.room_Roomnumber = room_Roomnumber;
        this.room_Alias = room_Alias;
        this.room_Seatcount = room_Seatcount;
        this.room_Tablecount = room_Tablecount;
        this.room_ID = room_ID;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.room_roomnumber_txt.setText(String.valueOf(room_Roomnumber.get(position)));
        holder.room_alias_txt.setText(String.valueOf(room_Alias.get(position)));
        holder.room_seatcount_txt.setText(String.valueOf(room_Seatcount.get(position)));
        holder.room_tablecount_txt.setText(String.valueOf(room_Tablecount.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(room_ID.get(holder.getAdapterPosition())));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return room_Roomnumber.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView room_roomnumber_txt, room_alias_txt, room_seatcount_txt, room_tablecount_txt;

        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room_roomnumber_txt = itemView.findViewById(R.id.room_roomnumber_txt);
            room_alias_txt = itemView.findViewById(R.id.room_alias_txt);
            room_seatcount_txt = itemView.findViewById(R.id.room_seatcount_txt);
            room_tablecount_txt = itemView.findViewById(R.id.room_tablecount_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
