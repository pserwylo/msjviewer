package com.serwylo.msjviewer;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class StylishWebClient extends WebViewClient {

    private static final String TAG = "com.serwylo.msjviewer.StylishWebClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        view.loadUrl(url);
        return true;

    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        for ( String stylesheetUrl : getStylesheetUrls( url ) ) {
            String javascript = "javascript:(function(){" + addCssToDom( stylesheetUrl ) + "})();";
            Log.d( TAG, "Loading javascript" );
            Log.d( TAG, javascript );
            view.loadUrl( javascript );
        }

    }

    protected abstract String[] getStylesheetUrls( String webpageUrl );

    private String addCssToDom( String stylesheetUrl ) {

        return
            "var headNode = document.getElementsByTagName('head')[0];" +
            "var cssNode = document.createElement('link');" +
            "cssNode.type = 'text/css';" +
            "cssNode.rel = 'stylesheet';" +
            "cssNode.href = '" + stylesheetUrl + "';" +
            "cssNode.media = 'screen';" +
            "headNode.appendChild(cssNode);";

    }

}
