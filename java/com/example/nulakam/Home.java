package com.example.nulakam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

public class Home extends AppCompatActivity {
    ShapeableImageView pa,bh,bs;

    //ProgressBar pb;

    //int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pa = findViewById(R.id.profilebtn);
        bh = findViewById(R.id.bookhistory);
        bs = findViewById(R.id.booksearchbtn);
        //pb = findViewById(R.id.progressBar);

        pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*pb.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        pb.setProgress(counter);

                        if (counter==50){
                            timer.cancel();
                        }


                    }
                };


                timer.schedule(timerTask,50,50);*/
                Intent in = new Intent(Home.this,ProfileActivity.class);
                startActivity(in);


            }

        });

        bh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,SearchActivity.class);
                startActivity(i);

            }
        });

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Home.this,BookSearch.class);
                startActivity(inte);

            }
        });

    }
}