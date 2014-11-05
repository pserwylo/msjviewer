package com.serwylo.msjviewer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        WebView view = (WebView)findViewById( R.id.web_view );
        WebViewClient client = new WebViewClient();
        client.shouldOverrideUrlLoading( view, "" );

        view.setWebViewClient( client );

    }
}
