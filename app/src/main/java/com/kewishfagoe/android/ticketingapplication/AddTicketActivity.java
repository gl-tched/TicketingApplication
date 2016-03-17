package com.kewishfagoe.android.ticketingapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.Ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    private void setNotification(Ticket ticket) {
        int id = 1;

        Intent resultIntent = new Intent(this, TicketDetailsActivity.class);
        resultIntent.putExtra("ticket_id", ticket.getTicket_id());
        resultIntent.putExtra("user_level", DashboardActivity.getUserLevel() == 2 ? 1 : 2);
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
        builder.setContentTitle("New ticket");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);

        StringBuilder sbContent = new StringBuilder();
        sbContent.append("Title: ");
        sbContent.append(ticket.getTitle());
        builder.setContentText(sbContent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
    }

    public void attemptTicketInsert(View view) {
        EditText title = (EditText) findViewById(R.id.newTitleField);
        EditText description = (EditText) findViewById(R.id.newDescriptionField);

        String sTitle = String.valueOf(title.getText());
        String sDescription = String.valueOf(description.getText());

        HashMap<String, String> usrInputs = new HashMap<>();
        usrInputs.put("Title", sTitle);
        usrInputs.put("Description", sDescription);

        String message = "";
        for (HashMap.Entry<String, String> input : usrInputs.entrySet()) {
            String key = input.getKey();
            String value = input.getValue();

            if (value.isEmpty()) message += String.format("%s is empty\n", key);
        }

        // Check whether required fields are empty
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseDAO db = new DatabaseDAO(this);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        ContentValues ticketValues = new ContentValues();
        ticketValues.put(DatabaseDAO.getTableTicketsCreationDate(), dateFormat.format(date));
        ticketValues.put(DatabaseDAO.getTableTicketsTypeProbleem(), "HARDWARE");
        ticketValues.put(DatabaseDAO.getTableTicketsTitle(), sTitle);
        ticketValues.put(DatabaseDAO.getTableTicketsDescription(), sDescription);
        ticketValues.put(DatabaseDAO.getTableTicketsStatus(), "OPEN");
        long rowId1 = db.insertTicket(ticketValues);

        if (rowId1 != -1) {
            Ticket ticket = db.findTicketByRowID(rowId1);

            if (ticket != null) {
                ContentValues userTicketValues = new ContentValues();
                userTicketValues.put(DatabaseDAO.getTableUserTicketsTid(), ticket.getTicket_id());
                userTicketValues.put(DatabaseDAO.getTableUserTicketsUid(), DashboardActivity.getUserId());
                long rowId2 = db.insertUserTicket(userTicketValues);

                if (rowId2 != -1) {
                    Toast.makeText(this, "Ticket has been saved", Toast.LENGTH_SHORT).show();
                    setNotification(ticket);
                } else {
                    Toast.makeText(this, "Failed to save ticket", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Ticket was not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to save ticket", Toast.LENGTH_SHORT).show();
        }
    }
}
