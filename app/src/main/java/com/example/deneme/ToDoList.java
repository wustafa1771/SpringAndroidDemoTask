package com.example.deneme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import android.widget.LinearLayout.LayoutParams;

public class ToDoList extends AppCompatActivity {
    final String baseURL="http://192.168.1.42:8080/";
    String txt;
    EditText editText;
    LinearLayout linearLayout;
    TextView[] listTexts;
    Button btn;
    Context context;
    String username;
    TextView[] tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        listTexts = new TextView[5];
        listTexts[0]= (TextView) findViewById(R.id.ListText0);
        listTexts[1]= (TextView) findViewById(R.id.ListText1);
        listTexts[2]= (TextView) findViewById(R.id.ListText2);
        listTexts[3]= (TextView) findViewById(R.id.ListText3);
        listTexts[4]= (TextView) findViewById(R.id.ListText4);
        editText = (EditText) findViewById(R.id.editTxt);
        btn = (Button) findViewById(R.id.button) ;
        context = this;
        username = getIntent().getStringExtra("USERNAME");
        System.out.println("your name in todolist activity  ıs "+username);
        populateTextView(baseURL+"getTodoLists?username="+username,linearLayout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewList(editText.getText().toString(),username);
                populateTextView(baseURL+"getTodoLists?username="+username,linearLayout);
            }
        });
    }

    private void addNewList(String text, final String username) {
        final String url = baseURL+"addTodoList?username="+username+"&todoListName="+text;
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
                        listTexts[i].setText(jsonObj.getString("todoListName"));
                        listTexts[i].setTextSize(30);
                        listTexts[i].setClickable(true);
                        final int finalI = i;
                        listTexts[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(ToDoList.this, ToDoItem.class);
                                intent.putExtra("USERNAME", username);
                                intent.putExtra("LISTNAME", listTexts[finalI].getText());
                                startActivity(intent);
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
                        listTexts[i].setText(jsonObj.getString("todoListName"));
                        listTexts[i].setTextSize(30);
                        listTexts[i].setClickable(true);
                        final int finalI = i;
                        listTexts[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(ToDoList.this, ToDoItem.class);
                                intent.putExtra("USERNAME", username);
                                intent.putExtra("LISTNAME", listTexts[finalI].getText());
                                startActivity(intent);
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
