package com.kewishfagoe.android.ticketingapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.User;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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


    public void attemptRegistration(View view) throws NoSuchAlgorithmException {
        EditText lastName = (EditText) findViewById(R.id.lastNameField);
        EditText firstName = (EditText) findViewById(R.id.firstNameField);
        EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberField);
        EditText address = (EditText) findViewById(R.id.addressField);
        EditText emailAddress = (EditText) findViewById(R.id.emailField);
        EditText username = (EditText) findViewById(R.id.regUserNameField);
        EditText password = (EditText) findViewById(R.id.regPasswordField);

        String sLastName = String.valueOf(lastName.getText());
        String sFirstName = String.valueOf(firstName.getText());
        String sPhoneNumber = String.valueOf(phoneNumber.getText());
        String sAddress = String.valueOf(address.getText());
        String sEmailAddress = String.valueOf(emailAddress.getText());
        String sUsername = String.valueOf(username.getText());
        String sPassword = String.valueOf(password.getText());

        HashMap<String, String> usrInputs = new HashMap<>();
        usrInputs.put("Lastname", sLastName);
        usrInputs.put("Firstname", sFirstName);
        usrInputs.put("Phone Number", sPhoneNumber);
        usrInputs.put("Address", sAddress);
        usrInputs.put("Username", sUsername);
        usrInputs.put("Password", sPassword);

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
        User userByUsername = db.findUserCreds(sUsername);
        User userByPhoneNumber = db.findUserByPhoneNumber(sPhoneNumber);

        if (userByUsername == null && userByPhoneNumber == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            ContentValues userValues = new ContentValues();
            userValues.put(DatabaseDAO.getTableUsersRegDate(), dateFormat.format(date));
            userValues.put(DatabaseDAO.getTableUsersFnaam(), sLastName);
            userValues.put(DatabaseDAO.getTableUsersVnaam(), sFirstName);
            userValues.put(DatabaseDAO.getTableUsersAdres(), sAddress);
            userValues.put(DatabaseDAO.getTableUsersTelefoon(), sPhoneNumber);
            userValues.put(DatabaseDAO.getTableUsersEmail(), sEmailAddress);
            userValues.put(DatabaseDAO.getTableUsersUsername(), sUsername);
            userValues.put(DatabaseDAO.getTableUsersPassword(), User.getPasswordHash(sPassword));
            userValues.put(DatabaseDAO.getTableUsersLevel(), DatabaseDAO.getUserLevel());

            long rowId = db.insertUser(DatabaseDAO.getTableUsersName(), userValues);
            if (rowId != -1) {
                Intent intent = new Intent(this, DashboardActivity.class);
                User user = db.findUserCreds(sUsername);
                intent.putExtra("user_id", user.getUser_id());
                intent.putExtra("f_naam", user.getF_naam());
                intent.putExtra("v_naam", user.getV_naam());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("password", user.getPassword());
                intent.putExtra("user_level", user.getUserLevel());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Unable to register user '" + sUsername, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (userByUsername == null && userByPhoneNumber != null) {
                Toast.makeText(this, "Phone number is not unique", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username '" + sUsername + "' already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
