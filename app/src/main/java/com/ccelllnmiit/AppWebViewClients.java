package com.ccelllnmiit;

import android.app.ProgressDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AppWebViewClients extends WebViewClient {
    private ProgressDialog progressBar;

    public AppWebViewClients(ProgressDialog progressBar) {
        this.progressBar=progressBar;
        progressBar.show();
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        progressBar.dismiss();
    }
}
