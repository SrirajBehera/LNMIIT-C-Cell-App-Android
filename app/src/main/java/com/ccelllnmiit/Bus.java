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
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ccelllnmiit.utils.InternetConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Bus() {
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
    public static Bus newInstance(String param1, String param2) {
        Bus fragment = new Bus();
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

//    Button download;
//
//    ScrollView scroll;
//    WebView web;
//
//    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_bus, container, false);

//        scroll=v.findViewById(R.id.scroll);
//        web=v.findViewById(R.id.web);
//
//        if(InternetConnection.checkConnection(getContext()))
//        {
//            web.setVisibility(View.VISIBLE);
//            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//            scroll.setVisibility(View.GONE);
//
//            web.getSettings().setJavaScriptEnabled(true);
//
//            progressDialog=new ProgressDialog(getContext());
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//
//            progressDialog.show();
//
//            web.setWebViewClient(new WebViewClient(){
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    Log.i("nus", "Processing webview url click...");
//                    view.loadUrl(url);
//                    return true;
//                }
//
//                public void onPageFinished(WebView view, String url) {
//                    Log.i("bus", "Finished loading URL: " +url);
//                    if (progressDialog!=null)
//                        progressDialog.dismiss();
//                }
//
//                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                    if (progressDialog!=null)
//                        progressDialog.dismiss();
//                    Log.e("bus", "Error: " + description);
//                    Toast.makeText(getContext(), "Oops! Please try again" + description, Toast.LENGTH_SHORT).show();
//                }
//            });

//            web.loadUrl("https://drive.google.com/file/d/1fZpLa3F8PXiRvAW_dnpTAbKKBqwtcsBm/view");
//            web.loadUrl();
            //garvit sir drive link https://drive.google.com/file/d/1bolP0GBnAGPVaZpQanfJs1QXpmOx9UMr/view

//        }
//        else
//        {
//            web.setVisibility(View.GONE);
//            scroll.setVisibility(View.VISIBLE);
//            Snackbar.make(getActivity().findViewById(android.R.id.content), "You are offline!", Snackbar.LENGTH_LONG).show();
//        }
        /*download=v.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        showExplanation("Permission Needed", "Storage permission is necessary", Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
                    } else {
                        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
                    }
                } else {

                    startDnld();
                }

            }
        });*/

        return v;
    }


//        private void copyFile(InputStream in, OutputStream out) throws IOException {
//            byte[] buffer = new byte[1024];
//            int read;
//            while((read = in.read(buffer)) != -1){
//                out.write(buffer, 0, read);
//            }
//        }

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

    /*private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);

                    }
                });
        builder.create().show();
    }


    void startDnld()
    {
        Toast.makeText(getActivity(), "Downloading Bus Time Table", Toast.LENGTH_SHORT).show();

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        AssetManager assetManager = getContext().getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("bus.pdf");
            File outFile = new File(dir, "bus.pdf");
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);

            downloadManager.addCompletedDownload(outFile.getName(), "Time Table", true, "application/pdf", outFile.getAbsolutePath(), outFile.length(), true);
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + "bus.pdf", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
    }


    private void requestPermission(String permissionName, int permissionRequestCode) {
        requestPermissions( new String[]{permissionName}, permissionRequestCode);
        Log.d("mm","De di");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("mm","Result aaya");

        switch(requestCode)
        {
            case 1:

                if(shouldShowRequestPermissionRationale( permissions[0]))
                {
                    showExplanation("Permission Needed", "Storage permission is necessary",
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,1);
                }
                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startDnld();
                }
                else
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error").setCancelable(false)
                            .setMessage("You have denied the permission forever. To download the Bus Time Table Allow STORAGE permission from settings.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                }
                            });
                    builder.create().show();
                }
                break;
        }
    }*/
}
