package com.example.rebelartstudios.sternenkrieg;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by christianbauer on 16/05/2017.
 */

public class QR_Reader extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    String ip;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        extras = getIntent().getExtras();

        if (extras == null) {
            ip = "127.0.0.0"; // localhost
        }
        else {
            ip = extras.getString("IP");
        }
    }

    /**
     * OnClickListener for the Button Scan QR Code
     * Opens window and activates the camera to scan a QR Code and when succeeded, prints the Code as Alert ATM.
     *
     * Caution! Some phones still need to manually activate the camera in the Android Settings for this application
     * for the QR Code Reader to work properly.
     *
     * TODO Add this info to the README.md
     * @param v
     */
    public void onClick(View v) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    /**
     * OnClickListener for the Button createQR code
     * @param v
     */
    public void onClickGenerateQR(View v) {
        createQRCode(ip);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        mScannerView.resumeCameraPreview(this);
    }


    private void createQRCode(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            ((ImageView) findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
