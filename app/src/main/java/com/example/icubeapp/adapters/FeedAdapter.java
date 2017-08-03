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
    int index=-1;


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

                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smileone.setImageResource(R.drawable.terrible_select);

                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(1),""));
                    }else
                    {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(1),""));

                    }



                }else
                {
                    data.get(position).status=0;
                    holder.smileone.setImageResource(R.drawable.terrible_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }


                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);


            }
        });
        holder.smiletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smiletwo.setImageResource(R.drawable.bad_select);

                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(2),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), ""));

                    }
                }else
                {
                    data.get(position).status=0;
                    holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smilethree.setImageResource(R.drawable.okay_select);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(3),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), ""));
                    }

                }else
                {
                    data.get(position).status=0;
                    holder.smilethree.setImageResource(R.drawable.okay_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {

                    data.get(position).status=1;
                    holder.smilefour.setImageResource(R.drawable.good_select);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(4),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), ""));
                    }

                }else
                {
                    data.get(position).status=0;
                    holder.smilefour.setImageResource(R.drawable.good_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefive.setImageResource(R.drawable.great_unselect);
            }
        });

        holder.smilefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smilefive.setImageResource(R.drawable.great_select);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(5),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), ""));
                    }
                }else
                {
                    data.get(position).status=0;
                    holder.smilefive.setImageResource(R.drawable.great_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }                holder.smileone.setImageResource(R.drawable.terrible_unselect);
                holder.smiletwo.setImageResource(R.drawable.bad_unselect);
                holder.smilethree.setImageResource(R.drawable.okay_unselect);
                holder.smilefour.setImageResource(R.drawable.good_unselect);
            }
        });

        holder.starone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.starone.setImageResource(R.drawable.start_select);

                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(1),""));
                    }else
                    {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(1),""));
                    }


                }else
                {
                    data.get(position).status=0;
                    holder.starone.setImageResource(R.drawable.star_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
               // holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.star_unselect);
                holder.starthree.setImageResource(R.drawable.star_unselect);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.startwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(containsData(global.feedbackdata,data.get(position).id))
                {
                    global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(2),""));
                }else
                {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(2),""));

                }

                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.start_select);
                holder.starthree.setImageResource(R.drawable.star_unselect);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.starthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(containsData(global.feedbackdata,data.get(position).id))
                {
                    global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(3),""));
                }else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), ""));
                }
                holder.starone.setImageResource(R.drawable.start_select);
                holder.startwo.setImageResource(R.drawable.start_select);
                holder.starthree.setImageResource(R.drawable.start_select);
                holder.starfour.setImageResource(R.drawable.star_unselect);
            }
        });
        holder.starfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(containsData(global.feedbackdata,data.get(position).id))
                {
                    global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(4),""));
                }else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), ""));

                }
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


    public boolean containsData(ArrayList<FeedBackSelection> list,String id)
    {
        for(FeedBackSelection feedback:list)
        {
            if(feedback.id.equals(id))
            {
                 index=list.indexOf(feedback);
                return  true;
            }


        }
        return false;
    }


}