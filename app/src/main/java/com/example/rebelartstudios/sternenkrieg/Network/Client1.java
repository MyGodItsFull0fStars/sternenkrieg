package com.example.rebelartstudios.sternenkrieg.Network;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.QR_Reader;
import com.example.rebelartstudios.sternenkrieg.R;

import java.io.IOException;
import java.net.Socket;

public class Client1 extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private EditText et;
    private EditText IPet;
    private Handler myhandler;
    private Socket socket;
    private String str = "";
    boolean running = false;
    private Button btnSend;
    private Button btnStart;
    private Button btnStop;
    private StartThread st ;
    private ReceiveThreadClient rt;
    String tag = "Client";
    String ip;
    String ipFromQR;
    boolean Exit = true;
    Button btnQR;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeButtonsViews();

        setButtonOnStartState(true);


        btnSend.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnQR.setOnClickListener(this);

        myhandler = new myHandlerClient();

        extras = getIntent().getExtras();

        if (extras == null) {
            ipFromQR = "127.0.0.0"; // localhost
        } else {
            IPet.setText(extras.getString("QR"));
            this.ip = IPet.toString();
        }

    }



    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnStart:

                st = new StartThread(socket, ip, rt, myhandler);
                st.start();
                setButtonOnStartState(false);

                break;
            case R.id.btnSend:

                String info = et.getText().toString();
                socket = st.getSocket();
                Thread wirte = new writeClient(true,socket,st,info);

                wirte.start();
                et.setText("");

                break;
            case R.id.btnStop:

                rt = st.getRt();
                try{ rt.setRunning(false);
                    String Exit = "Exit";
                    wirte = new writeClient(false,socket,st,Exit);
                    wirte.start();
                }catch(NullPointerException e){
                    Log.e(tag, "NullPointerException in Client: " + e.toString());
                }


                setButtonOnStartState(true);
                try {
                    socket = st.getSocket();
                    socket.close();
                    socket = null;
                } catch (NullPointerException e) {
                    Log.e(tag, "NullPointerException in Client: " + e.toString());
                    displayToast("nicht Erfolg");
                } catch (IOException e) {
                    Log.e(tag, "IOException in Client: " + e.toString());
                }
                break;
            case R.id.QRClient:
                Intent intent = new Intent(Client1.this, QR_Reader.class);
                startActivity(intent);
            default:
                break;
        }

    }

//    private class writeClient extends Thread {
//
//        boolean Exit;
//
//        public writeClient(boolean Exit) {
//            this.Exit = Exit;
//        }
//
//        public void run() {
//            OutputStream os = null;
//            try {
//                socket = st.getSocket();
//                os = socket.getOutputStream();
//                if (Exit) {
//                    System.out.println(et.getText().toString());
//                    os.write((et.getText().toString() + "\n").getBytes("utf-8"));
//
//                } else {
//                    os.write(("Exit" + "\n").getBytes("utf-8"));
//                }
//
//            } catch (IOException e) {
//                Log.e(tag, "IOException in WriteThread: " + e.toString());
//            } catch (NullPointerException e) {
//                Log.e(tag, "NullPointerException in WriteThread: " + e.toString());
//
//
//            }
//
//        }
//    }

    private void displayToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /**
     * Enables or disables the buttons using a boolean
     * @param flag
     */
    private void setButtonOnStartState(boolean flag) {
        btnSend.setEnabled(!flag);
        btnStop.setEnabled(!flag);
        btnStart.setEnabled(flag);
        IPet.setEnabled(flag);
    }

    /**
     * Handles the messaging, as well as the feedback while connecting/disconnecting with the server
     */
    class myHandlerClient extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    System.out.println("Client: " + msg.obj);
                    tv.setText(str);
                    break;
                case 0:
                    displayToast("Erfolg");
                    break;
                case 2:
                    displayToast("Der Server wurde getrennt");
                    tv.setText(null);
                    setButtonOnStartState(true);
                    break;
                default:
                    break;
            }

        }
    }


    /**
     * Initialization of the Buttons, TextView and EditTexts
     * Also the value ip gets the content from IPet
     */
    private void initializeButtonsViews() {
        tv = (TextView) findViewById(R.id.TV);
        et = (EditText) findViewById(R.id.et);
        IPet = (EditText) findViewById(R.id.IPet);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnQR = (Button) findViewById(R.id.QRClient);

        this.ip = IPet.getText().toString();
    }
}