package com.kewishfagoe.android.ticketingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.Ticket;

import java.util.ArrayList;
import java.util.ListIterator;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                addTicket();
            }
        });

        Bundle extras = getIntent().getExtras();
        String message = "";
        message += "user_id: " + extras.getInt("user_id") + "\n";
        message += "f_naam: " + extras.getString("f_naam") + "\n";
        message += "v_naam:" + extras.getString("v_naam") + "\n";
        message += "usr:" + extras.getString("username") + "\n";
        message += "psw:" + extras.getString("password") + "\n";
        message += "user_level:" + extras.getInt("user_level");

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        DatabaseDAO db = new DatabaseDAO(this);
//        Cursor cursor = db.cursorFindTickets(extras.getInt("user_id"), extras.getInt("user_level"));
//
//        String[] fromFieldNames = new String[] {DatabaseDAO.getTableTicketsTitle(), DatabaseDAO.getTableTicketsStatus()};
//        int[] toViewIDs = new int[] {R.id.itemTicketTitle, R.id.itemTicketStatus};
//
//        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
//        ListView lv = (ListView) findViewById(R.id.listViewTickets);
//        lv.setAdapter(cursorAdapter);

        ArrayList<Ticket> tickets = db.findTickets(extras.getInt("user_id"), extras.getInt("user_level"));

        if (tickets != null) {
            ListView listView = (ListView) findViewById(R.id.listViewTickets);
            ListIterator<Ticket> it = tickets.listIterator();

            ArrayList<String> helperList = new ArrayList<>();
            ArrayList<Ticket> uniqueTickets = new ArrayList<>();

            for (Ticket ticket : tickets) {
                if (!helperList.contains(ticket.toString())) {
                    helperList.add(ticket.toString());
                    uniqueTickets.add(ticket);
                }
            }

            ArrayAdapter<Ticket> adapter = new ArrayAdapter<Ticket>(this, android.R.layout.simple_selectable_list_item, uniqueTickets);

            listView.setAdapter(adapter);
        }
    }

    public void addTicket() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
