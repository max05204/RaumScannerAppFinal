package de.fhbielefeld.swe.raumscannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText inputRoomnumber, inputSeatcount, inputTablecount, inputSpecials, inputBuilding, inputSection, inputFloor, inputRoom, inputDamaged, inputAlias;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputRoomnumber = findViewById(R.id.inputRoomnumber);
        inputSeatcount = findViewById(R.id.inputSeatcount);
        inputTablecount = findViewById(R.id.inputTablecount);
        inputSpecials = findViewById(R.id.inputSpecials);
        inputBuilding = findViewById(R.id.inputBuilding);
        inputSection = findViewById(R.id.inputSection);
        inputFloor = findViewById(R.id.inputFloor);
        inputRoom = findViewById(R.id.inputRoom);
        inputDamaged = findViewById(R.id.inputDamaged);
        inputAlias = findViewById(R.id.inputAlias);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper helper = new SQLiteHelper(AddActivity.this);
                helper.addRoom(inputRoomnumber.getText().toString().trim(),
                        Integer.valueOf(inputSeatcount.getText().toString().trim()),
                        Integer.valueOf(inputTablecount.getText().toString().trim()),
                        inputSpecials.getText().toString().trim(),
                        inputBuilding.getText().toString().trim(),
                        inputSection.getText().toString().trim(),
                        inputFloor.getText().toString().trim(),
                        inputRoom.getText().toString().trim(),
                        inputDamaged.getText().toString().trim(),
                        inputAlias.getText().toString().trim()
                        );
            }
        });


    }
}