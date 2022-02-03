package com.ccelllnmiit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ccelllnmiit.adapter.*;
import com.ccelllnmiit.model.MyDataModel;
import com.ccelllnmiit.parser.JSONparser;
import com.ccelllnmiit.utils.InternetConnection;
import com.ccelllnmiit.utils.Keys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ccelllnmiit.R.layout.login;

public class login_activity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private static int sto = 5000;

    SignInButton Signin;
    GoogleApiClient googleApiClient;
    private static final int REQ_CODE=9001;
    User usr;

    ProgressDialog progressDialog;

    login_activity.GetDataTask task;

    boolean dbl = false;

    Handler handler;

    @Override
    public void onBackPressed() {
        if (dbl)
            this.finishAffinity();
        else {
            dbl = true;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dbl = false;
                }
            }, 2000);
        }
    }

    private ArrayList<String> list,listb;
    private badapter badapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler=new Handler();
        Signin = findViewById(R.id.bn_login);
        Log.d("newtag","login se aaya hu");
        usr=new User(getApplicationContext());

        TextView textView = (TextView) Signin.getChildAt(0);
        textView.setText("Sign In with Google");

        if(usr.isLoggedIn())
        {
            Signin.setVisibility(View.INVISIBLE);
            list=new ArrayList<String>();
            listb=new ArrayList<String>();
            if (InternetConnection.checkConnection(login_activity.this)) {
                if(task!=null)
                    task.cancel(true);
                task=new login_activity.GetDataTask();
                task.execute();
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        task.cancel(true);
                        Intent i = new Intent(login_activity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, sto);
            }
            else
            {
                //Snackbar.make(login_activity.this.findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(task!=null)
                        task.cancel(true);
                        Intent i = new Intent(login_activity.this, MainActivity.class);
                        Log.d("newtag","login=>\n"+usr.getDetSet().toString());
                        startActivity(i);
                        finish();
                    }
                }, sto);
            }


        }
        else
        {
            Signin.setOnClickListener(this);

            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            googleApiClient =new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(progressDialog!=null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressDialog.dismiss();
        Log.i("newtag","login ka onResume");
        if(usr.isLoggedIn())
        {
            Signin.setVisibility(View.INVISIBLE);
            list=new ArrayList<String>();
            listb=new ArrayList<String>();
            if (InternetConnection.checkConnection(login_activity.this)) {
                if(task!=null)
                    task.cancel(true);
                task=new login_activity.GetDataTask();
                task.execute();
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        task.cancel(true);
                        Log.d("debug","khali");
                        Intent i = new Intent(login_activity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, sto);
            }
            else
            {
                //Snackbar.make(login_activity.this.findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(task!=null)
                        task.cancel(true);
                        Intent i = new Intent(login_activity.this, MainActivity.class);
                        Log.d("newtag","login=>\n"+usr.getDetSet().toString());
                        startActivity(i);
                        finish();
                    }
                }, sto);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mm","onStop");
        if(task!=null)
        task.cancel(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("mm","Could Not Connect");
    }


    private void handleresult(GoogleSignInResult result)
    {
        boolean gaya=false;
        if(result!=null && result.isSuccess())
        {
            Log.d("mm","Success");
            FirebaseMessaging.getInstance().subscribeToTopic("all");
            GoogleSignInAccount account= result.getSignInAccount();
            if(account!=null)
            {
                String name = account.getDisplayName();
                if(name!=null)
                {
                    boolean space = true;
                    StringBuilder builder = new StringBuilder(name);
                    if(builder!=null)
                    {
                        final int len = builder.length();

                        for (int i = 0; i < len; ++i) {
                            char c = builder.charAt(i);
                            if (space) {
                                if (!Character.isWhitespace(c)) {
                                    // Convert to title case and switch out of whitespace mode.
                                    builder.setCharAt(i, Character.toTitleCase(c));
                                    space = false;
                                }
                            } else if (Character.isWhitespace(c)) {
                                space = true;
                            } else {
                                builder.setCharAt(i, Character.toLowerCase(c));
                            }
                        }
                        name = builder.toString();
                        String email = account.getEmail();
                        String img_url = "";

                        if (account.getPhotoUrl() != null)
                            img_url = account.getPhotoUrl().toString();
                        else
                            img_url = "kuch nahi hai";
                        usr.createLoginSession(name, email, img_url);

                        gaya=true;
                        if (InternetConnection.checkConnection(login_activity.this)) {
                            proceed();
                        } else {
                            //Snackbar.make(login_activity.this.findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                            usr.update_prof(new ArrayList<String>());
                            usr.update_bul(new ArrayList<String>());
                            Intent i = new Intent(this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }

            }
            if(progressDialog!=null && !gaya)
            progressDialog.dismiss();
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(this,"Oops! Please try again later",Toast.LENGTH_LONG).show();
        }
    }

    void proceed()
    {
        list=new ArrayList<String>();
        listb=new ArrayList<String>();
        if(task!=null)
            task.cancel(true);
        task=new login_activity.GetDataTask();
        task.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            Log.d("mm","Activity");
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            progressDialog = new ProgressDialog(login_activity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            handleresult(result);
        }
    }


    @Override
    public void onClick(View v) {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = JSONparser.getDataFromWeb();


            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        int lenArray = array.length();
                        list.clear();
                        if(lenArray > 0) {
                            for( jIndex=lenArray-1; jIndex>=0; jIndex--) {

                                MyDataModel model = new MyDataModel();

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String country = innerObject.getString(Keys.KEY_COUNTRY);

                                model.setName(name);
                                model.setCountry(country);

                                list.add(name+"Φ"+country);

                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONparser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> newl=usr.getDetSet();
            for(String x:list)
            {
                if(newl.contains(x))
                {
                    listb.add(usr.getBulSet().get(newl.indexOf(x)));
                }
                else
                {
                    listb.add("true");
                }
            }
            usr.update_prof(list);
            usr.update_bul(listb);
            if(progressDialog!=null)
            progressDialog.dismiss();
            handler.removeCallbacksAndMessages(null);

            Intent i=new Intent(login_activity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}
