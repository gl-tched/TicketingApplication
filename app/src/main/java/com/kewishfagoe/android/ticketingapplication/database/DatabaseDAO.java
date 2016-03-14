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
    private static final int USER_LEVEL = 2;

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

    public DatabaseDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static int getUserLevel() {
        return USER_LEVEL;
    }

    public static String getTableUsersName() {
        return TABLE_USERS_NAME;
    }

    public static String getTableUsersId() {
        return TABLE_USERS_ID;
    }

    public static String getTableUsersRegDate() {
        return TABLE_USERS_REG_DATE;
    }

    public static String getTableUsersFnaam() {
        return TABLE_USERS_FNAAM;
    }

    public static String getTableUsersVnaam() {
        return TABLE_USERS_VNAAM;
    }

    public static String getTableUsersAdres() {
        return TABLE_USERS_ADRES;
    }

    public static String getTableUsersTelefoon() {
        return TABLE_USERS_TELEFOON;
    }

    public static String getTableUsersEmail() {
        return TABLE_USERS_EMAIL;
    }

    public static String getTableUsersUsername() {
        return TABLE_USERS_USERNAME;
    }

    public static String getTableUsersPassword() {
        return TABLE_USERS_PASSWORD;
    }

    public static String getTableUsersLevel() {
        return TABLE_USERS_LEVEL;
    }

    public static String getTableTicketsName() {
        return TABLE_TICKETS_NAME;
    }

    public static String getTableTicketsId() {
        return TABLE_TICKETS_ID;
    }

    public static String getTableTicketsCreationDate() {
        return TABLE_TICKETS_CREATION_DATE;
    }

    public static String getTableTicketsTypeProbleem() {
        return TABLE_TICKETS_TYPE_PROBLEEM;
    }

    public static String getTableTicketsTitle() {
        return TABLE_TICKETS_TITLE;
    }

    public static String getTableTicketsDescription() {
        return TABLE_TICKETS_DESCRIPTION;
    }

    public static String getTableTicketsReparatieDatum() {
        return TABLE_TICKETS_REPARATIE_DATUM;
    }

    public static String getTableTicketsStatus() {
        return TABLE_TICKETS_STATUS;
    }

    public static String getTableUserTicketsName() {
        return TABLE_USERTICKETS_NAME;
    }

    public static String getTableUserTicketsId() {
        return TABLE_USERTICKETS_ID;
    }

    public static String getTableUserTicketsUid() {
        return TABLE_USERTICKETS_UID;
    }

    public static String getTableUserTicketsTid() {
        return TABLE_USERTICKETS_TID;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteScript.prepareDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertUser(ContentValues user) {
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

    public User findUserByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", TABLE_USERS_TELEFOON);
        String[] whereArgs = {phoneNumber};
        cursor = db.query(TABLE_USERS_NAME, new String[]{"user_id", "fnaam", "vnaam", "username", "password", "user_level"}, whereClause, whereArgs, null, null, null);

        User user = null;
        if (cursor.moveToNext()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        }

        db.close();
        return user;
    }

    public int updateTicket(ContentValues ticket, int ticket_id) {
        int affectedRows = 0;
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = String.format("%s = ?", TABLE_TICKETS_ID);
        String[] whereArgs = {String.valueOf(ticket_id)};
        affectedRows = db.update(TABLE_TICKETS_NAME, ticket, whereClause, whereArgs);

        return affectedRows;
    }

    public long insertTicket(ContentValues ticket) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(TABLE_TICKETS_NAME, null, ticket);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public Cursor cursorFindTickets(int user_id, int user_level) {
        ArrayList<Ticket> tickets = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        String sql = "";
        if (user_level != 1) {
            sql += "select tickets.title, tickets.status from user_tickets ";
            sql += "join tickets on user_tickets.ticket_id = tickets.ticket_id ";
            sql += "where user_id = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        } else {
            sql += "select ticket_id, title, status from tickets";
            cursor = db.rawQuery(sql, null);
        }

        db.close();

        return cursor;
    }

    public ArrayList<Ticket> findTickets(int user_id, int user_level) {
        ArrayList<Ticket> tickets = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        String sql = "";
        if (user_level != 1) {
            sql += "select tickets.ticket_id, tickets.title, tickets.status from user_tickets ";
            sql += "join tickets on user_tickets.ticket_id = tickets.ticket_id ";
            sql += "where user_id = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        } else {
            sql += "select ticket_id, title, status from tickets";
            cursor = db.rawQuery(sql, null);
        }

        if (cursor.getCount() > 0) tickets = new ArrayList<>();
        Ticket ticket = null;
        while (cursor.moveToNext()) {
            ticket = new Ticket(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            tickets.add(ticket);
        }

        db.close();

        return tickets;
    }

    public Ticket findTicket(int ticket_id) {
        ArrayList<Ticket> tickets = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        String sql = "";
        sql += "select * from tickets";
        sql += " where ticket_id = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(ticket_id)});

        Ticket ticket = null;
        if (cursor.moveToNext()) {
            ticket = new Ticket(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        }

        db.close();

        return ticket;
    }

    public Ticket findTicketByRowID(long rowId) {
        ArrayList<Ticket> tickets = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        String sql = "";
        sql += "select * from tickets";
        sql += " where rowid = ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(rowId)});

        Ticket ticket = null;
        if (cursor.moveToNext()) {
            ticket = new Ticket(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        }

        db.close();

        return ticket;
    }

    public long insertUserTicket(ContentValues userTicket) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(TABLE_USERTICKETS_NAME, null, userTicket);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }
}
