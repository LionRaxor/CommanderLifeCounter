package com.commanderlifecounter.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.ValueCallback;
import android.content.Intent;
import android.net.Uri;
import java.io.InputStream;
import java.io.IOException;

public class MainActivity extends Activity {
  private WebView webView;
  private ValueCallback<Uri[]> fileCallback;
  private static final int FILE_REQUEST = 111;

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    getWindow().getDecorView().setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
      View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
      View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    webView = new WebView(this);
    setContentView(webView);
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setDatabaseEnabled(true);
    settings.setAllowFileAccess(false);
    settings.setAllowContentAccess(true);
    settings.setMediaPlaybackRequiresUserGesture(false);
    webView.setWebViewClient(new WebViewClient() {
      @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        if (!"appassets.androidplatform.net".equals(uri.getHost())) return null;
        if (!"/index.html".equals(uri.getPath())) return new WebResourceResponse("text/plain", "UTF-8", 404, "Not found", null, new java.io.ByteArrayInputStream(new byte[0]));
        try {
          InputStream stream = getAssets().open("index.html");
          return new WebResourceResponse("text/html", "UTF-8", stream);
        } catch (IOException e) { return null; }
      }
    });
    webView.setWebChromeClient(new WebChromeClient() {
      @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
        if (fileCallback != null) fileCallback.onReceiveValue(null);
        fileCallback = callback;
        try {
          Intent intent = params.createIntent();
          startActivityForResult(intent, FILE_REQUEST);
          return true;
        } catch (Exception ex) {
          fileCallback = null;
          return false;
        }
      }
    });
    webView.loadUrl("https://appassets.androidplatform.net/index.html");
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == FILE_REQUEST && fileCallback != null) {
      fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
      fileCallback = null;
    }
  }

  @Override public void onBackPressed() {
    if (webView != null && webView.canGoBack()) webView.goBack();
    else super.onBackPressed();
  }
  @Override protected void onDestroy() {
    if (webView != null) { webView.destroy(); webView = null; }
    super.onDestroy();
  }
}
