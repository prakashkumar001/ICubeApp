package com.example.icubeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.icubeapp.R;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Prakash on 7/23/2017.
 */

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.MyViewHolder> {



    ArrayList<FEEDBACK> data;
    ImageLoader loader;
    Context context;
    GlobalClass global;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        SmileRating smileRating;
        RatingBar ratingstar;





        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            smileRating = (SmileRating) view.findViewById(R.id.smile_rating);
            ratingstar= (RatingBar) view.findViewById(R.id.ratingstar);



        }
    }


    public FeedBackAdapter(Context context, ArrayList<FEEDBACK> data) {

        this.data=data;
        this.context=context;
        global=new GlobalClass();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_item, parent, false);




        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       // holder.title.setText(data.get(position).title);

        holder.question.setText(data.get(position).question);

        if(data.get(position).rating_type.equalsIgnoreCase("1"))
        {
            holder.smileRating.setVisibility(View.VISIBLE);
            holder.ratingstar.setVisibility(View.GONE);

        }else {
            holder.ratingstar.setVisibility(View.VISIBLE);
            holder.smileRating.setVisibility(View.GONE);

        }
       // holder.smileRating.setVisibility(View.VISIBLE);

        holder.smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
            }
        });

        holder.smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                Log.i("RATINGSMILE","RATINGSMILE"+level);
                global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(level),""));


            }
        });

        holder.ratingstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {



            public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {


                Log.i("RATINGSTAR","RATINGSTAR"+rating);
                //ratingValue.setText(String.valueOf(rating));
                global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(rating),""));




            }

        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}