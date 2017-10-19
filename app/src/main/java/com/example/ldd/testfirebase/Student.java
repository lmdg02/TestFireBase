package com.example.ldd.testfirebase;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 16/10/2017.
 */

public class Student {
    public String idsv;
    public String Name;
    public String Address;
    public Integer Age;

    public Student() {
        // Với Firebase phải có constructer default khi nhận dữ liệu về .
    }

    public Student(String name, String address, Integer age) {
        Name = name;
        Address = address;
        Age = age;
    }

    public String getIdsv() {
        return idsv;
    }

    public void setIdsv(String idsv) {
        this.idsv = idsv;
    }

    @Override
    public String toString() {
        return Name + " - " + Address + " - " + Age;
    }

    public void setValues(Student updateStudent) {
        this.Name = updateStudent.Name;
        this.Address = updateStudent.Address;
        this.Age = updateStudent.Age;
    }
}
