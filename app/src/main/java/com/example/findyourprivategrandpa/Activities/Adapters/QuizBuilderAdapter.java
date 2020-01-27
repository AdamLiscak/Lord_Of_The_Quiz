package com.example.findyourprivategrandpa.Activities.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.Models.Structs.ScoreStruct;
import com.example.findyourprivategrandpa.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizBuilderAdapter extends RecyclerView.Adapter<QuizBuilderAdapter.QuestionHolder>
{
    private int page = 0;
    private JSONArray jsonQuestions;
    public QuizBuilderAdapter(JSONArray jsonQuestions)
    {
         this.jsonQuestions= jsonQuestions;
    }
    public class QuestionHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public QuestionHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_score_board, parent, false);
        QuestionHolder vh = new QuestionHolder(v);
        return vh;
    }
    @Override
    public int getItemCount()
    {
        return jsonQuestions.length();
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position)
    {
        try
        {
            JSONObject jsonQuestion = jsonQuestions.getJSONObject(position);
            ImageView imageView = holder.cardView.findViewById(R.id.quiz_builder_question_image_view);
            TextView textView = holder.cardView.findViewById(R.id.quiz_result_quiz_finished_textview);
            textView.setText(jsonQuestion.getString("name"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}