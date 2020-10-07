package com.praxello.tailorsmart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praxello.tailorsmart.utility.AvenuesParams;
import com.praxello.tailorsmart.utility.Constants;
import com.praxello.tailorsmart.utility.LoadingDialog;
import com.praxello.tailorsmart.utility.RSAUtility;
import com.praxello.tailorsmart.utility.ServiceUtility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CCAvenueWebViewActivity extends AppCompatActivity {
    Intent mainIntent;
    String encVal;
    String vResponse;
    Context mContext;
    App app;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ccavenue_webview);
        mContext = CCAvenueWebViewActivity.this;
        app = (App) getApplication();
        mainIntent = getIntent();
        //get rsa key method
        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
    }

    private class RenderView extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            LoadingDialog.showLoadingDialog(CCAvenueWebViewActivity.this, "Loading...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... arg0) {
            if (!ServiceUtility.chkNull(vResponse).equals("")
                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                StringBuffer vEncVal = new StringBuffer();
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            LoadingDialog.cancelLoading();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface {
                @JavascriptInterface
                public void processHTML(String html) {
                    // process the html source code to get final status of transaction
                    Log.e("html", html);
                    String status = null;
                    if (html.indexOf("Failure") != -1) {
                        status = "Transaction Declined!";
                    } else if (html.indexOf("Success") != -1) {
                        status = "Transaction Successful!";
                    } else if (html.indexOf("Aborted") != -1) {
                        status = "Transaction Cancelled!";
                    } else {
                        status = "Status Not Known!";
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
//                    intent.putExtra("transStatus", status);
//                    startActivity(intent);
                    Intent intent = getIntent();
                    intent.putExtra("status", status);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            final WebView webview = findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(webview, url);
                    LoadingDialog.cancelLoading();
//                    Log.e("in", "onPageFinished:" + url);
                    if (url.indexOf("/ccavResponseHandler.php") != -1) {
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                        String str = "javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');";
                        Log.e("str", str);
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LoadingDialog.showLoadingDialog(CCAvenueWebViewActivity.this, "Loading...");
                }
            });

            try {
                String postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8") + "&" +
                        AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8") + "&" +
                        AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8") + "&" +
                        AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8") + "&" +
                        AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8") + "&" +
                        AvenuesParams.BILLING_COUNTRY + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_COUNTRY), "UTF-8") + "&" +
                        AvenuesParams.BILLING_STATE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_STATE), "UTF-8") + "&" +
                        AvenuesParams.BILLING_CITY + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_CITY), "UTF-8") + "&" +
                        AvenuesParams.BILLING_ZIP + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_ZIP), "UTF-8") + "&" +
                        AvenuesParams.BILLING_NAME + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_NAME), "UTF-8") + "&" +
                        AvenuesParams.BILLING_ADDRESS + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_ADDRESS), "UTF-8") + "&" +
                        AvenuesParams.BILLING_TEL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_TEL), "UTF-8") + "&" +
                        AvenuesParams.BILLING_EMAIL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_EMAIL), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_NAME + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_NAME), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_ADDRESS + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_ADDRESS), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_CITY + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_CITY), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_STATE + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_STATE), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_ZIP + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_ZIP), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_COUNTRY + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_COUNTRY), "UTF-8") + "&" +
                        AvenuesParams.DELIVERY_TEL + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.DELIVERY_TEL), "UTF-8") + "&" +
                        AvenuesParams.BILLING_NOTES + "=" + URLEncoder.encode(mainIntent.getStringExtra(AvenuesParams.BILLING_NOTES), "UTF-8") + "&" +
                        AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8");
                webview.postUrl(Constants.TRANS_URL, postData.getBytes());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void get_RSA_key(final String ac, final String od) {
        LoadingDialog.showLoadingDialog(CCAvenueWebViewActivity.this, "Loading...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL), response -> {
            //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
            LoadingDialog.cancelLoading();
            if (response != null && !response.equals("")) {
                vResponse = response;     ///save retrived rsa key
//                Log.e("response", response);
                if (vResponse.contains("!ERROR!")) {
                    show_alert(vResponse);
                } else {
                    new RenderView().execute();   // Calling async task to get display content
                }
            } else {
                show_alert("No response");
            }
        }, error -> {
            LoadingDialog.cancelLoading();
            //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(AvenuesParams.ACCESS_CODE, ac);
                params.put(AvenuesParams.ORDER_ID, od);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void show_alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(CCAvenueWebViewActivity.this).create();
        alertDialog.setTitle("Error!!!");
        if (msg.contains("\n"))
            msg = msg.replaceAll("\\\n", "");
        alertDialog.setMessage(msg);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", (dialog, which) -> finish());
        alertDialog.show();
    }
}