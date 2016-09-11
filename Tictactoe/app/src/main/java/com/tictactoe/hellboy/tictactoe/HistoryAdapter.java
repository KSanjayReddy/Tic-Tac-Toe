package com.tictactoe.hellboy.tictactoe;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by anwesh on 9/9/16.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<scoreSetup> scoreSetups;

    public HistoryAdapter(ArrayList<scoreSetup> scoreSetups, Context context){
        this.scoreSetups = new ArrayList<>(scoreSetups);
        this.context = context;
    }

    @Override
    public int getCount() {
        return scoreSetups.size();
    }

    @Override
    public Object getItem(int i) {
        return scoreSetups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View _view, ViewGroup viewGroup) {
        View view = _view;
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_history, null);
        }
        else{
            view = _view;
        }
        scoreSetup scoreSetup = scoreSetups.get(i);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.p1name = (TextView) view.findViewById(R.id.player1_name);
        viewHolder.p1score = (TextView) view.findViewById(R.id.player1_score);
        viewHolder.p2name = (TextView) view.findViewById(R.id.player2_name);
        viewHolder.p2score = (TextView) view.findViewById(R.id.player2_score);

        viewHolder.p1name.setText(scoreSetup.getPlayer1_name());
        viewHolder.p1score.setText(scoreSetup.getPlayer1_score());
        viewHolder.p2name.setText(scoreSetup.getPlayer2_name());
        viewHolder.p2score.setText(scoreSetup.getPlayer2_score());

        return view;
    }

    static class ViewHolder {
        TextView p1name, p2name, p1score, p2score;
    }

}
