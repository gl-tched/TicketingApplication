package com.kewishfagoe.android.ticketingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kewishfagoe.android.ticketingapplication.database.DatabaseDAO;
import com.kewishfagoe.android.ticketingapplication.database.User;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void attemptLogin(View view) throws NoSuchAlgorithmException {
        EditText username = (EditText) findViewById(R.id.usernameField);
        String usrVal = String.valueOf(username.getText());

        EditText password = (EditText) findViewById(R.id.passwordField);
        String pswVal = String.valueOf(password.getText());

        DatabaseDAO db = new DatabaseDAO(this);
        User user = db.findUserCreds(usrVal);

        if (user != null) {
            boolean isPswValid = user.comparePassword(pswVal);

            if (isPswValid) {
                // login success
                // transition to dashboard
                // Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                DashboardActivity.setUserId(user.getUser_id());
                DashboardActivity.setUserLevel(user.getUserLevel());
                Intent intent = new Intent(this, DashboardActivity.class);
                //intent.putExtra("user_id", user.getUser_id());
                //intent.putExtra("f_naam", user.getF_naam());
                //intent.putExtra("v_naam", user.getV_naam());
                //intent.putExtra("username", user.getUsername());
                //intent.putExtra("password", user.getPassword());
                //intent.putExtra("user_level", user.getUserLevel());
                startActivity(intent);
            } else {
                // login failed
                Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
