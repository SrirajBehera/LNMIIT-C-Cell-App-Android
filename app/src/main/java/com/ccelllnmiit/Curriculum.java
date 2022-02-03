package com.ccelllnmiit;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ccelllnmiit.utils.InternetConnection;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Curriculum.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Curriculum#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Curriculum extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Curriculum() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Curriculum.
     */
    // TODO: Rename and change types and number of parameters
    public static Curriculum newInstance(String param1, String param2) {
        Curriculum fragment = new Curriculum();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_curriculum, container, false);



        final String cce="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17OnwardCCE.pdf";
        final String cse="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17OnwardCSE.pdf";
        final String ece="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17OnwardECE.pdf";
        final String me="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17OnwardME.pdf";
        final String dcse="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17Onward5YearIntegratedB.Tech.-M.Tech.DualDegreeCSE.pdf";
        final String dece="https://www.lnmiit.ac.in/uploaded_files/CurriculumY17Onward5YearIntegratedB.Tech.-M.Tech.DualDegreeECE.pdf";
        Button b1 = (Button)view.findViewById(R.id.cce);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(getActivity(), "Downloading CCE Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(cce));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });


        Button b2 = (Button)view.findViewById(R.id.cse);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Downloading CSE Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(cse));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });

        Button b3 = (Button)view.findViewById(R.id.ece);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Downloading ECE Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ece));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });

        Button b4 = (Button)view.findViewById(R.id.me);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Downloading ME Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(me));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });

        Button b5 = (Button)view.findViewById(R.id.dcse);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Downloading D-CSE Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(dcse));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });

        Button b6 = (Button)view.findViewById(R.id.dece);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Downloading D-ECE Curriculum", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(dece));
                    request.allowScanningByMediaScanner();
                    request.setDescription("Downloading...");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });

        return view;
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
}
