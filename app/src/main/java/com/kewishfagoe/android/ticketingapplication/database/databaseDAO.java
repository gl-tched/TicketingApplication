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
private static final String CREATE_TABLE_USERS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(user_id > 0), %s NUMERIC NOT NULL , %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL UNIQUE, %s NUMERIC NOT NULL UNIQUE, %s TEXT NOT NULL, %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL, %s INTEGER NOT NULL CHECK(user_level = 1 OR user_level = 2),PRIMARY KEY(user_id));",
        TABLE_USERS_NAME, TABLE_USERS_ID, TABLE_USERS_REG_DATE, TABLE_USERS_FNAAM, TABLE_USERS_VNAAM, TABLE_USERS_ADRES, TABLE_USERS_TELEFOON, TABLE_USERS_EMAIL, TABLE_USERS_USERNAME, TABLE_USERS_PASSWORD, TABLE_USERS_LEVEL);

//Create  Table tickets
private static final String CREATE_TABLE_TICKETS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(ticket_id > 0), %s NUMERIC NOT NULL , %s TEXT CHECK(type_probleem = 'SOFTWARE' OR type_probleem = 'HARDWARE'), %s TEXT, %s TEXT NOT NULL, %s NUMERIC, %s TEXT NOT NULL CHECK(status = 'OPEN' OR status = 'IN PROGRESS' OR status = 'CLOSED'),PRIMARY KEY(ticket_id));",
        TABLE_TICKETS_NAME, TABLE_TICKETS_ID, TABLE_TICKETS_CREATION_DATE, TABLE_TICKETS_TYPE_PROBLEEM, TABLE_TICKETS_TITLE, TABLE_TICKETS_DESCRIPTION, TABLE_TICKETS_REPARATIE_DATUM, TABLE_TICKETS_STATUS);

//Create Table user_tickets
private static final String CREATE_TABLE_USERTICKETS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(user_tickets_id > 0), %s INTEGER NOT NULL , %s INTEGER NOT NULL, PRIMARY KEY(user_tickets_id), FOREIGN KEY(user_id) REFERENCES users(user_id), FOREIGN KEY(ticket_id) REFERENCES tickets(ticket_id));",
        TABLE_USERTICKETS_NAME, TABLE_USERTICKETS_ID, TABLE_USERTICKETS_UID, TABLE_USERTICKETS_TID);

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
