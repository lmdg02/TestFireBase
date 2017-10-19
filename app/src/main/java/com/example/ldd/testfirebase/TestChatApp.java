package com.example.ldd.testfirebase;

import android.os.Message;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestChatApp extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    MyArrayAdapter arrayAdapter = null;
    ArrayList<ChatMessage> listMessage;

    DatabaseReference mDatabase;
    EditText input;

    public String test() {
        String test;
        if ((test = FirebaseAuth.getInstance()
                .getCurrentUser().getDisplayName()) == null) {
            test = FirebaseAuth.getInstance()
                    .getCurrentUser().getEmail();
        }else{
            test = FirebaseAuth.getInstance()
                    .getCurrentUser().getDisplayName();
        }

        return test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chat_app);
        mDatabase = FirebaseDatabase.getInstance().getReference("AppChat");
        input = (EditText) findViewById(R.id.input);

//        FloatingActionButton fab =
//                (FloatingActionButton) findViewById(R.id.fab);
        ImageView img = (ImageView) findViewById(R.id.image);
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        listMessage = new ArrayList<>();
        arrayAdapter = new MyArrayAdapter(this, R.layout.message, listMessage);
        listOfMessages.setAdapter(arrayAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage cm = new ChatMessage(input.getText().toString(), "Lee");

                mDatabase.push().setValue(new ChatMessage(input.getText().toString(),
                        test()));
                input.setText("");
            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage cm = dataSnapshot.getValue(ChatMessage.class);
                cm.setId(dataSnapshot.getKey());
                listMessage.add(cm);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
