package com.ccelllnmiit;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ccelllnmiit.utils.InternetConnection;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mess.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mess extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Mess() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bus.
     */
    // TODO: Rename and change types and number of parameters
    public static Mess newInstance(String param1, String param2) {
        Mess fragment = new Mess();
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

    PhotoView mess_view;
    WebView web;
    //User usr;

    ProgressDialog progressDialog;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_mess, container, false);

        mess_view=v.findViewById(R.id.mess_view);
        web=v.findViewById(R.id.web);

        if(InternetConnection.checkConnection(getContext()))
        {
            web.setVisibility(View.VISIBLE);
            mess_view.setVisibility(View.GONE);

            web.getSettings().setJavaScriptEnabled(true);

            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();

            web.setWebViewClient(new WebViewClient(){
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.i("mess", "Processing webview url click...");
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    Log.i("mess", "Finished loading URL: " +url);
                    if (progressDialog!=null)
                            progressDialog.dismiss();
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Log.e("mess", "Error: " + description);
                    Toast.makeText(getContext(), "Oops! Please try again" + description, Toast.LENGTH_SHORT).show();
                }
            });

            web.loadUrl("https://drive.google.com/file/d/1PXhnVexTB7kCIk72UhcsmyaxrNnZhgwT/view");
        // Garvitsir drive link https://drive.google.com/file/d/1DXTARUg0hXf4I9kvUbUnZp57Q-p_ubtU/view
        }
        else
        {
            web.setVisibility(View.GONE);
            mess_view.setVisibility(View.VISIBLE);
            Snackbar.make(getActivity().findViewById(android.R.id.content), "You are offline!", Snackbar.LENGTH_LONG).show();
        }
       /*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
       ImageLoader.getInstance().init(config);
       ImageLoader img=ImageLoader.getInstance();
       img.displayImage("https://garvitbhatia.000webhostapp.com/mess_menu.png", mess_view, new ImageLoadingListener() {
           @Override
           public void onLoadingStarted(String imageUri, View view) {
               mess_view.setImageResource(R.mipmap.mess_menu);
           }

           @Override
           public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
               mess_view.setImageResource(R.mipmap.mess_menu);
           }

           @Override
           public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

           }

           @Override
           public void onLoadingCancelled(String imageUri, View view) {
               mess_view.setImageResource(R.mipmap.mess_menu);
           }
       });*/


        return v;
    }




    /*@Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

    }*/



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
