package com.example.bhavikshah.propose;

import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtfld;
    private ImageView princess;
    private ImageView whome;
    private ImageView yesyou;
    private ImageView oktellme;
    private ImageView sleep;
    private ImageView birthday;
    private ImageView sea;
    private ImageView annoy;
    private ImageView cud;
    private ImageView proposal;
    private ImageView think;
    private ImageView jamun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtfld = findViewById(R.id.prinessTxtView);
        princess = (ImageView) findViewById(R.id.imageViewPrincess);
        princess.bringToFront();
        whome = (ImageView) findViewById(R.id.imageViewWhoMe);
        yesyou = (ImageView) findViewById(R.id.imageViewComeHere);
        oktellme = (ImageView) findViewById(R.id.imageViewsayit);
        sleep = (ImageView) findViewById(R.id.imageViewsleep);
        birthday = (ImageView) findViewById(R.id.imageViewBD);
        sea = (ImageView) findViewById(R.id.imageViewsea);
        annoy = (ImageView) findViewById(R.id.imageViewannoy);
        cud = (ImageView) findViewById(R.id.imageViewcud);
        proposal = (ImageView) findViewById(R.id.imageViewproposal);
        think = (ImageView) findViewById(R.id.imageViewthink);
        jamun = (ImageView) findViewById(R.id.imageViewjamun);
        jamun.setRotation(-90);
        princess.setOnClickListener(this);
        whome.setOnClickListener(this);
        yesyou.setOnClickListener(this);
        oktellme.setOnClickListener(this);
        cud.setOnClickListener(this);
        proposal.setOnClickListener(this);
        think.setOnClickListener(this);
        jamun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewPrincess:
                fade1(princess, whome);
                txtfld.setText("Who me?");
                break;

            case R.id.imageViewWhoMe:
                fade1(whome, yesyou);
                txtfld.setText("Come Here! I want to tell you something");
                break;

            case R.id.imageViewComeHere:
                fade1(yesyou, oktellme);
                txtfld.setText("Ok!! Say");
                break;

            case R.id.imageViewsayit:
                fade2(oktellme, cud);
                txtfld.setText("We have Shared many such moments!!!");
                break;

            case R.id.imageViewcud:
                fade1(cud, proposal);
                txtfld.setText("I Love You and want to Live with you forever!!");
                break;

            case R.id.imageViewproposal:
                fade1(proposal, think);
                txtfld.setText("Think and give me Answer");
                break;

            case R.id.imageViewthink:
                fade1(think, jamun);
                txtfld.setText("Cheers!! Waiting for your answer!!");

                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },
                        5000
                );

                break;

        }

    }

    public void fade1(ImageView v1, ImageView v2) {

        v1.animate().alpha(0f).setDuration(2000);
        v2.animate().alpha(1f).setDuration(2000);
        ;
        v2.bringToFront();
    }

    public void fade2(ImageView v1, ImageView v2) {

        v1.animate().alpha(0f).setDuration(1000);
        sleep.animate().alpha(1f).setDuration(2000);
        sleep.setVisibility(View.VISIBLE);
        sleep.bringToFront();
        birthday.setVisibility(View.VISIBLE);
        birthday.animate().alpha(1f).setDuration(2000).setStartDelay(2000);
        birthday.bringToFront();
        sea.animate().alpha(1f).setDuration(2000).setStartDelay(5000);
        sea.bringToFront();
        annoy.animate().alpha(1f).setDuration(2000).setStartDelay(8000);
        annoy.bringToFront();
        v2.animate().alpha(1f).setDuration(2000).setStartDelay(11000);
        v2.bringToFront();
    }

}
