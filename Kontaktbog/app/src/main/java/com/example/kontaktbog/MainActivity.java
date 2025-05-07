package com.example.kontaktbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private TextView emptyText;
    private final ActivityResultLauncher<Intent> addContactLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Contact contact = (Contact) result.getData().getSerializableExtra("contact");
                    if (contact != null) {
                        ContactManager.getInstance().addContact(contact, this);
                        adapter.notifyItemInserted(ContactManager.getInstance().getContacts().size() - 1);

                        // makes a check to see if TextView "emptyText" is displayed or not.
                        checkListState();
                        Toast.makeText(this, "Contact has been added", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactManager.getInstance().load(this);

        recyclerView = findViewById(R.id.recyclerView);
        emptyText    = findViewById(R.id.emptyText);

        // Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // needed here as it normally just removes it.
            getSupportActionBar().setTitle("Contacts list");
        }


        // recyclerView to use adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(ContactManager.getInstance().getContacts());
        recyclerView.setAdapter(adapter);

        // DEBUG TEST
        // this is for reset of data
        // ContactManager.getInstance().resetData(this);
        // this is for start data. It also clears list
        // ContactManager.getInstance().populateListWithData(this);



        checkListState();

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            addContactLauncher.launch(intent);
        });
    }

    private void checkListState() {
        if (adapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }
}