package it.jaschke.alexandria.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScanner extends ActionBarActivity implements ZXingScannerView.ResultHandler {
    private static final String LOG_TAG = BarcodeScanner.class.getSimpleName();

    public static final String RESULT_KEY = "RESULT_KEY";

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        final String ean = result.getText();
        // Do something with the result here
        Log.v(LOG_TAG, ean); // Prints scan results

        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY, ean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
