package com.serwylo.msjviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class FirstRunActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.first_run );

        WebViewClient client = new WebViewClient();
        WebView view = (WebView)findViewById( R.id.web_view );
        view.setWebViewClient( client );
        view.loadUrl( "file:///android_asset/first_run.html" );

        findViewById( R.id.btn_cancel ).setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick( View v ) {
                setResult( Activity.RESULT_CANCELED );
                finish();
            }
        });

        findViewById( R.id.btn_understand ).setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick( View v ) {
                setResult( Activity.RESULT_OK );
                finish();
            }
        });
    }
}
