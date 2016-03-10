package com.kewishfagoe.android.ticketingapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kewis on 3/9/2016.
 */
public class databaseDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "ticket_db.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USERS_NAME = "users";
    private static final String TABLE_USERS_ID = "user_id";
    private static final String TABLE_USERS_REG_DATE = "reg_datum";
    private static final String TABLE_USERS_FNAAM = "fnaam";
    private static final String TABLE_USERS_VNAAM = "vnaam";
    private static final String TABLE_USERS_ADRES = "adres";
    private static final String TABLE_USERS_TELEFOON = "teplefoon";
    private static final String TABLE_USERS_EMAIL = "email";
    private static final String TABLE_USERS_USERNAME = "username";
    private static final String TABLE_USERS_PASSWORD = "password";
    private static final String TABLE_USERS_LEVEL = "user_level";

    private static final String TABLE_TICKETS_NAME = "tickets";
    private static final String TABLE_TICKETS_ID = "ticket_id";
    private static final String TABLE_TICKETS_CREATION_DATE = "creatie_datum";
    private static final String TABLE_TICKETS_TYPE_PROBLEEM = "type_probleem";
    private static final String TABLE_TICKETS_TITLE = "title";
    private static final String TABLE_TICKETS_DESCRIPTION = "description";
    private static final String TABLE_TICKETS_REPARATIE_DATUM = "reparatie_datum";
    private static final String TABLE_TICKETS_STATUS = "status";

    private static final String TABLE_USERTICKETS_NAME = "user_tickets";
    private static final String TABLE_USERTICKETS_ID = "user_tickets_id";
    private static final String TABLE_USERTICKETS_UID = "user_id";
    private static final String TABLE_USERTICKETS_TID = "ticket_id";


//Create Table users Script


//Create  Table tickets


//Create Table user_tickets


    public databaseDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
