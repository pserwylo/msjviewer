package com.serwylo.msjviewer;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class StylishWebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        view.loadUrl(url);
        return true;

    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        for ( String stylesheetUrl : getStylesheetUrls( url ) ) {
            view.loadUrl("javascript:" + addCssToDom( stylesheetUrl ) );
        }

    }

    protected abstract String[] getStylesheetUrls( String webpageUrl );

    private String addCssToDom( String stylesheetUrl ) {

        return
            "var headNode = document.getElementsByTagName(\"head\")[0];" +
            "var cssNode = document.createElement('link');" +
            "cssNode.type = 'text/css';" +
            "cssNode.rel = 'stylesheet';" +
            "cssNode.href = '" + stylesheetUrl + "';" +
            "cssNode.media = 'screen';" +
            "headNode.appendChild(cssNode);";

    }

}
