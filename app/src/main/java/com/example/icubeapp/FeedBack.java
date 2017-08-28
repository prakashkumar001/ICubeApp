package com.example.icubeapp;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icubeapp.adapters.FeedAdapter;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.POS;
import com.example.icubeapp.utils.CodeSnippet;
import com.example.icubeapp.utils.WSUtils;
import com.wroclawstudio.kioskmode.RootKioskActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prakash on 7/23/2017.
 */

public class FeedBack extends AppCompatActivity {
    GlobalClass global;
    RecyclerView list;
    Button submit;
    CodeSnippet codeSnippet;
    JSONArray array;
    String macaddress;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog dialog;
    Dialog dialogs;
    Handler handler;
    Button logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);


    }

    public void responseFromServer() {
        class ResultfromServer extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... strings) {

                //"00:ef:ef:11:13:3d"
                String response = new WSUtils().getResultFromHttpRequest(global.globalurl + "/api/Feedback/GetspSelectFeedback?type=GetFeedbackMasterList&ExtraString1=" + global.languageId + "&ExtraString2=" + getMacAddr() + "&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10", "GET", new HashMap<String, String>());

                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();


                if (s == null) {
                    //  Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
                    // responseFromServer();

                } else {
                    try {


                        JSONArray array = new JSONArray(s);

                        if (array.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Novalue", Toast.LENGTH_SHORT).show();
                        } else {
                            global.feedback = new ArrayList<>();
                            ArrayList<Integer> status = new ArrayList<>();

                            for (int i = 0; i < 5; i++) {
                                status.add(0);
                            }

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ID = object.getString("ID");
                                String GroupID = object.getString("GroupID");
                                String LanguageID = object.getString("LanguageID");
                                String Question = object.getString("Question");
                                String RatingType = object.getString("RatingType");
                                String OutOf = object.getString("OutOf");
                                FEEDBACK feedback = new FEEDBACK(ID, GroupID, LanguageID, Question, RatingType, OutOf, status, "");
                                global.feedback.add(feedback);
                            }

                            FeedAdapter adapter = new FeedAdapter(FeedBack.this, global.feedback);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FeedBack.this);
                            list.setLayoutManager(mLayoutManager);
                            list.setItemAnimator(new DefaultItemAnimator());
                            list.setAdapter(adapter);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
        new ResultfromServer().execute();
    }


    public void uploadToServer() {
        class UploadtoServer extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected String doInBackground(String... strings) {

                HashMap<String, String> data = new HashMap<>();
                data.put("POSReqID", global.pos.id);
                data.put("POSID", global.pos.pos_id);


                String detid = "";
                String fbmid = "";

                String fbvalue = "";


                String fbcomment = "";


                for (int i = 0; i < global.feedbackdata.size(); i++) {


                    detid = detid + "0" + "^";
                    // detid=detid+"^";
                    fbmid = fbmid + (global.feedbackdata.get(i).id) + "^";
                    fbvalue = fbvalue + (global.feedbackdata.get(i).rating) + "^";
                    fbcomment = fbcomment + (global.feedbackdata.get(i).comment) + "^";


                }

               /* ArrayList<String> datas=new ArrayList<>();
                ArrayList<String> values=new ArrayList<>();
                datas.add(detid);
                datas.add(fbmid);
                datas.add(fbvalue);
                datas.add(fbcomment);

                for(int i=0;i<datas.size();i++)
                {
                    String value=removeLastChar(datas.get(i));
                    values.add(value);
                }
*/


                String url = global.globalurl + "/api/Feedback/spSaveFeedback?POSReqID=" + global.pos.id + "&POSID=" + global.pos.pos_id + "&DetID=" + detid + "&FBMID=" + fbmid + "&FBValue=" + fbvalue + "&FBComment=" + fbcomment + "&User=" + global.empId;

                Log.i("RESPONSE", "RESPOSE" + url);

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


                    if (array.length() == 0) {
                        Intent i = new Intent(FeedBack.this, ThankyouPage.class);
                        startActivity(i);
                        finish();
                    } else {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            result = object.getString("RESULT");


                        }

                        if (result.equalsIgnoreCase("Saved")) {

                            Intent i = new Intent(FeedBack.this, ThankyouPage.class);
                            startActivity(i);
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        new UploadtoServer().execute();
    }


    public void Snackbar() {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG);
        View vv = snack.getView();
        TextView textView = (TextView) vv.findViewById(android.support.design.R.id.snackbar_text);
        textView.setGravity(Gravity.CENTER);
        snack.show();
        snack.setAction("Enable", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeSnippet.hasNetworkConnection()) {
                    responseFromServer();
                } else {
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 1);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeSnippet = new CodeSnippet(getApplicationContext());
        global = (GlobalClass) getApplicationContext();
        list = (RecyclerView) findViewById(R.id.recyclerlist);
        submit = (Button) findViewById(R.id.submit);
        logout = (Button) findViewById(R.id.log_out);

        if (codeSnippet.hasNetworkConnection()) {
            dialog = new ProgressDialog(FeedBack.this);
            dialog.setMessage("Loading....");
            dialog.show();
            getPOSId();
        } else {
            Snackbar();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (codeSnippet.hasNetworkConnection()) {
                    dialog = new ProgressDialog(FeedBack.this);
                    dialog.setMessage("Loading....");
                    dialog.show();
                    uploadToServer();

                } else {
                    Snackbar();
                }

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            ActivityCompat.finishAffinity(FeedBack.this);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
    }


    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;  //instead of wlan0 i used eth0
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    public void getPOSId() {
        class ResultfromServer extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                macaddress = getMacAddr();
            }

            @Override
            protected String doInBackground(String... strings) {

                //String url="http://192.168.1.16/imageupload/api.php";
                String url = global.globalurl + "/api/Feedback/GetspSelectFeedback?type=CheckPendingFeedback&ExtraString1=" + getMacAddr() + "&ExtraString2=&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10";
                String response = new WSUtils().getResultFromHttpRequest(url, "GET", new HashMap<String, String>());


                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s == null) {

                    // getPOSId();
                    //Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        array = new JSONArray(s);
                        if (array.length() == 0) {
                            // Toast.makeText(getApplicationContext(),"empty array",Toast.LENGTH_SHORT).show();
                            // dialog.dismiss();
                            // getPOSId();
                            // Toast.makeText(getApplicationContext(),"Please Try again",Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ID = object.getString("ID");
                                String POSID = object.getString("POSID");

                                global.pos = new POS(ID, POSID);
                            }


                           /* if(handler!=null)
                            {
                                handler.removeCallbacksAndMessages(null);
                            }
*/


                        }

                        responseFromServer();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }


        }
        new ResultfromServer().execute();
    }


    void showDialog() {
        dialogs = new Dialog(FeedBack.this, R.style.ThemeDialogCustom);
        dialogs.setContentView(R.layout.show_dialog);
        dialogs.setCancelable(false);

        final EditText username = (EditText) dialogs.findViewById(R.id.user);
        final EditText password = (EditText) dialogs.findViewById(R.id.password);
        ImageView close = (ImageView) dialogs.findViewById(R.id.iv_close);
        Button logouts = (Button) dialogs.findViewById(R.id.btn_logout);


        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
                String user = preferences.getString("user", "");
                String pass = preferences.getString("pass", "");

                if (username.getText().toString().equalsIgnoreCase(user) && password.getText().toString().equalsIgnoreCase("pass")) {

                    SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor.putBoolean("loginstatus", false);
                    editor.putString("EmpID", "");
                    editor.putString("language", "");
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.commit();
                    dialogs.dismiss();
                    startActivity(new Intent(getBaseContext(), Login.class));
                    ActivityCompat.finishAffinity(FeedBack.this);

                } else {
                    Toast.makeText(getApplicationContext(), "Username or Password is Invalid", Toast.LENGTH_SHORT).show();
                }


            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogs.dismiss();
            }
        });


        dialogs.show();
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
//Do Code Here
// If want to block just return false
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
//Do Code Here
// If want to block just return false
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
//Do Code Here
// If want to block just return false
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
*/

}