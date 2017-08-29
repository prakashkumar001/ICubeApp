package com.example.icubeapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.utils.InternetPermissions;
import com.example.icubeapp.utils.WSUtils;
import com.wroclawstudio.kioskmode.RootKioskActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by v-62 on 10/11/2016.
 */

public class Login extends AppCompatActivity {
  //  private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    EditText user,password,hostname;
    Button signin,exit;
    public String names,pass;
    InternetPermissions internetPermissions;
    private static final int NETPERMISSION = 1888;
    GlobalClass global;
    Spinner spinner;

    //Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global=(GlobalClass)getApplicationContext();
        SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
        final boolean loginStatus = preferences.getBoolean("loginstatus", false);
         String empID = preferences.getString("EmpID", "");
        String langid = preferences.getString("language", "");
        if(loginStatus){
            global.empId=empID;
            global.languageId=langid;
            Intent obj = new Intent(getBaseContext(), Splash.class);
            startActivity(obj);
            finish();
        } else {

        }

        setContentView(R.layout.login);
        user=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password);
        hostname=(EditText)findViewById(R.id.hostname);
        spinner=(Spinner)findViewById(R.id.spinner);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        signin=(Button)findViewById(R.id.signup);
        exit=(Button)findViewById(R.id.exit);

        hostname.setText(global.globalurl);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ActivityCompat.finishAffinity(Login.this);
            }
        });

        ArrayList<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Login.this,android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                global.languageId=parent.getSelectedItem().toString();
               // Toast.makeText(getApplicationContext(),global.languageId,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.globalurl=hostname.getText().toString();
                names = user.getText().toString();
                pass = password.getText().toString();



                if(names.length()>0 && pass.length()>0)
                {
                    internetPermissions=new InternetPermissions(Login.this);
                    if(internetPermissions.isInternetOn())
                    {
                        loginService(user.getText().toString(),password.getText().toString(),getMacAddr());
                    }else
                    {

                        Snackbar snack= Snackbar.make(findViewById(android.R.id.content), "No Internet Connection",Snackbar.LENGTH_LONG);
                        View vv=snack.getView();
                        TextView textView=(TextView)vv.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setGravity(Gravity.CENTER);
                        snack.show();
                        snack.setAction("Enable", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(internetPermissions.isInternetOn())
                                {
                                    loginService(user.getText().toString(),password.getText().toString(),getMacAddr());
                                }else
                                {
                                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),NETPERMISSION);
                                }
                            }
                        });


                    }



                }else if(names.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
                {
                    if(names.equalsIgnoreCase("") && pass.equalsIgnoreCase("") )
                    {
                        user.setError("Username is invalid");
                        password.setError("Password is invalid");

                    }else if(pass.equalsIgnoreCase(""))
                    {
                        password.setError("Password is invalid");
                    }else
                    {
                        user.setError("Username is invalid");
                    }
                }


                //Intent i =new Intent(Login.this,MainActivity.class);
                //startActivity(i);
                //finish();
            }
        });

       /* signupfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
                finish();

            }
        });*/
    }



    public void loginService(final String names, final String passwords, final String mac)
    {
        class uploadTOserver extends AsyncTask<String, String, String> {
            ProgressDialog dialog;
            String response="";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(Login.this);
                dialog.setMessage("Loading..");
                dialog.show();
            }
            @Override
            protected String doInBackground(String[] params) {
                try {

                    String charset = "UTF-8";


                    String requestURL = global.globalurl+"/api/Feedback/LoginValidation?UserName="+names+"&Password="+passwords+"&MacAddress="+mac;
                      WSUtils utils=new WSUtils();
                     response= utils.getResultFromHttpRequest(requestURL,"GET",new HashMap<String, String>());

                    System.out.println("SERVER REPLIED:"+response);




                } catch (Exception ex) {
                    System.err.println(ex);
                }
                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                if(o==null)
                {
                    Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();


                }else
                {
                    try {
                        JSONArray array=new JSONArray(o);
                        String result="";
                        String RoleID;
                        String EmpID="";
                        String SessionID="";
                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject object=array.getJSONObject(i);
                             result=object.getString("ResultValue");
                             RoleID=object.getString("RoleID");
                             EmpID=object.getString("EmpID");
                            SessionID =object.getString("SessionID");

                        }

                        if(result.equalsIgnoreCase("Valid User"))
                        {
                            global.empId=EmpID;
                            SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                            editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                            editor.putBoolean("loginstatus", true);
                            editor.putString("EmpID", global.empId);
                            editor.putString("language", global.languageId);
                            editor.putString("user", user.getText().toString());
                            editor.putString("pass", password.getText().toString());
                            editor.commit();


                            Intent i=new Intent(Login.this,Splash.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            finish();

                        }else
                        {
                           // user.setError("Username is incorrect");
                            //password.setError("Password is incorrect");
                           Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                           /* Intent i=new Intent(Login.this,FeedBack.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            finish();
                            Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
*/

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                dialog.dismiss();
            }
        } new uploadTOserver().execute();


    }
    @Override
    public void onBackPressed() {


        }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;  //instead of wlan0 i used eth0
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                return res1.toString();
            }} catch (Exception ex) {
        }return "02:00:00:00:00:00";
    }

}
