package com.ccelllnmiit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private Toolbar tb;
    private NavigationView nv;
    private ImageButton open_drwaer;
    TextView frag_title,name,email,mark;
    ImageView img;
    GoogleApiClient googleApiClient;
    View headerView;

    static boolean isFront=false;
    static boolean isBB=false;
    static boolean isBug=false;

    FrameLayout fill_content;

    ProgressDialog progressDialog;

    User us;


    boolean dbl = false;

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(nv)) {
            dl.closeDrawer(nv);
            return;
        }
        if(!isBB)
        {
            Fragment fg = null;
            try {
                fg = (Fragment) BulletinBoard.class.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fill_content, fg);
            tx.commit();
            isBB=true;

            mark.setVisibility(View.VISIBLE);
            int size = nv.getMenu().size();
            for (int i = 0; i < size; i++) {
                nv.getMenu().getItem(i).setChecked(false);
            }

            nv.getMenu().getItem(0).setChecked(true);
            setTitle(nv.getMenu().getItem(0).getTitle());
            frag_title.setText(nv.getMenu().getItem(0).getTitle());
            return;
        }
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

    private BroadcastReceiver mMessageReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("newtag","main activity se aaya hu");
        us=new User(getApplicationContext());
        Log.d("newtag","Main Activity=>\n"+us.getDetSet().toString());


        if(us.getRat()<0.0F && (us.getSes()==5 || us.getSes()==15)) {
            final Dialog dialog= new Dialog(MainActivity.this);

            dialog.setContentView(R.layout.rate_ini);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            RelativeLayout parent_ini=dialog.findViewById(R.id.rate_ini_parent);
            parent_ini.getLayoutParams().width=(int) (displayMetrics.widthPixels*0.8);
            RatingBar rb=dialog.findViewById(R.id.mid);
            TextView ok=dialog.findViewById(R.id.ok);
            rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                    if(rating<2.0F)
                    {
                        dialog.dismiss();
                        final Dialog dialogf= new Dialog(MainActivity.this);

                        dialogf.setContentView(R.layout.rate_feed);
                        dialogf.setCancelable(false);
                        dialogf.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogf.show();

                        ConstraintLayout parent_ini=dialogf.findViewById(R.id.rate_feed_parent);
                        parent_ini.getLayoutParams().width=(int) (displayMetrics.widthPixels*0.9);

                        final EditText feed=dialogf.findViewById(R.id.editTextFeed);
                        TextView cancel,submit;
                        cancel=dialogf.findViewById(R.id.cancel);
                        submit=dialogf.findViewById(R.id.submit);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogf.dismiss();
                            }
                        });
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(feed.getText().toString().trim().isEmpty())
                                {
                                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                                    feed.startAnimation(shake);
                                }
                                else
                                {

                                        dialogf.dismiss();
                                        progressDialog = new ProgressDialog(MainActivity.this);
                                        progressDialog.setMessage("Sending...");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        send_query(feed.getText().toString().trim(),rating);
                                }
                            }
                        });
                    }
                    else
                    {
                        us.setRat(rating);
                        final Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.ccelllnmiit");
                        try {
                            dialog.dismiss();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
                                }
                            }, 500);
                        } catch (ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this, "Couldn't find PlayStore on this device", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if(us.getSes()<20)
            us.incSes();





        isFront=true;

        Log.d("mm","sabse Phunch gaya");
        dl=findViewById(R.id.drawer_layout);
        tb=findViewById(R.id.tool);

        Log.d("mm","upar Phunch gaya");
        frag_title = findViewById(R.id.frag_title);
        mark = findViewById(R.id.mark);
        nv=findViewById(R.id.navigation);
        open_drwaer=findViewById(R.id.open_drawer);
        Log.d("mm","Phunch gaya");
        fill_content=findViewById(R.id.fill_content);

        headerView=nv.getHeaderView(0);
        name=headerView.findViewById(R.id.full_name);
        email=headerView.findViewById(R.id.email);
        img=headerView.findViewById(R.id.profile_image);

        HashMap<String,String> details=us.getUserDetails();
        name.setText(details.get("name"));
        email.setText(details.get("email"));
        if(details.get("img").contains("kuch")) {
            Log.d("mm","Khali image");
            img.setImageResource(R.drawable.rsz_c_cell_logo);
        }
        else
            Glide.with(this).load(details.get("img")).into(img);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        open_drwaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dl.isDrawerOpen(nv)) {
                    dl.closeDrawer(nv);
                } else if (!dl.isDrawerOpen(nv)) {
                    dl.openDrawer(nv);

                }
            }
        });

        Fragment fg = null;
        try {
            fg = (Fragment) BulletinBoard.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fill_content, fg);
        tx.commit();
        isBB=true;
        mark.setVisibility(View.VISIBLE);

        nv.getMenu().getItem(0).setChecked(true);
        setTitle(nv.getMenu().getItem(0).getTitle());
        frag_title.setText(nv.getMenu().getItem(0).getTitle());
        setupDrawerContent(nv);

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Extract data included in the Intent
                String message = intent.getStringExtra("message");
                if(isBB) {
                    Fragment fg = null;
                    try {
                        fg = (Fragment) BulletinBoard.class.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.fill_content, fg);
                    tx.commit();
                    isBB = true;

                    mark.setVisibility(View.VISIBLE);
                }

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
        isFront=true;
        if(isBB)
            mark.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mMessageReceiver);
        isFront=false;
    }


    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    public void selectItemDrawer(MenuItem menuItem)
    {
        Fragment frag = null;
        Class fclass = null;
        int flg = 0;
        isBB=false;
        isBug=false;
        mark.setVisibility(View.GONE);
        switch (menuItem.getItemId()) {
            case R.id.bul_board:
                fclass = BulletinBoard.class;
                isBB=true;
                mark.setVisibility(View.VISIBLE);
                break;
            case R.id.imp_contacts:
                fclass = ImportantContact.class;
                break;
            case R.id.imp_links:
                fclass = ImportantLinks.class;
                break;
            case R.id.curriculum:
                fclass = Curriculum.class;
                break;
            case R.id.bus:
                fclass = Bus.class;
                break;
            case R.id.mess:
                fclass = Mess.class;
                break;
            case R.id.holiday:
                fclass = HolidayCalendar.class;
                break;
            case R.id.help:
                fclass = Help.class;
                break;
            case R.id.bug:
                fclass = Help.class;
                isBug=true;
                break;
            case R.id.ccell:
                fclass = AboutCCell.class;
                break;
            case R.id.rateus:
                flg=3;
                break;
            case R.id.faq:
                fclass = FAQ.class;
                break;
            case R.id.dev:
//                if(dl.isDrawerOpen(nv))
//                    dl.closeDrawer(nv);
//                Intent i=new Intent(MainActivity.this,Dev.class);
//                startActivity(i);
//                return;
                fclass = Developer.class;
                break;
            case R.id.logout:
                flg=2;
                break;
            default:
                fclass = BulletinBoard.class;
                break;

        }
        if(flg==2)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you really want to Log Out?").setTitle("Alert").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                    us.logoutUser();
                                }
                            });

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setCancelable(false);
            AlertDialog alert11 = builder.create();
            alert11.setTitle("Log Out");
            alert11.show();
            return;
        }
        if(flg==3)
        {
            if(us.getRat()<0.0F) {
                final Dialog dialog= new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.rate_ini);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                final DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                RelativeLayout parent_ini=dialog.findViewById(R.id.rate_ini_parent);
                parent_ini.getLayoutParams().width=(int) (displayMetrics.widthPixels*0.8);
                RatingBar rb=dialog.findViewById(R.id.mid);
                TextView ok=dialog.findViewById(R.id.ok);
                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                        if(rating<2.0F)
                        {
                            dialog.dismiss();
                            final Dialog dialogf= new Dialog(MainActivity.this);

                            dialogf.setContentView(R.layout.rate_feed);
                            dialogf.setCancelable(false);
                            dialogf.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialogf.show();

                            ConstraintLayout parent_ini=dialogf.findViewById(R.id.rate_feed_parent);
                            parent_ini.getLayoutParams().width=(int) (displayMetrics.widthPixels*0.9);

                            final EditText feed=dialogf.findViewById(R.id.editTextFeed);
                            TextView cancel,submit;
                            cancel=dialogf.findViewById(R.id.cancel);
                            submit=dialogf.findViewById(R.id.submit);
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogf.dismiss();
                                }
                            });
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(feed.getText().toString().trim().isEmpty())
                                    {
                                        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                                        feed.startAnimation(shake);
                                    }
                                    else
                                    {
                                        dialogf.dismiss();
                                        progressDialog = new ProgressDialog(MainActivity.this);
                                        progressDialog.setMessage("Sending...");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        send_query(feed.getText().toString().trim(),rating);
                                    }
                                }
                            });
                        }
                        else
                        {
                            us.setRat(rating);
                            final Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.ccelllnmiit");
                            try {
                                dialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
                                    }
                                }, 500);
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(MainActivity.this, "Couldn't find PlayStore on this device", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
            else
            {
                final Dialog dialog= new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.rate_thank);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                final DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                RelativeLayout parent_ini=dialog.findViewById(R.id.rate_thank_parent);
                parent_ini.getLayoutParams().width=(int) (displayMetrics.widthPixels*0.8);

                TextView star,ok;
                star = dialog.findViewById(R.id.star);
                ok = dialog.findViewById(R.id.ok);
                star.setText(String.valueOf(us.getRat()));

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
            return;
        }
        if(fclass!=null) {
            try {
                frag = (Fragment) fclass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }


            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fill_content, frag).commit();
        }

        int size = nv.getMenu().size();
        for (int i = 0; i < size; i++) {
            nv.getMenu().getItem(i).setChecked(false);
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        frag_title.setText(menuItem.getTitle());

        dl.closeDrawers();
    }

    public void setupDrawerContent(final NavigationView nv) {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                selectItemDrawer(item);
                return false;
            }
        });
    }

    private void send_query(final String mesg,final float rating) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbzRgcsdSOK5x29TpO4_EqiSadf6CY9WE8LLULGjKm-YlTQoMXIJ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        us.setRat(rating);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Thank you for your feedback. We will surely work on it!");
                        builder.setTitle("Success").setCancelable(true);
                        builder.create();
                        builder.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Oops! Please try again later",Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pp = new HashMap<>();
                pp.put("name",us.getUserDetails().get("name")+" ("+us.getUserDetails().get("email")+")" );
                pp.put("country","Feedback: "+mesg.trim());
                return pp;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}
