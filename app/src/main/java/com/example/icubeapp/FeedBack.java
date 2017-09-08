package com.example.icubeapp;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icubeapp.adapters.FeedAdapter;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.example.icubeapp.model.Language;
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
   // ImageView logout;
    TextView billno,netamount;
    public boolean done = false;
    ArrayList<Language> languages;
    ArrayList<String> language;
    Spinner spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        spinner=(Spinner)findViewById(R.id.spinner);

        languages=new ArrayList<>();
        language=new ArrayList<>();
        languages.add(new Language("1","English"));
        languages.add(new Language("2","العَرَبِيَّة"));
        languages.add(new Language("3","தமிழ்"));//
        languages.add(new Language("4","हिन्दी"));

        language.add("English");
        language.add("العَرَبِيَّة");
        language.add("தமிழ்");
        language.add("हिन्दी");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(FeedBack.this,android.R.layout.simple_list_item_1,language);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                global.languageId=languages.get(position).id;
                SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                editor.putString("language", global.languageId);
                editor.commit();
                // Toast.makeText(getApplicationContext(),global.languageId,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
        String langid = preferences.getString("language", "");

        for (int i=0;i<languages.size();i++)
        {
            if(langid.equalsIgnoreCase(languages.get(i).id))
            {
                spinner.setSelection(i);
            }
        }

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

                //String response=new WSUtils().getResultFromHttpRequest("http://icube.cloudapp.net:8080/iCubeIOS/api/Feedback/GetspSelectFeedback?type=GetFeedbackMasterList&ExtraString1=1&ExtraString2=1234&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10","GET",new HashMap<String, String>());
                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // dialog.dismiss();
                submit.setVisibility(View.GONE);
                global.feedback = new ArrayList<>();
                global.feedbackdata=new ArrayList<>();

                if (s == null) {
                    //  Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
                    // responseFromServer();

                    if(handler!=null)
                    {
                        handler.removeCallbacksAndMessages(null);
                    }
                    Intent i = new Intent(FeedBack.this, Splash.class);
                    startActivity(i);
                    finish();

                } else {
                    try {


                        billno.setText(global.pos.memo_no);
                        netamount.setText(global.pos.netamount);

                        JSONArray array = new JSONArray(s);

                        if (array.length() == 0) {
                            submit.setVisibility(View.GONE);

                            if(handler!=null)
                            {
                                handler.removeCallbacksAndMessages(null);
                            }
                            Intent i = new Intent(FeedBack.this, Splash.class);
                            startActivity(i);
                            finish();
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
                                String IsMandatory = object.getString("IsMandatory");
                                FEEDBACK feedback = new FEEDBACK(ID, GroupID, LanguageID, Question, RatingType, OutOf, status, "",IsMandatory);
                                global.feedback.add(feedback);
                            }

                            FeedAdapter adapter = new FeedAdapter(FeedBack.this, global.feedback);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FeedBack.this);
                            list.setLayoutManager(mLayoutManager);
                            list.setItemAnimator(new DefaultItemAnimator());
                            list.setAdapter(adapter);


                            submit.setVisibility(View.VISIBLE);

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
               // dialog.dismiss();
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


                            if(handler!=null)
                            {
                                handler.removeCallbacksAndMessages(null);
                            }


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
                    getPOSId();
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
     //   logout = (ImageView) findViewById(R.id.log_out);
        billno=(TextView) findViewById(R.id.billno);
        netamount=(TextView) findViewById(R.id.amount);


        submit.setVisibility(View.GONE);
        if (codeSnippet.hasNetworkConnection()) {


           if(handler!=null)
           {
               handler.removeCallbacksAndMessages(null);
           }

           global.feedback=new ArrayList<>();
            global.feedbackdata=new ArrayList<>();
            billno.setText("");
            netamount.setText("");

            FeedAdapter adapter = new FeedAdapter(FeedBack.this, global.feedback);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FeedBack.this);
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(adapter);



            handler=new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){

                   getPOSId();
                    handler.postDelayed(this, 15000);

                }
            }, 1000);



        } else {
            Snackbar();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               /* if(containsProduct(global.feedbackdata,"true"))
                {
                    Toast.makeText(getApplicationContext(),"Please select",Toast.LENGTH_SHORT).show();
                }else
                {

                }*/


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


       /* logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });*/

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

            if(dialog!=null)
            {
                dialog.dismiss();
            }

            if(handler!=null)
            {
                handler.removeCallbacksAndMessages(null);
            }

            done=true;
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
               // dialog.dismiss();
                global.pos=new POS();
                global.feedback=new ArrayList<>();
                global.feedbackdata=new ArrayList<>();



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
                         /*   if(!done) {
                                getPOSId();
                            }*/

                            if(handler!=null)
                            {
                                handler.removeCallbacksAndMessages(null);
                            }

                            Intent i = new Intent(FeedBack.this, Splash.class);
                            startActivity(i);
                            finish();
                        } else {

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ID = object.getString("ID");
                                String POSID = object.getString("POSID");
                                String MemoNo = object.getString("MemoNo");
                                String NetAmount = object.getString("NetAmount");

                                global.pos = new POS(ID, POSID,MemoNo,NetAmount);

                            }


                           /* if(handler!=null)
                            {
                                handler.removeCallbacksAndMessages(null);
                            }
*/
                            responseFromServer();

                        }



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

        final EditText password = (EditText) dialogs.findViewById(R.id.password);
        ImageView close = (ImageView) dialogs.findViewById(R.id.iv_close);
        Button logouts = (Button) dialogs.findViewById(R.id.btn_logout);


        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
                String user = preferences.getString("user", "");
                String pass = preferences.getString("pass", "");

                if (password.getText().toString().equalsIgnoreCase(pass)) {

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

    @Override
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


   /* @Override
    protected void onStop() {
        onStop();
        PackageManager localPackageManager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        String str = localPackageManager.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;
        Log.e("Current launcher Package Name:",str);
    }*/

    boolean containsProduct(List<FeedBackSelection> list, String selection) {
        for (FeedBackSelection item : list) {
            if (item.ismandatory.contains(selection)) {

                return true;

            }
            break;
        }

        return false;
    }
}