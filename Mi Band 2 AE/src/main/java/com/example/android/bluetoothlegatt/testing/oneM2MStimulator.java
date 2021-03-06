package com.example.android.bluetoothlegatt.testing;

import android.content.Context;
import android.util.Log;

import com.example.android.bluetoothlegatt.domain.oneM2MList.AE.AE_Create;
import com.example.android.bluetoothlegatt.domain.oneM2MList.Container.Container_Create;
import com.example.android.bluetoothlegatt.domain.oneM2MList.ContentInstance.ContentInstance_Create;
import com.example.android.bluetoothlegatt.etc.PrettyFormatter;
import com.example.android.bluetoothlegatt.reuse.network.HttpRequester;
import com.example.android.bluetoothlegatt.reuse.network.oneM2M.AE;
import com.example.android.bluetoothlegatt.reuse.network.oneM2M.Container;
import com.example.android.bluetoothlegatt.reuse.network.oneM2M.ContentInstance;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class oneM2MStimulator implements oneM2MTester.oneM2MOperation {

    private Context context;

    public oneM2MStimulator(Context context) {
        this.context = context;
    }

    /**** Testing code ****/
    @Override
    public void TC_AE_REG_BV_001() {
        AE aeRegistration = new AE(new AE_Create(context, XMLResponseListener, JSONResponseListener));
        aeRegistration.oneM2MResuest();
    }

    @Override
    public void TC_AE_DMR_BV_001() {
        Container containerRegistration = new Container(new Container_Create(context, XMLResponseListener, JSONResponseListener));
        containerRegistration.oneM2MResuest();
    }

    @Override
    public void TC_AE_DMR_BV_003() {
        ContentInstance contentInstanceRegistration = new ContentInstance(new ContentInstance_Create(context, XMLResponseListener, JSONResponseListener));
        contentInstanceRegistration.oneM2MResuest();
    }

    /**** User defined code ****/
    HttpRequester.NetworkResponseListenerXML XMLResponseListener = new HttpRequester.NetworkResponseListenerXML() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, HttpRequester.NetworkResponseListenerXML networkResponseListenerXML, String responseBody) {
            Log.i("testing", "XML onSuccess");
            Log.i("testing", ""+ statusCode);
        }

        @Override
        public void onFail(int statusCode, Header[] headers, HttpRequester.NetworkResponseListenerXML networkResponseListenerXML, String responseBody) {
            Log.i("testing", "XML onFail");
            Log.i("testing", ""+ statusCode);
        }
    };

    HttpRequester.NetworkResponseListenerJSON JSONResponseListener = new HttpRequester.NetworkResponseListenerJSON() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
            Log.i("testing", "JSON onSuccess");

            if(jsonObject != null) {
                Log.i("testing", PrettyFormatter.getPrettyJSON(jsonObject));
                Log.i("testing", ""+ statusCode);
            }
        }

        @Override
        public void onFail(int statusCode, Header[] headers, JSONObject jsonObject) {
            Log.i("testing", "JSON onFail1");

            if(jsonObject != null) {
                Log.i("testing", PrettyFormatter.getPrettyJSON(jsonObject));
                Log.i("testing", ""+ statusCode);
            }
        }

        @Override
        public void onFail(int statusCode, Header[] headers, String responseString) {
            Log.i("testing", "JSON onFail2");

            if(responseString != null) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(jsonObject != null) {
                    Log.i("testing", PrettyFormatter.getPrettyJSON(jsonObject));
                    Log.i("testing", ""+ statusCode);
                } else {
                    Log.i("testing", PrettyFormatter.getPrettyXML(responseString));
                    Log.i("testing", ""+ statusCode);
                }
            }
        }
    };
}