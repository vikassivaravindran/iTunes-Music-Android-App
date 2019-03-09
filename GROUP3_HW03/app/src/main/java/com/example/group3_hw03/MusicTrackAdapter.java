package com.example.group3_hw03;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
//import java.util.List;

public class MusicTrackAdapter extends ArrayAdapter<MusicTrack>{

    public MusicTrackAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MusicTrack> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MusicTrack listView = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.musictracklistview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.trackResult = convertView.findViewById(R.id.track_result);
            viewHolder.artistResult = convertView.findViewById(R.id.artist_result);
            viewHolder.priceResult = convertView.findViewById(R.id.price_result);
            viewHolder.dateResult = convertView.findViewById(R.id.date_result);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.trackResult.setText(listView.getTrackName());
        viewHolder.artistResult.setText(listView.getArtistName());
        viewHolder.priceResult.setText(String.valueOf(listView.getTrackPrice()).concat("$"));
        viewHolder.dateResult.setText(listView.getDate());

        return convertView;
    }

    private static class ViewHolder{
        TextView trackResult;
        TextView artistResult;
        TextView priceResult;
        TextView dateResult;
    }

}
