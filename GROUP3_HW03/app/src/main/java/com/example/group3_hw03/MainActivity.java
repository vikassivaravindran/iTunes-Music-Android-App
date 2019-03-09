package com.example.group3_hw03;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView limit_integer_value;
    public SearchView searchView;
    TextView load_button;
    ProgressBar progressBar;
    SeekBar seekBar;
    Button searchButton;
    Button resetButton;
    String artistName;
    String musicTrackAPI;
    ListView list_view;
    Switch switchOption;
    boolean status = true;
    Intent intent;
    ArrayList<MusicTrack> values;
    SimpleDateFormat presentFormat = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat requiredFormat = new SimpleDateFormat("mm-dd-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Music Search");

        searchButton = findViewById(R.id.search_button);
        intent = new Intent(MainActivity.this, DisplayActivity.class);
        resetButton = findViewById(R.id.reset_button);
        load_button = findViewById(R.id.Load_button);
        searchView = findViewById(R.id.searchView);
        list_view = (ListView)findViewById(R.id.list_view_music);
        switchOption = findViewById(R.id.switch1);
        progressBar = findViewById(R.id.progressBar);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                limit_integer_value.setText(""+10);
                searchView.setQuery("", false);
                searchView.clearFocus();
                seekBar.setProgress(0);
                list_view.setVisibility(View.INVISIBLE);
                switchOption.setChecked(true);
                artistName = null;
            }
        });

        switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (artistName == null || searchView.getQuery().length() <=0 ) {
                    artistName = searchView.getQuery().toString().replace(" ", "+");
                }

                else {
                    if (!isChecked) {
                        status = false;
                        displayListView(values, status);
                    } else {
                        status = true;
                        displayListView(values, status);
                    }
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Clicked", ""+position);
                Bundle b = new Bundle();
                MusicTrack t = values.get(position);
                Log.d("The Clicked Details",t.toString());
                intent.putExtra("trackDetails",t);
                startActivity(intent);
            }
        });

       // Log.d("track Name",trackName);
        seekBar = findViewById(R.id.seekBar);
        limit_integer_value = findViewById(R.id.limit_integer_value);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getProgress() < 10){
                    limit_integer_value.setText(""+10);
                }else {
                    limit_integer_value.setText("" + progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button Clicked", "Search");
                Log.d("track Name",searchView.getQuery().toString());
                artistName = searchView.getQuery().toString();
                list_view.setVisibility(View.INVISIBLE);

                if(artistName.length() <= 0){
                    Toast.makeText(MainActivity.this, "Please enter the artist name",Toast.LENGTH_SHORT).show();
                }

                else {
                    String[] searchResults = artistName.split(" ");
                    if (searchResults.length > 1) {
                        artistName = searchView.getQuery().toString().replace(" ", "+");
                    }

                    musicTrackAPI = "https://itunes.apple.com/search" + "?" +
                            "term=" + artistName + "&" + "limit=" + limit_integer_value.getText().toString();
                    new GetMusicTrack().execute(musicTrackAPI);

                    Log.d("The Value", "" + musicTrackAPI);
                }
            }
        });

    }

    private void displayListView(ArrayList<MusicTrack> musicTracks, boolean condition) {

        if(condition) {
            Log.d("Current status","date");
            Collections.sort(musicTracks, new Comparator<MusicTrack>() {
                @Override
                public int compare(MusicTrack o1, MusicTrack o2) {
                    try {
                        return requiredFormat.parse(o1.getDate()).compareTo(requiredFormat.parse(o2.getDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }else if(!condition){
            Log.d("Current status","price");
            Collections.sort(musicTracks, new Comparator<MusicTrack>() {
                @Override
                public int compare(MusicTrack o1, MusicTrack o2) {
                    try {
                        return (int) Math.round(o1.getTrackPrice() - o1.getTrackPrice());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }
        MusicTrackAdapter adapter = new MusicTrackAdapter(MainActivity.this, R.layout.musictracklistview, musicTracks);
        progressBar.setVisibility(View.INVISIBLE);
        load_button.setVisibility(View.INVISIBLE);
        list_view.setVisibility(View.VISIBLE);
        list_view.setAdapter(adapter);
        list_view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

    }

    public class GetMusicTrack extends AsyncTask<String, Integer, ArrayList<MusicTrack>>{


        @Override
        protected ArrayList<MusicTrack> doInBackground(String... strings) {

            values = new ArrayList<>();
            Log.d("Execution","inside Background");
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;

            try {
                Log.d("demo", "In Async"+strings[0]);
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String trackViewURL = IOUtils.toString(connection.getInputStream(),"UTF-8");

                    JSONObject rootObject = new JSONObject(trackViewURL);
                    JSONArray trackDetails = rootObject.getJSONArray("results");

                    for(int i = 0; i < trackDetails.length(); i++){

                        JSONObject trackObj = trackDetails.getJSONObject(i);
                        MusicTrack mTrack = new MusicTrack();
                        mTrack.setArtistName(trackObj.getString("artistName"));
                        mTrack.setTrackName(trackObj.getString("trackName"));
                        mTrack.setPrimaryGenreName(trackObj.getString("primaryGenreName"));
                        mTrack.setTrackPrice(trackObj.getDouble("trackPrice"));
                        mTrack.setCollectionName(trackObj.getString("collectionName"));
                        mTrack.setCollectionPrice(trackObj.getString("collectionPrice"));
                        mTrack.setArtworkUrl100(trackObj.getString("artworkUrl100"));
                        Log.d("Int",""+mTrack.getTrackPrice());
                        String myDate = trackObj.getString("releaseDate").substring(0,10);
                        Date mySampleDate = presentFormat.parse(myDate);
                        String receivedDate = requiredFormat.format(mySampleDate);
                        mTrack.setDate(receivedDate);
                        publishProgress(i);
                        values.add(mTrack);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }catch (ParseException e){
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return values;
        }

        @Override
        protected void onPreExecute() {
            load_button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            load_button.setText("LOADING");
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MusicTrack> musicTracks) {
            Log.d("Received Tracks",""+musicTracks.size());
            Log.d("Received Tracks",""+musicTracks.toString());
            displayListView(musicTracks, status);
            super.onPostExecute(musicTracks);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int percentage = progressBar.getProgress()+values[0];
            progressBar.setProgress(percentage);
            super.onProgressUpdate(values);
        }
    }


}
