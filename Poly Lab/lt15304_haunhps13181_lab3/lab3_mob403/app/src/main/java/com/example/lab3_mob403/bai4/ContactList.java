package com.example.lab3_mob403.bai4;

import com.example.lab3_mob403.Model.Contact;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactList {
    @SerializedName("contacts")
    @Expose
    private ArrayList<Contact> contacts = new ArrayList<>();
    /**
     * @return The contacts */
    public ArrayList<Contact> getContacts() { return contacts;
    }
    /**
     * @param contacts The contacts */
    public void setContacts(ArrayList<Contact> contacts) { this.contacts = contacts;
    }

}
