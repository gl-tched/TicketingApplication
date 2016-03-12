package com.kewishfagoe.android.ticketingapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by kewis on 3/9/2016.
 */
public class DatabaseDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "ticket_db.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USERS_NAME = "users";
    private static final String TABLE_USERS_ID = "user_id";
    private static final String TABLE_USERS_REG_DATE = "reg_datum";
    private static final String TABLE_USERS_FNAAM = "fnaam";
    private static final String TABLE_USERS_VNAAM = "vnaam";
    private static final String TABLE_USERS_ADRES = "adres";
    private static final String TABLE_USERS_TELEFOON = "telefoon";
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
private static final String SQL_CREATE_TABLE_USERS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(user_id > 0), %s NUMERIC NOT NULL , %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL UNIQUE, %s NUMERIC NOT NULL UNIQUE, %s TEXT NOT NULL, %s TEXT NOT NULL UNIQUE, %s TEXT NOT NULL, %s INTEGER NOT NULL CHECK(user_level = 1 OR user_level = 2),PRIMARY KEY(user_id));",
        TABLE_USERS_NAME, TABLE_USERS_ID, TABLE_USERS_REG_DATE, TABLE_USERS_FNAAM, TABLE_USERS_VNAAM, TABLE_USERS_ADRES, TABLE_USERS_TELEFOON, TABLE_USERS_EMAIL, TABLE_USERS_USERNAME, TABLE_USERS_PASSWORD, TABLE_USERS_LEVEL);

//Create  Table tickets
private static final String SQL_CREATE_TABLE_TICKETS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(ticket_id > 0), %s NUMERIC NOT NULL , %s TEXT CHECK(type_probleem = 'SOFTWARE' OR type_probleem = 'HARDWARE'), %s TEXT, %s TEXT NOT NULL, %s NUMERIC, %s TEXT NOT NULL CHECK(status = 'OPEN' OR status = 'IN PROGRESS' OR status = 'CLOSED'),PRIMARY KEY(ticket_id));",
        TABLE_TICKETS_NAME, TABLE_TICKETS_ID, TABLE_TICKETS_CREATION_DATE, TABLE_TICKETS_TYPE_PROBLEEM, TABLE_TICKETS_TITLE, TABLE_TICKETS_DESCRIPTION, TABLE_TICKETS_REPARATIE_DATUM, TABLE_TICKETS_STATUS);

//Create Table user_tickets
private static final String SQL_CREATE_TABLE_USERTICKETS = String.format(
        "create table if not exists %s(%s INTEGER NOT NULL CHECK(user_tickets_id > 0), %s INTEGER NOT NULL , %s INTEGER NOT NULL, PRIMARY KEY(user_tickets_id), FOREIGN KEY(user_id) REFERENCES users(user_id), FOREIGN KEY(ticket_id) REFERENCES tickets(ticket_id));",
        TABLE_USERTICKETS_NAME, TABLE_USERTICKETS_ID, TABLE_USERTICKETS_UID, TABLE_USERTICKETS_TID);


    public DatabaseDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        insertDefaultData();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_TICKETS);
        db.execSQL(SQL_CREATE_TABLE_USERTICKETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void insertDefaultData() {

        ContentValues userValues1 = new ContentValues();
        userValues1.put(TABLE_USERS_REG_DATE, 2015 - 01 - 18);
        userValues1.put(TABLE_USERS_FNAAM, "Bollinger");
        userValues1.put(TABLE_USERS_VNAAM, "Joyce");
        userValues1.put(TABLE_USERS_ADRES, "Windmolen 181");
        userValues1.put(TABLE_USERS_TELEFOON, 8693447);
        userValues1.put(TABLE_USERS_EMAIL, "JoyceJBollinger@inbound.plus");
        userValues1.put(TABLE_USERS_USERNAME, "joyce");
        userValues1.put(TABLE_USERS_PASSWORD, "5dafa4f662d29c5f870ba55a880dc5089226721f183543cdfaec0c21ccd8d63c");
        userValues1.put(TABLE_USERS_LEVEL, 2);
        insertUser(TABLE_USERS_NAME, userValues1);

        ContentValues userAdminValues = new ContentValues();
        userAdminValues.put(TABLE_USERS_REG_DATE, 2015 - 01 - 18);
        userAdminValues.put(TABLE_USERS_FNAAM, "Super");
        userAdminValues.put(TABLE_USERS_VNAAM, "Admin");
        userAdminValues.put(TABLE_USERS_ADRES, "Column St. 123");
        userAdminValues.put(TABLE_USERS_TELEFOON, 8932618);
        userAdminValues.put(TABLE_USERS_EMAIL, "AdminSuper@inbound.plus");
        userAdminValues.put(TABLE_USERS_USERNAME, "sadmin");
        userAdminValues.put(TABLE_USERS_PASSWORD, "9f5ba68f21489544d985797d58847b65e9a22c4981aeccafc96b351e84df254c");
        userAdminValues.put(TABLE_USERS_LEVEL, 1);
        insertUser(TABLE_USERS_NAME, userAdminValues);

        ContentValues ticketValues1 = new ContentValues();
        ticketValues1.put(TABLE_TICKETS_CREATION_DATE, 2015 - 01 - 18);
        ticketValues1.put(TABLE_TICKETS_TYPE_PROBLEEM, "SOFTWARE");
        ticketValues1.put(TABLE_TICKETS_TITLE, "MS Word Probleem");
        ticketValues1.put(TABLE_TICKETS_DESCRIPTION, "MS Word gaat niet open. Geeft x50326 error bij opstart.");
        ticketValues1.put(TABLE_TICKETS_REPARATIE_DATUM, 2015 - 01 - 22);
        ticketValues1.put(TABLE_TICKETS_STATUS, "CLOSED");
        insertTicket(ticketValues1);

        ContentValues userTicketValues1 = new ContentValues();
        userTicketValues1.put(TABLE_USERTICKETS_TID, 1);
        userTicketValues1.put(TABLE_USERTICKETS_UID, 1);
        insertUserTicket(TABLE_USERTICKETS_NAME, userTicketValues1);

    }


    public long insertUser(String name, ContentValues user) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(TABLE_USERS_NAME, null, user);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public User findUserCreds(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", TABLE_USERS_USERNAME);
        String[] whereArgs = {username};
        cursor = db.query(TABLE_USERS_NAME, new String[]{"user_id", "fnaam", "vnaam", "username", "password", "user_level"}, whereClause, whereArgs, null, null, null);

        User user = null;
        if (cursor.moveToNext()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        }

        db.close();
        return user;
    }

    public long insertTicket(ContentValues ticket) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(TABLE_TICKETS_NAME, null, ticket);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public ArrayList<Ticket> findTickets(int user_id, int user_level) {
        ArrayList<Ticket> tickets = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        String sql = "";
        sql += "select tickets.title, tickets.status from user_tickets ";
        sql += "join tickets on user_tickets.ticket_id = tickets.ticket_id";
        if (user_level != 1) {
            sql += " where user_id = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        } else {
            cursor = db.rawQuery(sql, null);
        }

        if (cursor.getCount() > 0) tickets = new ArrayList<>();
        Ticket ticket = null;
        while (cursor.moveToNext()) {
            ticket = new Ticket(cursor.getString(0), cursor.getString(1));
            tickets.add(ticket);
        }

        db.close();

        return tickets;
    }

    public long insertUserTicket(String name, ContentValues userTicket) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(TABLE_USERTICKETS_NAME, null, userTicket);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

}
