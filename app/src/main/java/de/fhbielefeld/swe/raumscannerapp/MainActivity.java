package de.fhbielefeld.swe.raumscannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    SQLiteHelper roomDB;
    ArrayList<String> room_ID, room_Roomnumber, room_Seatcount, room_Tablecount, room_Specials, room_Building, room_Section, room_Floor, room_Room, room_Damaged, room_Alias;

    RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);


            }
        });

        roomDB = new SQLiteHelper(MainActivity.this);

        room_ID = new ArrayList<>();
        room_Roomnumber = new ArrayList<>();
        room_Seatcount = new ArrayList<>();
        room_Tablecount = new ArrayList<>();
        room_Specials = new ArrayList<>();
        room_Building = new ArrayList<>();
        room_Section = new ArrayList<>();
        room_Floor = new ArrayList<>();
        room_Room = new ArrayList<>();
        room_Damaged = new ArrayList<>();
        room_Alias = new ArrayList<>();

        parseToArrays();

        roomAdapter = new RoomAdapter(MainActivity.this,this, room_ID, room_Roomnumber, room_Alias, room_Seatcount, room_Tablecount);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void parseToArrays(){
        Cursor cursor = roomDB.readAllData();
        if(cursor.getCount() == 0){
            // Wenn keine Daten vorhanden sind:
            Toast.makeText(this, "Keine Räume vorhanden", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                room_ID.add(cursor.getString(0));
                room_Roomnumber.add(cursor.getString(1));
                room_Seatcount.add(cursor.getString(2));
                room_Tablecount.add(cursor.getString(3));
                room_Specials.add(cursor.getString(4));
                room_Building.add(cursor.getString(5));
                room_Section.add(cursor.getString(6));
                room_Floor.add(cursor.getString(7));
                room_Room.add(cursor.getString(8));
                room_Damaged.add(cursor.getString(9));
                room_Alias.add(cursor.getString(10));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alles Zurücksetzen");
            builder.setMessage("Möchten Sie Wirklich alle Einträge enfernen?\nDies kann nicht rückgängig gemacht werden!");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    roomDB.deleteAll();
                    recreate();
                }
            });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //nix machen
                }
            });
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}