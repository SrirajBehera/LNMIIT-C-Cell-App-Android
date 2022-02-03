package com.ccelllnmiit;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutCCell.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutCCell#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutCCell extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AboutCCell() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutCCell.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutCCell newInstance(String param1, String param2) {
        AboutCCell fragment = new AboutCCell();
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

    TextView abt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*abt=v.findViewById(R.id.abt);

        abt.setText("LNMIIT Counseling & Guidance Cell (C Cell) is a body that functions with the objective of " +
                "facilitating the fresh batch make a smooth and healthy transition from ‘new students’ to " +
                "LNMIITians, sensitize them with the LNMIIT ethos, help provide answers to their queries " +
                "ranging from academic to personal and social ones. It works towards helping them" +
                " adjust to the new campus as a home away from home. It undertakes a variety of roles" +
                " and responsibilities ranging from organizing the Orientation Programme for the new" +
                " batch, providing support in the admission process, document verification & reporting," +
                " conducting the Student–Faculty Mentorship Programme and related activities during the academic year.");*/

        simulateDayNight(/* DAY */ 0);
//            Element adsElement = new Element();
//            adsElement.setTitle("Advertise with us");

        return new AboutPage(getContext())
                .isRTL(false)

                //.addItem(new Element().setTitle("Version 6.2"))
                .setImage(R.mipmap.splash_logo)

                .addGroup("Connect with us")
                .addEmail("c-cell@lnmiit.ac.in")
                .setDescription("LNMIIT Counseling & Guidance Cell (C Cell) is a body that functions with the objective of facilitating the fresh batch make a smooth and healthy transition from ‘new students’ to LNMIITians, sensitize them with the LNMIIT ethos, help provide answers to their queries ranging from academic to personal and social ones. It works towards helping them adjust to the new campus as a home away from home. It undertakes a variety of roles and responsibilities ranging from organizing the Orientation Programme for the new batch, providing support in the admission process, document verification & reporting, conducting the Student–Faculty Mentorship Programme and related activities during the academic year.")
                .addPlayStore("com.ccelllnmiit")

                .create();


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

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.about_youtube), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_email);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.START);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
