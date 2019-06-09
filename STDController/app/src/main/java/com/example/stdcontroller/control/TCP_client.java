package com.example.stdcontroller.control;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_client extends Thread {
    private static final String TAG_1 = "TCPChat";
    private Handler mhandler;
    private Socket socket;
    private boolean isruning;
    public InputStream inputStream;
    public OutputStream outputStream;
    private PrintStream out = null;

    private static final String IP = "192.168.4.1";
    private static final int PORT = 333;//端口号
    public static int  CLIENT_STATE_CORRECT_READ=1;
    public static int  CLIENT_STATE_CORRECT_WRITE=2;               //正常通信信息
    public static int  CLIENT_STATE_ERROR=3;                 //发生错误异常信息
    public static int  CLIENT_STATE_IOFO=4;                  //发送SOCKET信息

    public TCP_client(Handler mhandler) {
        this.mhandler=mhandler;       //传递handler对象用于与主线程的信息交互
        isruning=true;
    }


    @Override
    public void run() {
        if(socket == null){
            try {

                Log.e(TAG_1,"启动连接线程");
                socket=new Socket(IP,PORT);
                socket.setSoTimeout(5000);
                new Receive_Thread(socket).start();  //启动接收线程
                getadress();
            } catch (IOException e) {
                e.printStackTrace();
                senderror();
            }
        }
    }
    public void getadress()  //获取本地的IP地址和端口号
    {
        String[] strings = new String[2];
        strings[0]=socket.getInetAddress().getHostAddress();
        strings[1]=socket.getInetAddress().getHostName();
        Message message = mhandler.obtainMessage(CLIENT_STATE_IOFO,-1,-1,strings);
        mhandler.sendMessage(message);
    }

    public  void close(){
        if (socket !=null){
            try {
                socket.close();
                socket=null;
                isruning=false;
            } catch (IOException e) {
            }
        }else if (socket ==null){
            Log.e(TAG_1, "未建立连接");
        }
    }


    //启动接收线程
    class Receive_Thread extends Thread{
        private  Socket msocket;
        public Receive_Thread (Socket msocket){
            this.msocket =msocket;
        }
        @Override
        public void run() {
            try {
                while (isruning) {
                    inputStream = msocket.getInputStream();
                    while (inputStream.available()==0){
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final byte[] buffer = new byte[1024];//创建接收缓冲区

                    final int len = inputStream.read(buffer);//数据读出来，并且数据的长度
                    String i = len+"";
                    Log.d("react",i+new String(buffer));
                    mhandler.sendMessage(mhandler.
                            obtainMessage(CLIENT_STATE_CORRECT_READ,len,-1,buffer));
                }
            }catch (IOException e) {
                e.printStackTrace();
                senderror();
            }finally {
                if(msocket!=null){
                    try {
                        msocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if(inputStream!=null){
                        inputStream.close();
                    }
                    if (out!=null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG_1,"关闭连接，释放资源");
            }
        }
    }
    public void sendmessage(String message){
        try {
            outputStream =socket.getOutputStream();
            out = new PrintStream(outputStream);
            out.print(message);
            out.flush();
            mhandler.sendMessage(mhandler.
                    obtainMessage(CLIENT_STATE_CORRECT_WRITE,-1,-1,message));


        } catch (IOException e) {
            senderror();
        }
    }

    void senderror(){
        mhandler.sendMessage(mhandler.obtainMessage(CLIENT_STATE_ERROR));  //发送连接的错误
    }
}
