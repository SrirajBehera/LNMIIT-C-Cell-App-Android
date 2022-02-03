package com.ccelllnmiit;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Help.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Help#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Help extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean flag=false;

    private OnFragmentInteractionListener mListener;

    public Help() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Help.
     */
    // TODO: Rename and change types and number of parameters
    public static Help newInstance(String param1, String param2) {
        Help fragment = new Help();
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

    User us;
    ProgressDialog progressDialog;
    Button send;
    EditText msg;
    TextView textView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_help, container, false);

        us=new User(getActivity().getApplicationContext());

        msg=v.findViewById(R.id.msg);
        send=v.findViewById(R.id.send);
        textView3=v.findViewById(R.id.textView3);


        if(MainActivity.isBug)
        {
            textView3.setText("Please mention the Bug here. Try to explain it in detail!");
            send.setText("Send Bug Report");
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg.getText().toString().trim().isEmpty())
                {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    msg.startAnimation(shake);
                }
                else
                {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Submitting...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    send_query(msg.getText().toString());
                }
            }
        });

        return v;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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



    private void send_query(final String mesg) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbzRgcsdSOK5x29TpO4_EqiSadf6CY9WE8LLULGjKm-YlTQoMXIJ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        if(MainActivity.isBug)
                        {
                            builder.setMessage("Bug Report Sent. Developers will start working on it ASAP.");
                        }
                        else
                        {
                            builder.setMessage("Your complaint has been received. We will get back to you shortly.");
                        }
                        builder.setTitle("Success").setCancelable(true);
                        builder.create();
                        builder.show();
                        msg.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getContext(),"Oops! Please try again later",Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pp = new HashMap<>();
                pp.put("name",us.getUserDetails().get("name")+" ("+us.getUserDetails().get("email")+")" );
                if(MainActivity.isBug)
                    pp.put("country","Bug: "+mesg.trim());
                else
                    pp.put("country","Help: "+mesg.trim());
                return pp;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
