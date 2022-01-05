package de.fhbielefeld.swe.raumscannerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    EditText inputRoomnumber, inputSeatcount, inputTablecount, inputSpecials, inputBuilding, inputSection, inputFloor, inputRoom, inputDamaged, inputAlias;
    Button update_button, delete_button;

    String ID, title;

    SQLiteHelper roomDB;
    ArrayList<String> room_ID, room_Roomnumber, room_Seatcount, room_Tablecount, room_Specials, room_Building, room_Section, room_Floor, room_Room, room_Damaged, room_Alias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputRoomnumber = findViewById(R.id.inputRoomnumber2);
        inputSeatcount = findViewById(R.id.inputSeatcount2);
        inputTablecount = findViewById(R.id.inputTablecount2);
        inputSpecials = findViewById(R.id.inputSpecials2);
        inputBuilding = findViewById(R.id.inputBuilding2);
        inputSection = findViewById(R.id.inputSection2);
        inputFloor = findViewById(R.id.inputFloor2);
        inputRoom = findViewById(R.id.inputRoom2);
        inputDamaged = findViewById(R.id.inputDamaged2);
        inputAlias = findViewById(R.id.inputAlias2);

        roomDB = new SQLiteHelper(UpdateActivity.this);

        getIntentData();
        populate();
        title = inputRoomnumber.getText().toString().trim();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Update Aufruf
            public void onClick(View v) {
                roomDB.updateData(ID, inputRoomnumber.getText().toString().trim(),
                        Integer.parseInt(inputSeatcount.getText().toString().trim()),Integer.parseInt(inputTablecount.getText().toString().trim()),
                        inputSpecials.getText().toString().trim(), inputBuilding.getText().toString().trim(),
                        inputSection.getText().toString().trim(),inputFloor.getText().toString().trim(),
                        inputRoom.getText().toString().trim(),inputDamaged.getText().toString().trim(),
                        inputAlias.getText().toString().trim());
                finish();
            }
        });

        delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_deletion();
            }
        });


    }

    void populate(){
        Cursor cursor = roomDB.readSpecificByID(ID);
        if(cursor.getCount() == 0){
            // Wenn keine Daten vorhanden sind:
            Toast.makeText(this, "Keine Räume vorhanden", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                String dump = (cursor.getString(0));
                inputRoomnumber.setText(cursor.getString(1));
                inputSeatcount.setText(cursor.getString(2));
                inputTablecount.setText(cursor.getString(3));
                inputSpecials.setText(cursor.getString(4));
                inputBuilding.setText(cursor.getString(5));
                inputSection.setText(cursor.getString(6));
                inputFloor.setText(cursor.getString(7));
                inputRoom.setText(cursor.getString(8));
                inputDamaged.setText(cursor.getString(9));
                inputAlias.setText(cursor.getString(10));
            }
        }
    }

    void getIntentData(){
        if(getIntent().hasExtra("id")){
            ID = getIntent().getStringExtra("id");

        }
        else{
            Toast.makeText(this, "Keine ID übergeben", Toast.LENGTH_SHORT).show();
        }

    }

    void confirm_deletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title + "löschen");
        builder.setMessage("Möchten Sie wirklich " + title + " löschen?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                roomDB.deleteByID(ID);
                finish();
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


}