package com.example.ldd.testfirebase;

/**
 * Created by Ki Thuat 88 on 10/18/2017.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends
        ArrayAdapter<ChatMessage>
{
    Activity context=null;
    ArrayList<ChatMessage>myArray=null;
    int layoutId;

    public MyArrayAdapter(Activity context,
                          int layoutId,
                          ArrayList<ChatMessage>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater=
                context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        if(myArray.size()>0 && position>=0)
        {
            final TextView txtUser=(TextView)
                    convertView.findViewById(R.id.message_user);
            final TextView txtTime=(TextView)
                    convertView.findViewById(R.id.message_time);
            final TextView txtText=(TextView)
                    convertView.findViewById(R.id.message_text);
            final ChatMessage emp=myArray.get(position);

            txtUser.setText(emp.getMessageUser());
            txtTime.setText(emp.getMessageTime());
            txtText.setText(emp.getMessageText());
        }
        return convertView;
    }
}
