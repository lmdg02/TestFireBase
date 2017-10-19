package com.example.ldd.testfirebase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button btnAdd;
    ListView listView;
    ArrayList<Student> listStudent;
    ArrayAdapter adapter = null;
    EditText edtName,edtAdress,edtAge;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference("Student");


        edtName = (EditText) findViewById(R.id.edtName);
        edtAdress = (EditText) findViewById(R.id.edtAdress);
        edtAge = (EditText) findViewById(R.id.edtAge);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        listView = (ListView) findViewById(R.id.listview);
        listStudent = new ArrayList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listStudent);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s = new Student(edtName.getText().toString(), edtAdress.getText().toString(), Integer.parseInt(edtAge.getText().toString()));
                addSV(s);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Sửa hay xóa");
                alertDialogBuilder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        rmSV(listStudent.get(position));

                    }
                });
                alertDialogBuilder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_custom);
                        dialog.setTitle("Update");
                        Button dialogButtonUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
                        final EditText edtNameUpdate = (EditText) dialog.findViewById(R.id.edtNameDialog);
                        final EditText edtAddressUpdate = (EditText) dialog.findViewById(R.id.edtAgeDialog);
                        final EditText edtAgeUpdate = (EditText) dialog.findViewById(R.id.edtAgeDialog);
                        dialogButtonUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Student s = listStudent.get(position);
                                Student student = new Student(edtNameUpdate.getText().toString(), edtAddressUpdate.getText().toString(), Integer.parseInt(edtAgeUpdate.getText().toString()));
                                s.setValues(student);
                                updateSV(s);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                student.setIdsv(dataSnapshot.getKey());
                listStudent.add(student);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Student student = dataSnapshot.getValue(Student.class);
                for (Student st : listStudent){
                    if (st.getIdsv().equals(key)){
                        st.setValues(student);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (Student s : listStudent){
                    if (s.getIdsv().equals(key)){
                        listStudent.remove(s);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addSV(Student s) {
        mDatabase.push().setValue(s);
    }

    private void updateSV(Student s) {
        mDatabase.child(s.getIdsv()).setValue(s);
    }

    private void rmSV(Student s) {
        mDatabase.child(s.getIdsv()).removeValue();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
//            AuthUI.getInstance().signOut(this)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(MainActivity.this,
//                                    "Đăng xuất thành công",
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                            finish();
//                        }
//                    });
        }
        if (item.getItemId() == R.id.Chat){
            startActivity(new Intent(this,TestChatApp.class));
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remain_menu,menu);
        return true;
    }
}
