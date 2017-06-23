package com.example.rebelartstudios.sternenkrieg.res;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.rebelartstudios.sternenkrieg.R;
import com.example.rebelartstudios.sternenkrieg.exception.MyRuntimeException;
import com.example.rebelartstudios.sternenkrieg.network.Client;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * QR Reader activity containing two use cases:
 *
 * ONE: The host wants to generate a QR code, using the IP address of used host device
 *      and displays it on the screen of the device.
 *
 * TWO: The client wants to receive the hosts IP as a QR code using the clients camera
 *      When successfully scanning a QR code, this code will be put as an intent extra and
 *      will be handed over to the client activity class for further use in the IP TextView
 */

public class QRReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String tag = "QRReader";

    String ip;
    Bundle extras;

    /**
     * onCreate Method
     * The String the value ip gets is either the standard localhost
     * or gets the IP address from the Host Class, which gets the native IP from the phone
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        extras = getIntent().getExtras();

        if (extras == null) {
            ip = "localhost"; // localhost
        } else {
            ip = extras.getString("IP");
        }
    }

    /**
     * OnClickListener for the Button Scan QR Code
     * Opens window and activates the camera to scan a QR Code and when succeeded, prints the Code as Alert ATM.
     * <p>
     * Caution! Some phones still need to manually activate the camera in the Android Settings for this application
     * for the QR Code Reader to work properly.
     * <p>
     *
     *
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
     *
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

    /**
     * Handles the scanned QR Code
     * Send the result as an Intent extra to Client Server IP TextView
     * to use it as input to receive the ip adress of host
     * @param result
     */
    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Intent intent = new Intent(QRReader.this, Client.class);
        intent.putExtra("QR", result.getText());
        startActivity(intent);

        mScannerView.resumeCameraPreview(this);
    }


    /**
     * Creates the QR Code and adds it to an ImageView to print it to the screen
     *
     * @param content
     */
    private void createQRCode(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // Creation of the QR Code
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            ((ImageView) findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            Log.e(tag, "QR Code code creation failed.");
            throw new MyRuntimeException(e);
        }
    }
}
