package com.example.kontaktbog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String PREF_NAME = "contact_prefs";
    private static final String CONTACT_KEY = "contact_list";

    private static ContactManager instance;
    private ArrayList<Contact> contacts;
    private final Gson gson = new Gson();
    private final Type CONTACT_LIST_TYPE = new TypeToken<ArrayList<Contact>>() {}.getType();
    private ContactManager() {
        contacts = new ArrayList<>();
    }

    public static synchronized ContactManager getInstance() {
        if (instance == null) {
            instance = new ContactManager();
        }
        return instance;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CONTACT_KEY, null);
        ArrayList<Contact> loaded = gson.fromJson(json, CONTACT_LIST_TYPE);
        contacts.clear();
        if (loaded != null) contacts.addAll(loaded);
    }

    public void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CONTACT_KEY, gson.toJson(contacts));
        editor.apply();
    }

    public void addContact(Contact contact, Context context) {
        if (contact != null) {
            contacts.add(contact);
            save(context);
        }
    }

}