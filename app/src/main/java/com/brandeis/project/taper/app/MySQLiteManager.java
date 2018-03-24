package com.brandeis.project.taper.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baezd on 9/17/2017.
 */
public class MySQLiteManager extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "reservations";
    private static final int COLNO__ID = 0;
    private static final int COLNO_DATE = 1;
    private static final int COLNO_TIME = 2;
    private static final int COLNO_NAME = 3;
    private static final int COLNO_EMAIL = 4;
    private static final int COLNO_PHONE = 5;
    private static final String[] TABLE_COLUMNS = new String[]{"_id", "date", "time", "name", "email", "phone"};

    private static final String DB_FILE="taper.db";
    private static final int DB_VERSION=1;
    private static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n"
            + "	_id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	date text NOT NULL,\n"
            + "	time text NOT NULL,\n"
            + "	name text NOT NULL,\n"
            + "	email text,\n"
            + " phone text NOT NULL);";

    public MySQLiteManager(Context context) {
        super(context, DB_FILE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public static int getCOLNO__ID() {
        return COLNO__ID;
    }

    public static int getCOLNO_DATE() {
        return COLNO_DATE;
    }

    public static int getCOLNO_TIME() {
        return COLNO_TIME;
    }

    public static int getCOLNO_NAME() {
        return COLNO_NAME;
    }

    public static int getCOLNO_EMAIL() {
        return COLNO_EMAIL;
    }

    public static int getCOLNO_PHONE() {
        return COLNO_PHONE;
    }

    public static String[] getTABLE_COLUMNS() {
        return TABLE_COLUMNS;
    }
}
