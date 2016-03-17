package com.kewishfagoe.android.ticketingapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
        Button updateBtn = (Button) findViewById(R.id.ticketUpdateButton);

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

        int userLevel;
        try {
            userLevel = extras.getInt("user_level");
            if (DashboardActivity.getUserLevel() == 1) userLevel = 1;
        } catch (NullPointerException e) {
            userLevel = DashboardActivity.getUserLevel();
        }

        // Disable update functionality for non-admin users
        if (userLevel == 2) {
            disableEditText(title);
            disableEditText(description);
            disableSpinner(type);
            disableSpinner(status);
            disableEditText(repdate);
            updateBtn.setVisibility(View.GONE);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_ticketdetails, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.logoutUserTDetails) {
//            logoutUserTD();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void disableEditText(EditText fld) {
        fld.setFocusable(false);
        fld.setCursorVisible(false);
        fld.setKeyListener(null);
        fld.setBackgroundColor(Color.TRANSPARENT);
    }

    private void disableSpinner(Spinner sp) {
        sp.setClickable(false);
        sp.setFocusable(false);
        sp.setBackground(null);
    }

    private void setNotification() {
        int id = 1;

        Intent resultIntent = new Intent(this, TicketDetailsActivity.class);
        resultIntent.putExtra("ticket_id", ticket.getTicket_id());
        resultIntent.putExtra("user_level", 2);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(TicketDetailsActivity.class);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_stat_logo);
        builder.setContentTitle("Ticket updated");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);

        StringBuilder sbContent = new StringBuilder();
        sbContent.append("Title: ");
        sbContent.append(title.getText());
        builder.setContentText(sbContent);

        StringBuilder sbSub = new StringBuilder();
        sbSub.append("Status: ");
        sbSub.append(status.getSelectedItem());
        builder.setSubText(sbSub);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
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
            setNotification();
        }
    }

//    public void logoutUserTD() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
}
