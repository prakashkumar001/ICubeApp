package com.example.icubeapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.icubeapp.adapters.FeedBackAdapter;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.POS;
import com.example.icubeapp.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Prakash on 7/23/2017.
 */

public class FeedBack extends AppCompatActivity {
    GlobalClass global;
    RecyclerView list;
    Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        global = (GlobalClass) getApplicationContext();
        list = (RecyclerView) findViewById(R.id.recyclerlist);
        submit = (Button) findViewById(R.id.submit);
        responseFromServer();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadToServer();
            }
        });
    }

    public void responseFromServer() {
        class ResultfromServer extends AsyncTask<String, Void, String> {

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(FeedBack.this);
                dialog.setMessage("Loading....");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                String response = new WSUtils().getResultFromHttpRequest("http://icube.cloudapp.net:8080/iCubeIOS/api/Feedback/GetspSelectFeedback?type=GetFeedbackMasterList&ExtraString1=1&ExtraString2=&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10", "GET", new HashMap<String, String>());

                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();

                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String ID = object.getString("ID");
                        String GroupID = object.getString("GroupID");
                        String LanguageID = object.getString("LanguageID");
                        String Question = object.getString("Question");
                        String RatingType = object.getString("RatingType");
                        String OutOf = object.getString("OutOf");

                        FEEDBACK feedback = new FEEDBACK(ID, GroupID, LanguageID, Question, RatingType, OutOf);
                        global.feedback.add(feedback);
                    }

                    FeedBackAdapter adapter = new FeedBackAdapter(FeedBack.this, global.feedback);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FeedBack.this);
                    list.setLayoutManager(mLayoutManager);
                    list.setItemAnimator(new DefaultItemAnimator());
                    list.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        new ResultfromServer().execute();
    }


    public void uploadToServer() {
        class UploadtoServer extends AsyncTask<String, Void, String> {

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(FeedBack.this);
                dialog.setMessage("Loading....");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                HashMap<String, String> data = new HashMap<>();
                data.put("POSReqID", global.pos.id);
                data.put("POSID", global.pos.pos_id);


                StringBuffer detid = new StringBuffer();
                StringBuffer fbmid = new StringBuffer();
                ;
                StringBuffer fbvalue = new StringBuffer();
                ;

                StringBuffer fbcomment = new StringBuffer();
                ;


                for (int i = 0; i < global.feedbackdata.size(); i++) {
                    detid.append(global.feedbackdata.get(i).id).append("^");
                    // detid=detid+"^";
                    fbmid.append(global.feedbackdata.get(i).id).append("^");
                    fbvalue.append(global.feedbackdata.get(i).rating).append("^");
                    fbcomment.append(global.feedbackdata.get(i).comment).append("^");


                }

                Log.i("DDDDDDDDd", "DDDDDDDdd" + detid.toString());


                String url = "http://icube.cloudapp.net:8080/iCubeIOS/api/Feedback/spSaveFeedback?POSReqID=1&POSID=1&DetID=0^0&FBMID=" + fbmid.toString() + "&FBValue=" + fbvalue.toString() + "&FBComment=test^test" + "&User=emp0001";

                Log.i("URL", "URL" + url);
                String response = new WSUtils().getResultFromHttpRequest(url, "GET", new HashMap<String, String>());

                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();
                String result = "";
                global.feedback = new ArrayList<>();
                global.feedbackdata = new ArrayList<>();

                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        result = object.getString("RESULT");


                    }

                    if (result.equalsIgnoreCase("Saved")) {
                        dialogShow();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        new UploadtoServer().execute();
    }

    public void dialogShow() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                FeedBack.this);


        // set dialog message
        alertDialogBuilder
                .setMessage("Your Feedback Submitted Sucessfully ")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        FeedBack.this.finish();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
