package de.fhbielefeld.swe.raumscannerapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "RaumeDB.db";
    private static int  DATABASE_VERSION = 1;

    // Table RoomsTBL
    private static final String TABLE_NAME = "RaumeTBL";
    // Spalten:
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ROOMNUMBER = "roomnumber";
    private static final String COLUMN_SEATCOUNT = "seatcount";
    private static final String COLUMN_TABLECOUNT = "tablecount";
    private static final String COLUMN_SPECIALS = "specials";
    private static final String COLUMN_BUILDING = "building";
    private static final String COLUMN_SECTION = "section";
    private static final String COLUMN_FLOOR = "floor";
    private static final String COLUMN_ROOM = "room";
    private static final String COLUMN_DAMAGED = "damaged";
    private static final String COLUMN_ALIAS = "alias";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ROOMNUMBER + " TEXT, " +
                        COLUMN_SEATCOUNT + " INTEGER, " +
                        COLUMN_TABLECOUNT + " INTEGER, " +
                        COLUMN_SPECIALS + " TEXT, " +
                        COLUMN_BUILDING + " TEXT, " +
                        COLUMN_SECTION + " TEXT, " +
                        COLUMN_FLOOR + " TEXT, " +
                        COLUMN_ROOM + " TEXT, " +
                        COLUMN_DAMAGED + " TEXT, " +
                        COLUMN_ALIAS + " TEXT" +
                        ");";

        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRoom(String roomnumber, int seatcount, int tablecount, String specials, String building, String section, String floor, String room, String damaged, String alias){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(COLUMN_ROOMNUMBER, roomnumber);
        content.put(COLUMN_SEATCOUNT, seatcount);
        content.put(COLUMN_TABLECOUNT, tablecount);
        content.put(COLUMN_SPECIALS, specials);
        content.put(COLUMN_BUILDING, building);
        content.put(COLUMN_SECTION, section);
        content.put(COLUMN_FLOOR, floor);
        content.put(COLUMN_ROOM, room);
        content.put(COLUMN_DAMAGED, damaged);
        content.put(COLUMN_ALIAS, alias);

        //Einfügen der Zeile in die DB
        long success = db.insert(TABLE_NAME, null, content);

        if(success == -1){
            Toast.makeText(context, "Raum konnte leider nicht hinzugefügt werden.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Raum wurde erfolgreich hinzugefügt.", Toast.LENGTH_LONG).show();
        }

    }

    Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + ";";

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        else{

        }
        return cursor;
    }

    Cursor readSpecificByID(String ID){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id IS "+ ID +";";
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        else{

        }
        return cursor;
    }

    void updateData(String row_id, String roomnumber, int seatcount, int tablecount, String specials, String building, String section, String floor, String room, String damaged, String alias){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues content = new ContentValues();

        content.put(COLUMN_ROOMNUMBER, roomnumber);
        content.put(COLUMN_SEATCOUNT, seatcount);
        content.put(COLUMN_TABLECOUNT, tablecount);
        content.put(COLUMN_SPECIALS, specials);
        content.put(COLUMN_BUILDING, building);
        content.put(COLUMN_SECTION, section);
        content.put(COLUMN_FLOOR, floor);
        content.put(COLUMN_ROOM, room);
        content.put(COLUMN_DAMAGED, damaged);
        content.put(COLUMN_ALIAS, alias);

        long res = db.update(TABLE_NAME, content, "_id=?", new String[]{row_id});

        if(res == -1){
            Toast.makeText(context, "Updaten fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Update erfolgreich", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteByID(String row_id){
        SQLiteDatabase db = this.getReadableDatabase();
        long res = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if(res == -1){
            Toast.makeText(context, "Löschen fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Löschen erfolgreich", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME + ";");
    }

}
