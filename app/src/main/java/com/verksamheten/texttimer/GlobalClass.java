package com.verksamheten.texttimer;

import android.app.Application;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class GlobalClass extends Application{


    private ContactsClass contact;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String message;
    private String phoneNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ContactsClass getContact() {
        return contact;
    }

    public void setContact(ContactsClass contact) {
        this.contact = contact;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
    }
}
