package com.example.rebelartstudios.sternenkrieg.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by wenboda on 2017/6/5.
 */

public class myhandler extends Handler {

    String message;
    Handler handler = new Handler();

    public myhandler(){
        //empty?
    }

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
                Log.i(myhandler.class.getName(),"Message: "+message);
                break;
            case 0:
                Log.i(myhandler.class.getName(),"Erfolg");
                break;
            case 2:
                Log.i(myhandler.class.getName(),"Client getrennt");
                break;
            default:
                break;
        }
    }

}
