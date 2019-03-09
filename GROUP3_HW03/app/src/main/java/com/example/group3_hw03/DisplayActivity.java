package com.example.group3_hw03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class DisplayActivity extends AppCompatActivity {

    Button finishButton;
    TextView trackValue;
    TextView genreValue;
    TextView artistValue;
    TextView albumValue;
    TextView trackPrice;
    TextView albumPrice;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("iTunes Music Search");

        finishButton = findViewById(R.id.finish_button);
        trackValue = findViewById(R.id.track_Value);
        genreValue = findViewById(R.id.genre_Value);
        artistValue = findViewById(R.id.artist_Value);
        albumValue = findViewById(R.id.album_value);
        trackPrice = findViewById(R.id.track_price);
        albumPrice = findViewById(R.id.album_price);
        imageView = findViewById(R.id.image_View);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MusicTrack trackDetails = (MusicTrack) getIntent().getSerializableExtra("trackDetails");
        Picasso.get().load(trackDetails.getArtworkUrl100()).into(imageView);
        trackValue.setText(trackDetails.getTrackName());
        genreValue.setText(trackDetails.getPrimaryGenreName());
        artistValue.setText(trackDetails.getArtistName());
        albumValue.setText(trackDetails.getCollectionName());
        trackPrice.setText(""+trackDetails.getTrackPrice()+"$");
        albumPrice.setText(trackDetails.getCollectionPrice()+"$");
        Log.d("IMAGE", trackDetails.getArtworkUrl100());


    }
}
