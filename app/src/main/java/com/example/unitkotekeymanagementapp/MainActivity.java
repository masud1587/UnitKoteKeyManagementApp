package com.example.unitkotekeymanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pusher.pushnotifications.PushNotifications;

public class MainActivity extends AppCompatActivity {
    CardView mProfileListBtn;
    CardView mAuthenticationBtn;
    CardView mLogBtn;
    CardView mLogOutBtn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PushNotifications.start(this, "4fd41a40-074d-4b76-bab8-dc56aca7eba6");
        PushNotifications.addDeviceInterest("hello");



        mProfileListBtn = findViewById(R.id.profileCard);
        mAuthenticationBtn = findViewById(R.id.authCard);
        mLogBtn = findViewById(R.id.logCard);
        mLogOutBtn = findViewById(R.id.logOutCard);
        progressBar = findViewById(R.id.progressBar2);

        mProfileListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), ProfileList.class));
                progressBar.setVisibility(View.GONE);
            }
        });

        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), Log.class));
                progressBar.setVisibility(View.GONE);
            }
        });

        mAuthenticationBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("FLAG");
                DatabaseReference myRef2 = database.getReference("FLAG");

                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(value.equals("A")){
                            startActivity(new Intent(getApplicationContext(), Alarm.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value = dataSnapshot.getValue(String.class);
                                    if(value.equals("Y")){
                                        startActivity(new Intent(getApplicationContext(), Authentication.class));
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "No New Approval request found!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });
    }

}