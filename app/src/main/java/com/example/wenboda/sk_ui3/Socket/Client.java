package com.example.wenboda.sk_ui3.Socket;

/**
 * Created by wenboda on 2017/4/26.
 */
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenboda.sk_ui3.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends AppCompatActivity implements View.OnClickListener {
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
    private StartThread st;
    private ReceiveThread rt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        tv = (TextView) findViewById(R.id.TV);
        et = (EditText) findViewById(R.id.et);
        IPet = (EditText) findViewById(R.id.IPet);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        setButtonOnStartState(true);

        btnSend.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        myhandler = new MyHandler();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:

                st = new StartThread();
                st.start();
                setButtonOnStartState(false);

                break;
            case R.id.btnSend:

                Thread wirte = new Write();

                wirte.start();
                et.setText("");

                break;
            case R.id.btnStop:
                running = false;
                setButtonOnStartState(true);
                try {
                    socket.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    displayToast("nicht Erfolg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    private class Write extends Thread{
        @Override
        public void run() {
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                os.write((et.getText().toString()+"\n").getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }
    private class StartThread extends Thread{
        @Override
        public void run() {
            try {

                socket = new Socket(IPet.getText().toString(),12345);

                rt = new ReceiveThread(socket);
                rt.start();
                running = true;
                System.out.println(socket.isConnected());
                if(socket.isConnected()){
                    Message msg0 = myhandler.obtainMessage();
                    msg0.what=0;
                    myhandler.sendMessage(msg0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReceiveThread extends Thread{
        private InputStream is;

        public ReceiveThread(Socket socket) throws IOException {
            is = socket.getInputStream();
        }
        @Override
        public void run() {
            while (running) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                try {

                    System.out.println(str = br.readLine());

                } catch (NullPointerException e) {
                    running = false;
                    Message msg2 = myhandler.obtainMessage();
                    msg2.what = 2;
                    myhandler.sendMessage(msg2);
                    e.printStackTrace();
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }


                Message msg = myhandler.obtainMessage();


                msg.what = 1;
//                }
                msg.obj = str;
                myhandler.sendMessage(msg);
                try {
                    sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Message msg2 = myhandler.obtainMessage();
            msg2.what = 2;
            myhandler.sendMessage(msg2);

        }
    }

    private void displayToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void setButtonOnStartState(boolean flag){
        btnSend.setEnabled(!flag);
        btnStop.setEnabled(!flag);
        btnStart.setEnabled(flag);
        IPet.setEnabled(flag);
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    System.out.println(msg.obj);
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

            }

        }
    }

}