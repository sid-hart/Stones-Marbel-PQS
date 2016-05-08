package com.sid_hart.stones_marbelpqs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Siddharth on 4/29/2016.
 */
public class DBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "stones.db";
    public static final String TABLE_KOTA = "kota";
    public static final String TABLE_KOTA_EXTRA = "kotaextra";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEASUREMENTS = "measurements";
    public static final String COLUMN_EXTRAS = "extras";
    public static final String COLUMN_PRICE = "price";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String insertQuery = "INSERT INTO " + TABLE_KOTA + "(" + COLUMN_MEASUREMENTS + ", " + COLUMN_PRICE + ") VALUES ";
        String query = "CREATE TABLE " + TABLE_KOTA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEASUREMENTS + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " INTEGER NOT NULL" +
                ");";
        db.execSQL(query);
        //db.execSQL("INSERT INTO " + TABLE_KOTA + " VALUES(null, 'hello', 41);");
        Integer[] length = {23, 29, 35, 41, 47, 53, 59, 65, 71, 77, 83, 89, 95};
        Integer[] prices = {40, 41, 42, 43, 45, 48, 50, 51, 53, 54, 56, 57, 59};

        for (int i = 0; i < length.length; i++) {

            if (i == length.length - 1) {
                insertQuery += "(" + length[i] + ", " + prices[i] + ");";
            } else {
                insertQuery += "(" + length[i] + ", " + prices[i] + "), ";
            }
        }
        db.execSQL(insertQuery);

        String insertExtra = "INSERT INTO " + TABLE_KOTA_EXTRA + " (" + COLUMN_EXTRAS + ", " + COLUMN_PRICE + ") VALUES ";
        String queryExtra = "CREATE TABLE " + TABLE_KOTA_EXTRA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXTRAS + " TEXT NOT NULL, " +
                COLUMN_PRICE + " INTEGER NOT NULL);";

        String[] extra = {"Width", "DP", "EdgePolish", "HalfRound", "FullRound"};
        Integer[] pricesExtra = {10, 10, 10, 25, 30};

        for (int i = 0; i < extra.length; i++) {

            if (i == extra.length - 1) {
                insertExtra += "('" + extra[i] + "', " + pricesExtra[i] + ");";
            } else {
                insertExtra += "('" + extra[i] + "', " + pricesExtra[i] + "), ";
            }
        }
        db.execSQL(queryExtra);
        db.execSQL(insertExtra);
        //db.close();
    }

    public void changePrice(int length, int price) {
        SQLiteDatabase db = getWritableDatabase();
        boolean flag = true;

        String verifyQuery = "Select * from " + TABLE_KOTA + " where " + COLUMN_MEASUREMENTS + " = " + length + ";";
        Cursor cursor = db.rawQuery(verifyQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            flag = false;
        }
        cursor.close();

        if (flag) {
            String query = "UPDATE " + TABLE_KOTA + " SET " + COLUMN_PRICE + "=" + price + " WHERE " + COLUMN_MEASUREMENTS + "=" + length + ";";
            db.execSQL(query);
        } else {
            addNewKota(length, price);
        }
            /*SQLiteStatement statement = db.compileStatement("SELECT changes();");
            return statement.simpleQueryForLong();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOTA);
        onCreate(db);
    }

    //Add a new row to database
    public void addNewKota(int kotaLength, int kotaPrice) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEASUREMENTS, kotaLength);
        values.put(COLUMN_PRICE, kotaPrice);
        SQLiteDatabase db = getWritableDatabase();
        //db.insert(TABLE_KOTA, null, values);
        db.insertWithOnConflict(TABLE_KOTA, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        /*SQLiteStatement statement = db.compileStatement("SELECT changes();");
        return statement.simpleQueryForLong();*/
    }

    //Delete product from the database
   /* public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_KOTA + " WHERE " + COLUMN_MEASUREMENTS + "=\"" + productName + "\";");
    }*/

    //Print out the database
    public ArrayList<Integer> getMeasurements() {

        ArrayList<Integer> measureString = new ArrayList<Integer>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_KOTA + " WHERE 1";

        //Cursor point to a location in your database
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("measurements")) != null) {
                measureString.add(c.getInt(c.getColumnIndex("measurements")));
            }
            c.moveToNext();
        }
        db.close();
        return measureString;
    }

    public ArrayList<Integer> getPrices() {
        ArrayList<Integer> priceString = new ArrayList<Integer>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_KOTA + " WHERE 1";

        //Cursor point to a location in your database
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("price")) != null) {
                priceString.add(c.getInt(c.getColumnIndex("price")));
            }
            c.moveToNext();
        }
        db.close();
        return priceString;
    }

    public int getPrice(int length) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        int price = 0;
        String query = "SELECT " + COLUMN_PRICE + " FROM " + TABLE_KOTA + " WHERE " + COLUMN_MEASUREMENTS + "=" + length + ";";
        c = db.rawQuery(query, null);

        if (c.getCount() == 1) {
            c.moveToFirst();
            price = c.getInt(c.getColumnIndex(COLUMN_PRICE));
        }

        return price;
    }

    public int getExtraCost(String extra) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        int price = 0;
        String query = "SELECT " + COLUMN_PRICE + " FROM " + TABLE_KOTA_EXTRA + " WHERE " + COLUMN_EXTRAS + "='" + extra + "';";
        c = db.rawQuery(query, null);

        if (c.getCount() == 1) {
            c.moveToFirst();
            price = c.getInt(c.getColumnIndex(COLUMN_PRICE));
        }

        return price;
    }

    public void updateExtraCost(ArrayList<Integer> price) {
        String[] extras = {"Width", "DP", "EdgePolish", "HalfRound", "FullRound"};
        SQLiteDatabase dbUpdate = getReadableDatabase();

        for (int i = 0; i < price.size(); i++) {
            String updateQuery = "UPDATE " + TABLE_KOTA_EXTRA +
                    " SET " + COLUMN_PRICE + "=" + price.get(i) +
                    " WHERE " + COLUMN_EXTRAS + "='" + extras[i] + "';";
            dbUpdate.execSQL(updateQuery);
        }

    }

}
