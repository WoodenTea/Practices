package com.ymsfd.practices.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/14/15
 * Time: 09:52
 */
public class JSActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.javascript_activity);
        setUpActionBar(true);

        final WebView webview = (WebView) findViewById(R.id.webview);
        Preconditions.checkNotNull(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/index.html");
        Button button = (Button) findViewById(R.id.submit);
        Preconditions.checkNotNull(button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                webview.loadUrl("javascript:updateHtml()");
            }
        });

        button = (Button) findViewById(R.id.button1);
        Preconditions.checkNotNull(button);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                webview.loadUrl("file:///android_asset/index.html");
                // return
            }
        });

        webview.addJavascriptInterface(new JSOperation(), "login");

        return true;
    }

    class JSOperation {
        @JavascriptInterface
        public void startFunction() {
            AlertDialog.Builder ab = new AlertDialog.Builder(JSActivity.this);
            ab.setTitle("提示");
            ab.setMessage("通JS调用了java中的方法");
            ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ab.create().show();
        }
    }
}
