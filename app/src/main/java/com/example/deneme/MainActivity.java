package com.example.deneme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button loginBtn,regButton;
    TextView usernameTW,passTW;
    TextView gecici;
    EditText usernameET,passET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.buttonLogin);
        usernameTW = (TextView) findViewById(R.id.tw1);
        passTW = (TextView) findViewById(R.id.tw2);
        regButton = (Button) findViewById(R.id.buttonRegister);
        usernameET=(EditText) findViewById(R.id.ETusername);
        passET = (EditText) findViewById(R.id.ETpassword);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ToDoList.class);
                intent.putExtra("USERNAME", usernameET.getText().toString());
                startActivity(intent);
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoList.class);
                intent.putExtra("USERNAME", usernameET.getText().toString());
                startActivity(intent);
            }
        });
    }
}
