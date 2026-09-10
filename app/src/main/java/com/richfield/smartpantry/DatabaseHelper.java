package com.richfield.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PantryManager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PANTRY = "pantry";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QTY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY = "expiryDate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QTY + " REAL, " +
                COLUMN_UNIT + " TEXT, " +
                COLUMN_EXPIRY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }

    // --- CRUD OPERATIONS ---

    // Create
    public boolean addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, ingredient.getName());
        cv.put(COLUMN_QTY, ingredient.getQuantity());
        cv.put(COLUMN_UNIT, ingredient.getUnit());
        cv.put(COLUMN_EXPIRY, ingredient.getExpiryDate());
        long insert = db.insert(TABLE_PANTRY, null, cv);
        return insert != -1;
    }

    // Read
    public List<Ingredient> getAllPantryItems() {
        List<Ingredient> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_PANTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double qty = cursor.getDouble(2);
                String unit = cursor.getString(3);
                String expiry = cursor.getString(4);
                returnList.add(new Ingredient(id, name, qty, unit, expiry));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    // Update
    public boolean updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, ingredient.getName());
        cv.put(COLUMN_QTY, ingredient.getQuantity());
        cv.put(COLUMN_UNIT, ingredient.getUnit());
        cv.put(COLUMN_EXPIRY, ingredient.getExpiryDate());
        int update = db.update(TABLE_PANTRY, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(ingredient.getId())});
        return update > 0;
    }

    // Delete
    public boolean deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(TABLE_PANTRY, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return delete > 0;
    }
}