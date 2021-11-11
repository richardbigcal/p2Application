package com.jose.p2system;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    static final float END_SCALE = 0.7f;


    Button btndelro, btnbarbie, btnblack, btnghost;



    ImageView menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;

    CardView card1,card2,card4;
    Dialog mdialog;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);




        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card4 = findViewById(R.id.card4);


        btndelro = findViewById(R.id.btndelro);
        btnbarbie = findViewById(R.id.btnbarbie);
        btnblack = findViewById(R.id.btnblack);
        btnghost = findViewById(R.id.btnghost);

        // Link
        ///  link = findViewById(R.id.link);




        btndelro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DelroActivity.class);
                startActivity(intent);

            }
        });

        btnbarbie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Barbie1Activity.class);
                startActivity(intent);

            }
        });

        btnghost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GhostActivity.class);
                startActivity(intent);
            }
        });

        btnblack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Black1Activity.class);
                startActivity(intent);
            }
        });






        mdialog = new Dialog(this);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                //        startActivity(intent);
                Toast.makeText(MainActivity.this, "THIS WAS CLICKED", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        });



        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LostandFoundActivity.class);
                startActivity(intent);

            }
        });







        navigationDrawer();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            Bitmap imageData = null;
            if (resultCode == RESULT_OK) {
                imageData = (Bitmap) data.getExtras().get("data");

                Intent i = new Intent(this, GgeoActivity.class);
                i.putExtra("name", imageData);
                startActivity(i);


            }
        }
    }



    private void navigationDrawer() { navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }});

        animateNavigationDrawer();



    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide( View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);


                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleX(offsetScale);

                final float xoffset = drawerView.getWidth() * slideOffset;
                final float xoffsetDiff = contentView.getWidth()* diffScaledOffset / 2;
                final float xTransaction = xoffset - xoffsetDiff;
                contentView.setTranslationX(xTransaction);


            }

            @Override
            public void onDrawerOpened( View drawerView) {

            }

            @Override
            public void onDrawerClosed( View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }







    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Home", Toast.LENGTH_SHORT).show();

                break;

            case R.id.nav_rate:

                Toast.makeText(getApplicationContext(),"Rate us", Toast.LENGTH_SHORT).show();

                break;

            case  R.id.nav_learn_more:

                Toast.makeText(getApplicationContext(),"Learn more", Toast.LENGTH_SHORT).show();

                break;

            case R.id.nav_contact_us:

                Toast.makeText(getApplicationContext(),"Contact us", Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(MainActivity.this, HomeScreenActivity.class);
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"Profile", Toast.LENGTH_SHORT).show();

                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



}


