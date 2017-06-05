package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;

/**
 * Created by wenboda on 2017/6/5.
 */

public class myHandle extends Handler {

    String message;
    Handler handler = new Handler();

    public myHandle(){}

    public Handler getHandler(){
        return handler;
    }

    public String getMessage(){
        return this.message;
    }
    public void handleMessage(Message msg) {


        switch (msg.what) {
            case 1:
                message = (String) msg.obj;

                System.out.println(message);
                break;
            case 0:
                System.out.println("Erfolg");

                break;
            case 2:
                System.out.println("Client getrennt");
                break;
        }
    }

}
