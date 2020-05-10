package com.example.dosar;

import android.os.Build;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Products {
    private int _id;
    private String _productname;
    private String _college;
    private String _mobile;
    private String _date;
    private String _event;
    //Added this empty constructor in lesson 50 in case we ever want to create the object and assign it later.
    public Products(){
    }

    public String get_event() {
        return _event;
    }

    public void set_event(String event) {
        this._event = event;
    }

    public void set_college(String _college) {
        this._college = _college;
    }

    public void set_mobile(String _mobile) {
        this._mobile = _mobile;
    }

    public void set_date(String  _date) {
        this._date = _date;
    }

    public String get_college() {
        return _college;
    }

    public String get_mobile() {
        return _mobile;
    }

    public String  get_date() {
        return _date;
    }
    public Products(String _productname,String _college,String _mobile)
    {
        this._productname=_productname;
        this._college=_college;
        this._mobile= _mobile;
        this._date= new Date(System.currentTimeMillis()).toString();
    }
    public Products(String _productname,String _college,String _mobile,String event)
    {
        this._productname=_productname;
        this._college=_college;
        this._mobile= _mobile;
        this._date= new Date(System.currentTimeMillis()).toString();
        this._event=event;
    }
    public Products(String productName) {
        this._productname = productName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }
}
