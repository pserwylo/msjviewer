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

        WebViewClient client = new MsjWebClient();

        WebView view = (WebView)findViewById( R.id.web_view );
        view.getSettings().setJavaScriptEnabled( true );
        view.setWebViewClient( client );
        view.loadUrl( "https://ssl.stjohnvic.com.au/msj" );

    }
}
