package com.wy.rafael.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wy.rafael.R;
import com.wy.rafael.models.Buddy;

public class BuddiesActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private DatabaseReference buddiesRef;
    private FirebaseRecyclerAdapter<Buddy, BuddyViewHolder> adapter;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(BuddiesActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    loadUi();
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    private void loadUi() {
        setContentView(R.layout.activity_buddies);
        fab = (FloatingActionButton) findViewById(R.id.addBuddyFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.buddiesRecyclerView);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        buddiesRef = FirebaseDatabase.getInstance().getReference().
                child("users").child(user.getUid()).child("buddies");
        adapter =
                new FirebaseRecyclerAdapter<Buddy, BuddyViewHolder>(
                        Buddy.class,
                        R.layout.buddy_item,
                        BuddyViewHolder.class,
                        buddiesRef
                ) {
                    @Override
                    protected void populateViewHolder(BuddyViewHolder viewHolder, Buddy model, int position) {
                        viewHolder.name.setText(model.getName());
                        viewHolder.mobile.setText(model.getMobile());
                    }
                };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int buddiesCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (buddiesCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void createDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_buddy, null);
        final EditText nameView = (EditText) dialogView.findViewById(R.id.name);
        final EditText mobileView = (EditText) dialogView.findViewById(R.id.mobile);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameView.getText().toString().trim();
                        String mobile = mobileView.getText().toString().trim();

                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile)) {
                            Toast.makeText(BuddiesActivity.this, "Please enter a name/number!", Toast.LENGTH_LONG);
                            return;
                        }
                        createBuddy(name, mobile);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }

    private void createBuddy(String name, String mobile) {
        buddiesRef = FirebaseDatabase.getInstance().getReference().
                child("users").child(user.getUid()).child("buddies");
        buddiesRef.push().setValue(new Buddy(name, mobile), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(BuddiesActivity.this, "New buddy saved!", Toast.LENGTH_LONG);
                }
                else {
                    Toast.makeText(BuddiesActivity.this, "Failed to add new buddy!", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private static class BuddyViewHolder extends RecyclerView.ViewHolder {
        TextView name, mobile;

        public BuddyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.mobile = (TextView) itemView.findViewById(R.id.mobile);
        }

    }
}
