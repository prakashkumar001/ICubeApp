package com.example.icubeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.icubeapp.R;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Creative IT Works on 03-Aug-17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>  {



        ArrayList<FEEDBACK> data;
        ImageLoader loader;
        Context context;
        GlobalClass global;
    boolean status;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        LinearLayout smileRating;
        LinearLayout ratingstar;
        ImageView smileone, smiletwo, smilethree, smilefour, smilefive;
        ImageView starone, startwo, starthree, starfour;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            smileRating = (LinearLayout) view.findViewById(R.id.smile);
            ratingstar = (LinearLayout) view.findViewById(R.id.star);

            //smiley
            smileone = (ImageView) view.findViewById(R.id.smileone);
            smiletwo = (ImageView) view.findViewById(R.id.smiletwo);
            smilethree = (ImageView) view.findViewById(R.id.smilethree);
            smilefour = (ImageView) view.findViewById(R.id.smilefour);
            smilefive = (ImageView) view.findViewById(R.id.smilefive);

            //star

            starone = (ImageView) view.findViewById(R.id.starone);
            startwo = (ImageView) view.findViewById(R.id.startwo);
            starthree = (ImageView) view.findViewById(R.id.starthree);
            starfour = (ImageView) view.findViewById(R.id.starfour);

       /* starone.setOnClickListener(this);
        startwo.setOnClickListener(this);
        starthree.setOnClickListener(this);
        starfour.setOnClickListener(this);
        smileone.setOnClickListener(this);
        smiletwo.setOnClickListener(this);
        smilethree.setOnClickListener(this);
        smilefour.setOnClickListener(this);
        smilefive.setOnClickListener(this);*/


        }


    }

    public FeedAdapter(Context context, ArrayList<FEEDBACK> data) {

        this.data=data;
        this.context=context;
        global=new GlobalClass();


    }

    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);




        return new FeedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeedAdapter.MyViewHolder holder, final int position) {

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

        holder.smileone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isSelect(data.get(position).focus))
                {
                    data.get(position).focus=false;
                    holder.smileone.setImageResource(R.drawable.terrible_unselect);
                }else {
                    data.get(position).focus=true;
                    holder.smileone.setImageResource(R.drawable.terrible_select);

                }
                notifyDataSetChanged();


                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });
        holder.smiletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=true;
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_select);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=true;
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_select);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=true;

                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_select);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=true;
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_select);
            }
        });

        holder.starone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.star_unselect);
                holder.starthree.setImageResource(R.drawable.star_unselect);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.startwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.start_select);
                holder.starthree.setImageResource(R.drawable.star_unselect);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.starthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.start_select);
                holder.starthree.setImageResource(R.drawable.start_select);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.starfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.start_select);
                holder.starthree.setImageResource(R.drawable.start_select);
                holder.starfour.setImageResource(R.drawable.start_select);
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


    public boolean isSelect(boolean status)
    {
        if(status==true)
        {
            return false;
        }else {
            return true;
        }

    }

}