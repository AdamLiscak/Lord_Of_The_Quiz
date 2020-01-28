package com.example.quiz_lord.activities.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz_lord.gamelogic.Quiz;
import com.example.quiz_lord.gamelogic.structs.ScoreStruct;
import com.example.quiz_lord.R;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.ScoreHolder>
{
    private int page = 0;
    private Quiz quiz;
    private ScoreStruct[] highScores;
    public ScoreBoardAdapter(Quiz quiz)
    {
        try
        {
            this.quiz= quiz;
            quiz.pullHighScores();
            this.highScores = quiz.getHighScores();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            highScores = new ScoreStruct[0];
        }
    }
    public class ScoreHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ScoreHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public int getPage()
    {
        return page;
    }

    public void nextPage()
    {
        page++;
        try
        {
            quiz.pullHighScores();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_score_board, parent, false);
        ScoreHolder vh = new ScoreHolder(v);
        return vh;
    }
    @Override
    public int getItemCount()
    {
        return highScores.length;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position)
    {
        ScoreStruct scoreStruct=highScores[position];
        int score = scoreStruct.getScore();
        String user = scoreStruct.getUser().getName();
        TextView rank = holder.cardView.findViewById(R.id.scoreboard_rank_textView);
        TextView userView = holder.cardView.findViewById(R.id.scoreboard_user_name_textView);
        TextView scoreView = holder.cardView.findViewById(R.id.scoreboard_score_textView);
        rank.setText((position+1)+".");
        userView.setText(user);
        scoreView.setText(""+score);
    }
}