package com.ymsfd.practices.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

import com.ymsfd.practices.R;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/14/15
 * Time: 09:52
 */
public class JSActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.javascript_activity);
        enableToolbarUp(true);

        final WebView webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/index.html");
        Button button = findViewById(R.id.submit);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                webview.loadUrl("javascript:updateHtml()");
            }
        });

        button = findViewById(R.id.button1);
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
