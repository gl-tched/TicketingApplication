package com.kewishfagoe.android.ticketingapplication;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.Ticket;

import java.util.HashMap;

public class TicketDetailsActivity extends AppCompatActivity {
    private Ticket ticket;
    private EditText title;
    private EditText description;
    private Spinner type;
    private Spinner status;
    private EditText repdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //Toast.makeText(this, "You reached ticket details", Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        //String message = "";
        //message += "listitem pos: " + extras.getInt("listitem pos") + "\n";
        //message += "adapteritem pos:" + extras.getInt("adapteritem pos");

        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        int ticketId = extras.getInt("ticket_id");
        DatabaseDAO db = new DatabaseDAO(this);
        Ticket ticket = db.findTicket(ticketId);

        this.title = (EditText) findViewById(R.id.ticketTitle);
        this.description = (EditText) findViewById(R.id.ticketDescription);
        this.type = (Spinner) findViewById(R.id.typeDropDown);
        this.status = (Spinner) findViewById(R.id.statusDropDown);
        this.repdate = (EditText) findViewById(R.id.ticketRepDate);

        HashMap<String, Integer> typeHM = new HashMap<>();
        typeHM.put("HARDWARE", 0);
        typeHM.put("SOFTWARE", 1);

        HashMap<String, Integer> statusHM = new HashMap<>();
        statusHM.put("OPEN", 0);
        statusHM.put("IN PROGRESS", 1);
        statusHM.put("CLOSED", 2);

        if (ticket != null) {
            this.ticket = ticket;
            title.setText(ticket.getTitle());
            description.setText(ticket.getDescription());
            type.setSelection(typeHM.get(ticket.getType_probleem()));
            status.setSelection(statusHM.get(ticket.getStatus()));
            repdate.setText(ticket.getReparatie_datum());
        }

        if (DashboardActivity.getUserLevel() == 2) {
            disableEditText(title);
            disableEditText(description);
//            disableEditText(type);
//            disableEditText(status);
            disableEditText(repdate);
        }
    }

    public void disableEditText(EditText fld) {
        fld.setFocusable(true);
        fld.setEnabled(true);
        fld.setCursorVisible(true);
        fld.setKeyListener(null);
        fld.setBackgroundColor(Color.TRANSPARENT);
    }

    public void attemptTicketUpdate(View view) {
        ContentValues ticketValues = new ContentValues();
        ticketValues.put(DatabaseDAO.getTableTicketsTypeProbleem(), type.getSelectedItem().toString());
        ticketValues.put(DatabaseDAO.getTableTicketsTitle(), String.valueOf(title.getText()));
        ticketValues.put(DatabaseDAO.getTableTicketsDescription(), String.valueOf(description.getText()));
        ticketValues.put(DatabaseDAO.getTableTicketsReparatieDatum(), String.valueOf(repdate.getText()));
        ticketValues.put(DatabaseDAO.getTableTicketsStatus(), status.getSelectedItem().toString());

        DatabaseDAO db = new DatabaseDAO(this);
        int affectedRows = db.updateTicket(ticketValues, ticket.getTicket_id());

        if (affectedRows > 0) {
            Toast.makeText(this, "Ticket has been updated", Toast.LENGTH_SHORT).show();
        }
    }
}
