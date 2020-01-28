package com.example.findyourprivategrandpa.Activities.Adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.Models.Structs.ScoreStruct;
import com.example.findyourprivategrandpa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class QuizBuilderAdapter extends RecyclerView.Adapter<QuizBuilderAdapter.QuestionHolder>
{
    private int page = 0;
    private JSONArray jsonQuestions;
    private  Application application;
    public QuizBuilderAdapter(JSONArray jsonQuestions, Application application)
    {
         this.jsonQuestions= jsonQuestions;
         this.application = application;
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
                .inflate(R.layout.card_view_quiz_builder, parent, false);
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
            ConstraintLayout constraintLayout = holder.cardView.findViewById(R.id.card_view_quiz_builder);
            constraintLayout.setTag(position);
            ImageView imageView = holder.cardView.findViewById(R.id.quiz_builder_question_image_view);
            String uri;
            try
            {
                Picasso.with(application).load(new File(jsonQuestions.getJSONObject(position).getString("picture"))).into(imageView);
                TextView textView = holder.cardView.findViewById(R.id.quiz_builder_question_name);
                textView.setText(jsonQuestion.getString("name"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}