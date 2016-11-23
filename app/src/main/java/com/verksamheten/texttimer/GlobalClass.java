package com.verksamheten.texttimer;

import android.app.Application;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class GlobalClass extends Application{


    private ContactsClass contact;
    private DatePicker datePicker;
    private TimePicker timePicker;

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
