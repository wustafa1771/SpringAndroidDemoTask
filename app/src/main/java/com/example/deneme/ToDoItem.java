package com.example.deneme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ToDoItem extends AppCompatActivity {


    final String baseURL="http://192.168.1.42:8080/";
    String username,listname;
    LinearLayout linearLayout;
    EditText editText;
    Context context;
    Button button;

    TextView[] listTexts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item);
        username = getIntent().getStringExtra("USERNAME");
        listname = getIntent().getStringExtra("LISTNAME");
        editText = (EditText) findViewById(R.id.editTxt);
        button = (Button) findViewById(R.id.button);
        context = this;
        listTexts = new TextView[5];
        listTexts[0]= (TextView) findViewById(R.id.ListText0);
        listTexts[1]= (TextView) findViewById(R.id.ListText1);
        listTexts[2]= (TextView) findViewById(R.id.ListText2);
        listTexts[3]= (TextView) findViewById(R.id.ListText3);
        listTexts[4]= (TextView) findViewById(R.id.ListText4);
        linearLayout = findViewById(R.id.linearLayout);
        populateTextView(baseURL+"getTodoItems?username="+username+"&todoListName="+listname,linearLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem(editText.getText().toString(),username);
                populateTextView(baseURL+"getTodoItems?username="+username+"&todoListName="+listname,linearLayout);

            }
        });
    }

    private void addNewItem(String toString, String username) {
        final String url = baseURL+"addTodoItem?username="+username+"&todoListName="+listname+"&todoItemName="+editText.getText();
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = null;
                try {
                    response= client.newCall(request).execute();
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

            }
        }.execute();

    }

    public void populateTextView( final String url, final LinearLayout linearLayout){
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = null;
                try {
                    response= client.newCall(request).execute();
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                String jsonArrayString;
                jsonArrayString = o.toString();
                String geciciStr = null;
                try {
                    JSONArray jsonArray = new JSONArray(jsonArrayString);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        geciciStr+=i+" th json object is "+jsonObj.toString();
                        listTexts[i].setText(jsonObj.getString("todoItemName"));
                        listTexts[i].setTextSize(30);
                        listTexts[i].setClickable(true);
                        final int finalI = i;
                        listTexts[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                    }
                    LinearLayout layout = new LinearLayout(context);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();


    }

}
