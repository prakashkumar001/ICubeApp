package com.example.icubeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.icubeapp.R;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.database.Smiley;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.icubeapp.helper.Helper.getHelper;

/**
 * Created by Creative IT Works on 03-Aug-17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {


    ArrayList<FEEDBACK> data;
    ImageLoader loader;
    Context context;
    GlobalClass global;
    boolean status;
    int index = -1;


    public FeedAdapter(Context context, ArrayList<FEEDBACK> data) {

        this.data = data;
        this.context = context;
        global = new GlobalClass();
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

        if (data.get(position).rating_type.equalsIgnoreCase("1")) {

            holder.smileRating.setVisibility(View.GONE);
            holder.ratingstar.setVisibility(View.VISIBLE);
            holder.commentbox.setVisibility(View.GONE);
            global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, "0", data.get(position).comment, data.get(position).IsMandatory,data.get(position).status_select));


        } else if (data.get(position).rating_type.equalsIgnoreCase("2")) {
            holder.ratingstar.setVisibility(View.GONE);
            holder.smileRating.setVisibility(View.VISIBLE);
            holder.commentbox.setVisibility(View.GONE);


            global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, "0", data.get(position).comment,data.get(position).IsMandatory,data.get(position).status_select));

        } else {
            holder.ratingstar.setVisibility(View.GONE);
            holder.smileRating.setVisibility(View.GONE);
            holder.commentbox.setVisibility(View.VISIBLE);

            global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, "0", data.get(position).comment,data.get(position).IsMandatory,data.get(position).status_select));

        }


        if (data.get(position).outof.equalsIgnoreCase("5.0") && data.get(position).rating_type.equalsIgnoreCase("1")) {

            holder.starone.setVisibility(View.VISIBLE);
            holder.startwo.setVisibility(View.VISIBLE);
            holder.starthree.setVisibility(View.VISIBLE);
            holder.starfour.setVisibility(View.VISIBLE);

            holder.starone.setImageBitmap(decodebeforeImage("1","1"));
            holder.startwo.setImageBitmap(decodebeforeImage("1","2"));
            holder.starthree.setImageBitmap(decodebeforeImage("1","3"));
            holder.starfour.setImageBitmap(decodebeforeImage("1","4"));
           // holder.starfive.setVisibility(View.VISIBLE);
        } else if (data.get(position).outof.equalsIgnoreCase("4.0")&& data.get(position).rating_type.equalsIgnoreCase("1")) {
            holder.starone.setVisibility(View.VISIBLE);
            holder.startwo.setVisibility(View.VISIBLE);
            holder.starthree.setVisibility(View.VISIBLE);
            holder.starfour.setVisibility(View.VISIBLE);
           // holder.starfive.setVisibility(View.GONE);


            holder.starone.setImageBitmap(decodebeforeImage("1","1"));
            holder.startwo.setImageBitmap(decodebeforeImage("1","2"));
            holder.starthree.setImageBitmap(decodebeforeImage("1","3"));
            holder.starfour.setImageBitmap(decodebeforeImage("1","4"));



        } else if (data.get(position).outof.equalsIgnoreCase("3.0")&& data.get(position).rating_type.equalsIgnoreCase("1")) {
            holder.starone.setVisibility(View.VISIBLE);
            holder.startwo.setVisibility(View.VISIBLE);
            holder.starthree.setVisibility(View.VISIBLE);
            holder.starfour.setVisibility(View.GONE);
           // holder.starfive.setVisibility(View.GONE);

            holder.starone.setImageBitmap(decodebeforeImage("1","1"));
            holder.startwo.setImageBitmap(decodebeforeImage("1","2"));
            holder.starthree.setImageBitmap(decodebeforeImage("1","3"));
           // holder.starfour.setImageBitmap(decodebeforeImage("1","4"));




        }else if (data.get(position).outof.equalsIgnoreCase("2.0")&& data.get(position).rating_type.equalsIgnoreCase("1")) {
            holder.starone.setVisibility(View.VISIBLE);
            holder.startwo.setVisibility(View.VISIBLE);
            holder.starthree.setVisibility(View.GONE);
            holder.starfour.setVisibility(View.GONE);
           // holder.starfive.setVisibility(View.GONE);


            holder.starone.setImageBitmap(decodebeforeImage("1","1"));
            holder.startwo.setImageBitmap(decodebeforeImage("1","2"));
           // holder.starthree.setImageBitmap(decodebeforeImage("1","3"));
           // holder.starfour.setImageBitmap(decodebeforeImage("1","4"));

        }





        if (data.get(position).outof.equalsIgnoreCase("5.0") && data.get(position).rating_type.equalsIgnoreCase("2")) {

            holder.smile_one_lay.setVisibility(View.VISIBLE);
            holder.smile_two_lay.setVisibility(View.VISIBLE);
            holder.smile_three_lay.setVisibility(View.VISIBLE);
            holder.smile_four_lay.setVisibility(View.VISIBLE);
            holder.smile_five_lay.setVisibility(View.VISIBLE);

            holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
            holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
            holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
            holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
            holder.smilefive.setImageBitmap(decodebeforeImage("2","4"));
        } else if (data.get(position).outof.equalsIgnoreCase("4.0")&& data.get(position).rating_type.equalsIgnoreCase("2")) {
            holder.smile_one_lay.setVisibility(View.VISIBLE);
            holder.smile_two_lay.setVisibility(View.VISIBLE);
            holder.smile_three_lay.setVisibility(View.VISIBLE);
            holder.smile_four_lay.setVisibility(View.VISIBLE);
            holder.smile_five_lay.setVisibility(View.GONE);

            holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
            holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
            holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
            holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
            //holder.smilefive.setImageBitmap(decodebeforeImage("2","1"));

        } else if (data.get(position).outof.equalsIgnoreCase("3.0")&& data.get(position).rating_type.equalsIgnoreCase("2")) {
            holder.smile_one_lay.setVisibility(View.VISIBLE);
            holder.smile_two_lay.setVisibility(View.VISIBLE);
            holder.smile_three_lay.setVisibility(View.VISIBLE);
            holder.smile_four_lay.setVisibility(View.GONE);
            holder.smile_five_lay.setVisibility(View.GONE);


            holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
            holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
            holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
           // holder.smilefour.setImageBitmap(decodebeforeImage("2","1"));
           // holder.smilefive.setImageBitmap(decodebeforeImage("2","1"));


        }else if (data.get(position).outof.equalsIgnoreCase("2.0")&& data.get(position).rating_type.equalsIgnoreCase("2")) {
            holder.smile_one_lay.setVisibility(View.VISIBLE);
            holder.smile_two_lay.setVisibility(View.VISIBLE);
            holder.smile_three_lay.setVisibility(View.GONE);
            holder.smile_four_lay.setVisibility(View.GONE);
            holder.smile_five_lay.setVisibility(View.GONE);
            holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
            holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));

            // holder.smiletwo.setImageBitmap(decodebeforeImage("2","1"));
            //holder.smilethree.setImageBitmap(decodebeforeImage("2","1"));
        }

        holder.layer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideKeyboard(v);
                return false;
            }
        });

        holder.comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                String comment = s.toString();
                if (containsData(global.feedbackdata, data.get(position).id)) {
                    global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, "0", comment,data.get(position).IsMandatory,"selected"));
                } else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, "0", comment,data.get(position).IsMandatory,"selected"));

                }
            }
        });


        holder.smileone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int heights = holder.smileone.getHeight();
                int widths = holder.smileone.getWidth();
                if (data.get(position).status.get(0) == 0) {
                    data.get(position).status.set(0,1);
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);

                    resize(holder.smileone, heights, widths);
                   // holder.smileone.setImageResource(R.mipmap.terrible_select);
                    holder.smileone.setImageBitmap(decodeAfterImage("2","1"));
                    holder.terrible.setTextColor(Color.BLACK);

                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(1), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(1), "",data.get(position).IsMandatory,"selected"));

                    }


                } else {
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);

                    resize(holder.smileone, heights, widths);
                    holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
                    //holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                    holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));

                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }




               /* holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);
*/

                holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
                holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
                holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
                holder.smilefive.setImageBitmap(decodebeforeImage("2","5"));


                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));

                resize(holder.smiletwo, 90, 90);
                resize(holder.smilethree, 90, 90);
                resize(holder.smilefour, 90, 90);
                resize(holder.smilefive, 90, 90);


            }
        });
        holder.smiletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.get(position).status.get(1) == 0) {
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(1,1);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);

                   // holder.smiletwo.setImageResource(R.mipmap.bad_select);
                    holder.smiletwo.setImageBitmap(decodeAfterImage("2","2"));
                    resize(holder.smiletwo, 90, 90);

                    holder.bad.setTextColor(Color.BLACK);
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), "",data.get(position).IsMandatory,"selected"));

                    }
                } else {
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);
                    resize(holder.smiletwo, 90, 90);
                    //holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                    holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
                    holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }
              /*  holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);
*/


                holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
                holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
                holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
                holder.smilefive.setImageBitmap(decodebeforeImage("2","5"));


                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));


                resize(holder.smileone, 90, 90);
                resize(holder.smilethree, 90, 90);
                resize(holder.smilefour, 90, 90);
                resize(holder.smilefive, 90, 90);

            }
        });

        holder.smilethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).status.get(2) == 0) {
                    data.get(position).status.set(2,1);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);
                    //holder.smilethree.setImageResource(R.mipmap.okay_select);
                    holder.smilethree.setImageBitmap(decodeAfterImage("2","3"));
                    resize(holder.smilethree, 90, 90);

                    holder.okay.setTextColor(Color.BLACK);
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), "",data.get(position).IsMandatory,"selected"));
                    }

                } else {
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);
                    resize(holder.smilethree, 90, 90);
                   // holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                    holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
                    holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }
               /* holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);
*/

                holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
                holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
                holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
                holder.smilefive.setImageBitmap(decodebeforeImage("2","5"));


                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));


                resize(holder.smileone, 90, 90);
                resize(holder.smiletwo, 90, 90);
                resize(holder.smilefour, 90, 90);
                resize(holder.smilefive, 90, 90);

            }
        });

        holder.smilefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).status.get(3) == 0) {

                    data.get(position).status.set(3,1);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(4,0);
                    //holder.smilefour.setImageResource(R.mipmap.good_select);
                    holder.smilefour.setImageBitmap(decodeAfterImage("2","4"));

                    resize(holder.smilefour, 90, 90);

                    holder.good.setTextColor(Color.BLACK);
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), "",data.get(position).IsMandatory,"selected"));
                    }

                } else {
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);
                  /*  holder.smilefour.requestLayout();
                    holder.smilefour.getLayoutParams().height = 90;
                    holder.smilefour.getLayoutParams().width = 90;*/
                    resize(holder.smilefour, 90, 90);
                    //holder.smilefour.setImageResource(R.mipmap.good_unselect);
                    holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));
                    holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }
             /*   holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefive.setImageResource(R.mipmap.great_unselect);
*/

                holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
                holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
                holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
                holder.smilefive.setImageBitmap(decodebeforeImage("2","5"));


                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));
                holder.great.setTextColor(Color.parseColor("#D5D8DA"));


                resize(holder.smileone, 90, 90);
                resize(holder.smiletwo, 90, 90);
                resize(holder.smilethree, 90, 90);
                resize(holder.smilefive, 90, 90);
            }
        });

        holder.smilefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).status.get(4) == 0) {
                    data.get(position).status.set(4,1);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(3,0);

                   // holder.smilefive.setImageResource(R.mipmap.great_select);
                    holder.smilefive.setImageBitmap(decodeAfterImage("2","5"));
                    resize(holder.smilefive, 90, 90);

                    holder.great.setTextColor(Color.BLACK);

                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), "",data.get(position).IsMandatory,"selected"));
                    }
                } else {
                    data.get(position).status.set(1,0);
                    data.get(position).status.set(0,0);
                    data.get(position).status.set(2,0);
                    data.get(position).status.set(3,0);
                    data.get(position).status.set(4,0);
                    resize(holder.smilefive, 90, 90);
                   // holder.smilefive.setImageResource(R.mipmap.great_unselect);
                    holder.smilefive.setImageBitmap(decodebeforeImage("2","5"));
                    holder.great.setTextColor(Color.parseColor("#D5D8DA"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }
               /* holder.smileone.setImageResource(R.mipmap.terrible_unselect);
                holder.smiletwo.setImageResource(R.mipmap.bad_unselect);
                holder.smilethree.setImageResource(R.mipmap.ok_unselect);
                holder.smilefour.setImageResource(R.mipmap.good_unselect);
*/

                holder.smileone.setImageBitmap(decodebeforeImage("2","1"));
                holder.smilethree.setImageBitmap(decodebeforeImage("2","3"));
                holder.smiletwo.setImageBitmap(decodebeforeImage("2","2"));
                holder.smilefour.setImageBitmap(decodebeforeImage("2","4"));

                holder.bad.setTextColor(Color.parseColor("#D5D8DA"));
                holder.okay.setTextColor(Color.parseColor("#D5D8DA"));
                holder.good.setTextColor(Color.parseColor("#D5D8DA"));
                holder.terrible.setTextColor(Color.parseColor("#D5D8DA"));


                resize(holder.smileone, 90, 90);
                resize(holder.smiletwo, 90, 90);
                resize(holder.smilethree, 90, 90);
                resize(holder.smilefour, 90, 90);
            }
        });

        holder.starone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.get(position).status.get(0) == 0) {
                    data.get(position).status.set(0,1);
                    //holder.starone.setImageResource(R.mipmap.star_select);
                    holder.starone.setImageBitmap(decodeAfterImage("1","1"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(1), "",data.get(position).IsMandatory,"selected"));
                    } else {
                        global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(1), "",data.get(position).IsMandatory,"selected"));
                    }


                } else {
                    data.get(position).status.set(0,0);
                  //  holder.starone.setImageResource(R.mipmap.star_unselect);
                    holder.starone.setImageBitmap(decodebeforeImage("1","1"));
                    if (containsData(global.feedbackdata, data.get(position).id)) {
                        global.feedbackdata.remove(index);
                    }
                }
                // holder.starone.setImageResource(R.mipmap.start_select);
              /*  holder.startwo.setImageResource(R.mipmap.star_unselect);
                holder.starthree.setImageResource(R.mipmap.star_unselect);
                holder.starfour.setImageResource(R.mipmap.star_unselect);
             */   //holder.starfive.setImageResource(R.mipmap.star_unselect);

                holder.startwo.setImageBitmap(decodebeforeImage("1","1"));
                holder.starthree.setImageBitmap(decodebeforeImage("1","1"));
                holder.starfour.setImageBitmap(decodebeforeImage("1","1"));


            }
        });
        holder.startwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (containsData(global.feedbackdata, data.get(position).id)) {
                    global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), "",data.get(position).IsMandatory,"selected"));
                } else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(2), "",data.get(position).IsMandatory,"selected"));

                }

                //holder.starone.setImageResource(R.mipmap.star_select);
                //holder.startwo.setImageResource(R.mipmap.star_select);


                holder.starone.setImageBitmap(decodeAfterImage("1","1"));
                holder.startwo.setImageBitmap(decodeAfterImage("1","1"));

                holder.starthree.setImageBitmap(decodebeforeImage("1","1"));
                holder.starfour.setImageBitmap(decodebeforeImage("1","1"));
               // holder.starfive.setImageResource(R.mipmap.star_unselect);

            }
        });
        holder.starthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (containsData(global.feedbackdata, data.get(position).id)) {
                    global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), "",data.get(position).IsMandatory,"selected"));
                } else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(3), "",data.get(position).IsMandatory,"selected"));
                }
              /*  holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_unselect);
          */     // holder.starfive.setImageResource(R.mipmap.star_unselect);


                holder.starone.setImageBitmap(decodeAfterImage("1","1"));
                holder.startwo.setImageBitmap(decodeAfterImage("1","1"));

                holder.starthree.setImageBitmap(decodeAfterImage("1","1"));
                holder.starfour.setImageBitmap(decodebeforeImage("1","1"));


            }
        });
        holder.starfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (containsData(global.feedbackdata, data.get(position).id)) {
                    global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), "",data.get(position).IsMandatory,"selected"));
                } else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(4), "",data.get(position).IsMandatory,"selected"));

                }
            /*    holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_select);
            */    //holder.starfive.setImageResource(R.mipmap.star_unselect);


                holder.starone.setImageBitmap(decodeAfterImage("1","1"));
                holder.startwo.setImageBitmap(decodeAfterImage("1","1"));

                holder.starthree.setImageBitmap(decodeAfterImage("1","1"));
                holder.starfour.setImageBitmap(decodeAfterImage("1","1"));


            }
        });

       /* holder.starfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (containsData(global.feedbackdata, data.get(position).id)) {
                    global.feedbackdata.set(index, new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), "",data.get(position).IsMandatory,"selected"));
                } else {
                    global.feedbackdata.add(new FeedBackSelection(data.get(position).id, data.get(position).group_id, data.get(position).language_id, data.get(position).question, data.get(position).rating_type, data.get(position).outof, String.valueOf(5), "",data.get(position).IsMandatory,"selected"));

                }
                holder.starone.setImageResource(R.mipmap.star_select);
                holder.startwo.setImageResource(R.mipmap.star_select);
                holder.starthree.setImageResource(R.mipmap.star_select);
                holder.starfour.setImageResource(R.mipmap.star_select);
                holder.starfive.setImageResource(R.mipmap.star_select);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public boolean containsData(ArrayList<FeedBackSelection> list, String id) {
        for (FeedBackSelection feedback : list) {
            if (feedback.id.equals(id)) {
                index = list.indexOf(feedback);
                return true;
            }


        }
        return false;
    }

    public void resize(ImageView imageView, int height, int width) {
        imageView.requestLayout();
        imageView.getLayoutParams().height = imageView.getHeight();
        imageView.getLayoutParams().width = imageView.getWidth();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        LinearLayout smileRating;
        LinearLayout ratingstar;
        LinearLayout commentbox;
        ImageView smileone, smiletwo, smilethree, smilefour, smilefive;
        TextView terrible, bad, okay, good, great;
        TextView comments;
        LinearLayout smile_one_lay,smile_two_lay,smile_three_lay,smile_four_lay,smile_five_lay;
        LinearLayout layer;

        ImageView starone, startwo, starthree, starfour;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            smileRating = (LinearLayout) view.findViewById(R.id.smile);
            ratingstar = (LinearLayout) view.findViewById(R.id.star);
            commentbox = (LinearLayout) view.findViewById(R.id.commentbox);
            comments = (TextView) view.findViewById(R.id.comments);

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
           // starfive = (ImageView) view.findViewById(R.id.starfive);



            smile_one_lay = (LinearLayout) view.findViewById(R.id.smile_one_lay);
            smile_two_lay = (LinearLayout) view.findViewById(R.id.smile_two_lay);
            smile_three_lay = (LinearLayout) view.findViewById(R.id.smile_three_lay);
            smile_four_lay = (LinearLayout) view.findViewById(R.id.smile_four_lay);
            smile_five_lay = (LinearLayout) view.findViewById(R.id.smile_five_lay);
            layer = (LinearLayout) view.findViewById(R.id.layer);

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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public Bitmap decodebeforeImage(String FBrateId,String ratingCount)
    {
        List<Smiley> data=getHelper().getEncodeImage(FBrateId,ratingCount);
        String encodedImage=data.get(0).getBeforeImage();
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
    public Bitmap decodeAfterImage(String FBrateId,String ratingCount)
    {
        
        List<Smiley> data=getHelper().getEncodeImage(FBrateId,ratingCount);
        String encodedImage=data.get(0).getAfterImage();
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

}