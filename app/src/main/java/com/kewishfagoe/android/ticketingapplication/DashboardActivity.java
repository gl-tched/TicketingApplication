package com.kewishfagoe.android.ticketingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.Ticket;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    private static int userId;
    private static int userLevel;
    private static boolean isListPopulated = false;

    public static int getUserId() {
        return userId;
    }

    public static int getUserLevel() {
        return userLevel;
    }

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

        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        userId = extras.getInt("user_id");
        userLevel = extras.getInt("user_level");
        HashMap<String, Object> result = populateTicketList(userId, userLevel);

        if (result != null) {
            isListPopulated = true;
            final Context $this = this;
            final ArrayAdapter<Ticket> adapter = (ArrayAdapter<Ticket>) result.get("adapter");

            ListView listView = (ListView) findViewById(R.id.listViewTickets);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent($this, TicketDetailsActivity.class);
                    //intent.putExtra("listitem pos", position);
                    //intent.putExtra("adapteritem pos", adapter.getPosition(adapter.getItem(position)));
                    intent.putExtra("ticket_id", adapter.getItem(position).getTicket_id());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isListPopulated) {
            HashMap<String, Object> result = populateTicketList(userId, userLevel);

            if (result != null) {
                isListPopulated = true;
                final Context $this = this;
                final ArrayAdapter<Ticket> adapter = (ArrayAdapter<Ticket>) result.get("adapter");

                ListView listView = (ListView) findViewById(R.id.listViewTickets);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent($this, TicketDetailsActivity.class);
                        //intent.putExtra("listitem pos", position);
                        //intent.putExtra("adapteritem pos", adapter.getPosition(adapter.getItem(position)));
                        intent.putExtra("ticket_id", adapter.getItem(position).getTicket_id());
                        startActivity(intent);
                    }
                });
            }
        }
        isListPopulated = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private HashMap<String, Object> populateTicketList(int user_id, int user_level) {
        DatabaseDAO db = new DatabaseDAO(this);
        ArrayList<Ticket> tickets = db.findTickets(user_id, user_level);

        if (tickets == null) return null;

        ListView listView = (ListView) findViewById(R.id.listViewTickets);
        final ArrayAdapter<Ticket> adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, tickets);
        listView.setAdapter(adapter);

        HashMap<String, Object> result = new HashMap<>();
        result.put("adapter", adapter);

        return result;
    }

    private void addTicket() {
        Intent intent = new Intent(this, AddTicketActivity.class);
        startActivity(intent);
    }
}
