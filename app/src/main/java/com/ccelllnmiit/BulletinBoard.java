package com.ccelllnmiit;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.ccelllnmiit.User;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccelllnmiit.adapter.badapter;
import com.ccelllnmiit.model.MyDataModel;
import com.ccelllnmiit.parser.JSONparser;
import com.ccelllnmiit.utils.InternetConnection;
import com.ccelllnmiit.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BulletinBoard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BulletinBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BulletinBoard extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BulletinBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BB.
     */
    // TODO: Rename and change types and number of parameters
    public static BulletinBoard newInstance(String param1, String param2) {
        BulletinBoard fragment = new BulletinBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;
    private ArrayList<String> list;

    private badapter badapter;

    SwipeRefreshLayout srl;
    User us;
    ImageView no_net;
    boolean done=false;
    static boolean isUpdate=false;

    TextView mark;

    BulletinBoard.GetDataTask task;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.bulletin_board, container, false);

        recyclerView = v.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        srl=v.findViewById(R.id.parentLayout);
        no_net=v.findViewById(R.id.no_net);
        mark=getActivity().findViewById(R.id.mark);

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listb = new ArrayList<String>();
                ArrayList<String> bul=us.getBulSet();
                for(String x:bul)
                {
                    listb.add("false");
                }
                us.update_bul(listb);
                putUI();
            }
        });

        srl.setOnRefreshListener(this);

        Log.d("newtag","bulletin board se aaya hu");
        us=new User(getActivity().getApplicationContext());
        Log.d("newtag","Bulletin=>\n"+us.getDetSet().toString());

        list = new ArrayList<String>();


        badapter = new badapter(getContext(),new ArrayList<MyDataModel>());
        recyclerView.setAdapter(badapter);

        if(!InternetConnection.checkConnection(getContext()))
        {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Swipe down to refresh", Snackbar.LENGTH_LONG).show();
        }


        if(us.getDetSet().isEmpty())
        {
            no_net.setVisibility(View.VISIBLE);
        }
        else
        {
            putUI();
            done=true;
            Log.d("debuging","onCreate");
        }

        return v;
    }

    @Override
    public void onRefresh() {
        if (InternetConnection.checkConnection(getActivity())) {
            task=new BulletinBoard.GetDataTask();
            task.execute();
        } else {
            srl.setRefreshing(false);
            if(no_net.getVisibility()!=View.VISIBLE)
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(task!=null)
            task.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        done=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.isBB=true;
        mark.setVisibility(View.VISIBLE);
        Log.d("debuging","onResume");
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getContext().getSystemService(ns);
        nMgr.cancel(0);
        if(done==false && isUpdate)
        {
            putUI();
            isUpdate=false;
        }
    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            srl.setRefreshing(true);
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
                               // Log.d("mm",name+"\n"+country);
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
            srl.setRefreshing(false);

            if(list.size() > 0) {
                badapter.notifyDataSetChanged();
                if(us.getDetSet().equals(list))
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "No New Update Found", Snackbar.LENGTH_LONG).show();
                putUI();
            } else {
                badapter.notifyDataSetChanged();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void putUI()
    {
        ArrayList<String> hs=us.getDetSet();

        if(!list.isEmpty()) {
            no_net.setVisibility(View.GONE);
            ArrayList<String> newl = us.getDetSet();
            ArrayList<String> listb = new ArrayList<String>();
            for (String x : list) {
                if (newl.contains(x)) {
                    listb.add(us.getBulSet().get(newl.indexOf(x)));
                } else {
                    listb.add("true");
                }
            }
            us.update_prof(list);
            us.update_bul(listb);
            hs=list;
        }

        ArrayList<MyDataModel> mydata=new ArrayList<MyDataModel>();
        for(String x:hs)
        {
            Log.d("newtag","\n"+x+"\n");
            String got[]=x.split("Φ");
            MyDataModel model = new MyDataModel();
            model.setName(got[0]);
            model.setCountry(got[1]);
            if(us.getBulSet().get(hs.indexOf(x)).equals("true"))
                model.setN(true);
            mydata.add(model);
        }
        badapter = new badapter(getContext(),mydata);
        recyclerView.setAdapter(badapter);
    }
}
