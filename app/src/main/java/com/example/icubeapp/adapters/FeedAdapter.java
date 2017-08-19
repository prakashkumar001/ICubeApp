package com.example.icubeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.icubeapp.FeedBack;
import com.example.icubeapp.R;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

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
        LinearLayout commentbox;
        ImageView smileone, smiletwo, smilethree, smilefour, smilefive;
        TextView terrible, bad, okay, good, great;
        TextView comments;

        ImageView starone, startwo, starthree, starfour,starfive;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            smileRating = (LinearLayout) view.findViewById(R.id.smile);
            ratingstar = (LinearLayout) view.findViewById(R.id.star);
             commentbox = (LinearLayout) view.findViewById(R.id.commentbox);
            comments=(TextView)view.findViewById(R.id.comments);

            //smiley
            smileone = (ImageView) view.findViewById(R.id.smileone);
            smiletwo = (ImageView) view.findViewById(R.id.smiletwo);
            smilethree = (ImageView) view.findViewById(R.id.smilethree);
            smilefour = (ImageView) view.findViewById(R.id.smilefour);
            smilefive = (ImageView) view.findViewById(R.id.smilefive);


            //text
            terrible = (TextView) view.findViewById(R.id.terrible);
            bad = (TextView) view.findViewById(R.id.bad);
            okay = (TextView) view.findViewById(R.id.okay);
            good = (TextView) view.findViewById(R.id.good);
            great = (TextView) view.findViewById(R.id.great);


            //star

            starone = (ImageView) view.findViewById(R.id.starone);
            startwo = (ImageView) view.findViewById(R.id.startwo);
            starthree = (ImageView) view.findViewById(R.id.starthree);
            starfour = (ImageView) view.findViewById(R.id.starfour);
            starfive = (ImageView) view.findViewById(R.id.starfive);


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
        global.feedbackdata.clear();


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
            holder.commentbox.setVisibility(View.GONE);
            global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,"0",data.get(position).comment));


        }else if(data.get(position).rating_type.equalsIgnoreCase("2")) {
            holder.ratingstar.setVisibility(View.VISIBLE);
            holder.smileRating.setVisibility(View.GONE);
            holder.commentbox.setVisibility(View.GONE);


            global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,"0",data.get(position).comment));

        }else
        {
            holder.ratingstar.setVisibility(View.GONE);
            holder.smileRating.setVisibility(View.GONE);
            holder.commentbox.setVisibility(View.VISIBLE);


            global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,"0",data.get(position).comment));

        }

        holder.comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                String comment=s.toString();
                if(containsData(global.feedbackdata,data.get(position).id))
                {
                    global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,"0",comment));
                }else
                {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,"0",comment));

                }
            }
        });


        holder.smileone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int heights=  holder.smileone.getHeight();
                int widths=  holder.smileone.getWidth();
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;

                    resize(holder.smileone,heights,widths);
                    holder.smileone.setImageResource(R.mipmap.terrible_select);
                    holder.terrible.setTextColor(Color.BLACK);

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

                    resize(holder.smileone,heights,widths);
                    holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                    holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));

                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }


                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);

                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));

                resize(holder.smiletwo,90,90);
                resize(holder.smilethree,90,90);
                resize(holder.smilefour,90,90);
                resize(holder.smilefive,90,90);



            }
        });
        holder.smiletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smiletwo.setImageResource(R.mipmap.bad_select);
                    resize(holder.smiletwo,90,90);

                    holder.bad.setTextColor(Color.BLACK);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(2),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), ""));

                    }
                }else
                {
                    data.get(position).status=0;
                    resize(holder.smiletwo,90,90);
                    holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                    holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);

                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));



                resize(holder.smileone,90,90);
                resize(holder.smilethree,90,90);
                resize(holder.smilefour,90,90);
                resize(holder.smilefive,90,90);

            }
        });

        holder.smilethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smilethree.setImageResource(R.mipmap.okay_select);
                    resize(holder.smilethree,90,90);

                    holder.okay.setTextColor(Color.BLACK);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(3),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), ""));
                    }

                }else
                {
                    data.get(position).status=0;
                    resize(holder.smilethree,90,90);
                    holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                    holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);

                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));


                resize(holder.smileone,90,90);
                resize(holder.smiletwo,90,90);
                resize(holder.smilefour,90,90);
                resize(holder.smilefive,90,90);

            }
        });

        holder.smilefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {

                    data.get(position).status=1;
                    holder.smilefour.setImageResource(R.mipmap.good_select);
                    resize(holder.smilefour,90,90);

                    holder.good.setTextColor(Color.BLACK);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(4),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), ""));
                    }

                }else
                {
                    data.get(position).status=0;
                  /*  holder.smilefour.requestLayout();
                    holder.smilefour.getLayoutParams().height = 90;
                    holder.smilefour.getLayoutParams().width = 90;*/
                    resize(holder.smilefour,90,90);
                    holder.smilefour.setImageResource(R.mipmap.good_unselect);
                    holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);

                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));



                resize(holder.smileone,90,90);
                resize(holder.smiletwo,90,90);
                resize(holder.smilethree,90,90);
                resize(holder.smilefive,90,90);
            }
        });

        holder.smilefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.smilefive.setImageResource(R.mipmap.great_select);
                    resize(holder.smilefive,90,90);

                    holder.great.setTextColor(Color.BLACK);

                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(5),""));
                    }else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), ""));
                    }
                }else
                {
                    data.get(position).status=0;
                    resize(holder.smilefive,90,90);
                    holder.smilefive.setImageResource(R.mipmap.great_unselect);
                    holder.great.setTextColor(Color.parseColor("#D5D8DA"));
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
                holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);

                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));



                resize(holder.smileone,90,90);
                resize(holder.smiletwo,90,90);
                resize(holder.smilethree,90,90);
                resize(holder.smilefour,90,90);
            }
        });

        holder.starone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.get(position).status==0)
                {
                    data.get(position).status=1;
                    holder.starone.setImageResource(R.mipmap.star_select);

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
                    holder.starone.setImageResource(R.mipmap.star_unselect);
                    if(containsData(global.feedbackdata,data.get(position).id))
                    {
                        global.feedbackdata.remove(index);
                    }
                }
               // holder.starone.setImageResource(R.mipmap.start_select);
                holder.startwo.setImageResource(R.mipmap.star_unselect);
                holder.starthree.setImageResource(R.mipmap.star_unselect);
                holder.starfour.setImageResource(R.mipmap.star_unselect);
                holder.starfive.setImageResource(R.mipmap.star_unselect);

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

                holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_unselect);
                holder.starfour.setImageResource(R.mipmap.star_unselect);
                holder.starfive.setImageResource(R.mipmap.star_unselect);

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
                holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_unselect);
                holder.starfive.setImageResource(R.mipmap.star_unselect);

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
                holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_select);
                holder.starfive.setImageResource(R.mipmap.star_unselect);

            }
        });

        holder.starfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(containsData(global.feedbackdata,data.get(position).id))
                {
                    global.feedbackdata.set(index,new FeedBackSelection(data.get(position).id,data.get(position).group_id,data.get(position).language_id,data.get(position).question,data.get(position).rating_type,data.get(position).outof,String.valueOf(5),""));
                }else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), ""));

                }
                holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_select);
                holder.starfive.setImageResource(R.mipmap.star_select);
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

    public  void resize(ImageView imageView,int height,int width)
    {
        imageView.requestLayout();
        imageView.getLayoutParams().height = imageView.getHeight();
        imageView.getLayoutParams().width = imageView.getWidth();

    }



}