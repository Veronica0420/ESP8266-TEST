package com.example.stdcontroller;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stdcontroller.control.Data;
import com.example.stdcontroller.control.MyFragmentPagerAdapter;
import com.example.stdcontroller.control.TCP_client;
import com.example.stdcontroller.control.ActivityToFragment;
import com.example.stdcontroller.control.BottomFragmentActivity;
import com.example.stdcontroller.control.FragmentListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity  implements BottomFragmentActivity, FragmentListener,View.OnClickListener {

    private Button connectBtn,infoBtn,menuBtn;
    private LinearLayout topview;
    private View menuscrool;
    ViewPager mViewPager;
    MyFragmentPagerAdapter mViewPagerFragmentAdapter;
    FragmentManager mFragmentManager;
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    final TemperatureLayout temperatureLayout=new TemperatureLayout();
    final LcdLayout lcdLayout = new LcdLayout();
    final DsLayout dsLayout = new DsLayout();
    final  EepromLayout eepromLayout = new EepromLayout();
    final TouchLayout touchLayout = new TouchLayout();
    final AddaLayout addaLayout = new AddaLayout();
    private  Data app;




    // TCP客户端通信模式下
    private TCP_client tcp_client =null;
    private final static int  CLIENT_STATE_CORRECT_READ=1;
    public final static int  CLIENT_STATE_CORRECT_WRITE=2;               //正常通信信息
    public final static int  CLIENT_STATE_ERROR=3;                 //发生错误异常信息
    public final static int  CLIENT_STATE_IOFO=4;                  //发送SOCKET信息
    private boolean client_islink =false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app=(Data)getApplication();
        mFragmentManager = getSupportFragmentManager();
        initFragmetList();
        mViewPagerFragmentAdapter = new MyFragmentPagerAdapter(mFragmentManager,mFragmentList);
        initView();
        initViewPager();
    }


    public void initView() {

        topview = findViewById(R.id.top);
        menuBtn =topview.findViewById(R.id.menuBtn);
        connectBtn=topview.findViewById(R.id.connectBtn);
        infoBtn=topview .findViewById(R.id.infoBtn);
        connectBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.vpager);
        menuscrool = (View)findViewById(R.id.scrollView1);




    }
    public void initViewPager() {
        // mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(10);
    }
    public void initFragmetList() {
        mFragmentList.add( new LedLayout());   //灯
        mFragmentList.add(new MotorLayout());   //马达
        mFragmentList.add(new BuzzerLayout());   //蜂鸣器
        mFragmentList.add(lcdLayout);            //屏幕
        mFragmentList.add(temperatureLayout);     //温度传感器
        mFragmentList.add(new TubeLayout());      //数码管
        mFragmentList.add( dsLayout);     //ds1302 显示时间
        mFragmentList.add( eepromLayout);   //
        mFragmentList.add(touchLayout);
       // mFragmentList.add(new KeyLayout());   //按键
        mFragmentList.add(addaLayout);
        mFragmentList.add(new ConnectLayout());
        mFragmentList.add(new InfoLayout());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connectBtn:
                Log.d("menuTop", "connectBtn is clicked.");
                mViewPager.setCurrentItem(10);
                break;

            case R.id.infoBtn:
                Log.d("menuTop", "infoBtn is clicked.");
                mViewPager.setCurrentItem(11);
                break;

            case R.id.menuBtn:
                Log.d("MenuLeft", "menuBtn is clicked.");
                if(menuscrool.getVisibility() == VISIBLE){
                    Log.d("MenuLeft", "is became gone.");
                    menuscrool.setVisibility(GONE);
                    menuBtn.setBackgroundResource(R.drawable.menu_1);
                }else{
                    Log.d("MenuLeft", "is became visible.");
                    menuscrool.setVisibility(VISIBLE);
                    menuBtn.setBackgroundResource(R.drawable.menu_2);
                }
                break;
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(tcp_client != null){
            tcp_client.close();
            tcp_client=null;
        }
    }

    //客户端通信模式下
    private Handler cli_handler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch( msg.what){
                case CLIENT_STATE_ERROR :
                    Toast.makeText(MainActivity.this,"连接异常"
                            ,Toast.LENGTH_SHORT).show();
                    client_islink=false;
                    break;
                case CLIENT_STATE_IOFO :
                    client_islink  =true;
                    Log.d("react","true");
                    Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_LONG).show();
                    break;
                //接收数据
                case CLIENT_STATE_CORRECT_READ :
                    Log.d("react","receive");
                    Handler_receive(msg);
                    break;
                //发送数据
                case CLIENT_STATE_CORRECT_WRITE:
                    //Handler_send(msg);
                    break;
            }
        }
    };



    // 接收数据处理分析函数，通过handler从子线程回传到主线程
    //通过全局变量DATA 来判断更新哪个Fragmetn
    private  void Handler_receive(Message msg){
        byte[]  buffer= (byte[]) msg.obj;
        String readMessage = null;
        try {
            readMessage = new String(buffer, 0, msg.arg1, "GBK");
            if(app.getType().equals("Temp")){
                ActivityToFragment tempActivity ;
                tempActivity = temperatureLayout;
                tempActivity.setTemp(readMessage);
            }
            if(app.getType().equals("Lcd")){
                ActivityToFragment LcdActivity ;
                LcdActivity = lcdLayout;
                LcdActivity.setTemp(readMessage);
            }
            if(app.getType().equals("Eeprom")){
                ActivityToFragment EepromActivity ;
                EepromActivity = eepromLayout;
                EepromActivity.setTemp(readMessage);
            }
            if(app.getType().equals("Touch")){
                ActivityToFragment TouchActivity ;
                TouchActivity= touchLayout;
                TouchActivity.setTemp("Dangerous");
            }
            if(app.getType().equals("Adda")){
                ActivityToFragment AddaActivity;
                AddaActivity = addaLayout;
                AddaActivity.setTemp(readMessage);
            }

            Log.d("react",readMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    //回调函数，str 判断fragmemt，position 判断按钮
    @Override
    public void onSuccess(int position) {
            Log.d("FragmentListener", "Bottom");
            mViewPager.setCurrentItem(position);
    }


    @Override
    public void getETContent(String type,final String content) {
        if(type.equals("Connect")){
            if(content.equals("ON")){
                if(tcp_client == null) {
                    tcp_client =new TCP_client(cli_handler);
                    tcp_client.start();
                }
            }
            if(content.equals("OFF")){
                if(tcp_client != null){
                    tcp_client.close();
                    tcp_client=null;
                }
            }
        }
        if(type.equals("Led")){
            if(content.equals("ON")){        //LedON
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            tcp_client.sendmessage("a1");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("OFF")){     //LedOFF
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("a2");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(type.equals("Motor")){
            if(content.equals("ON")){
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("b1");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("OFF")){
                if(client_islink==true){

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("b2");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(type.equals("Buzzer")){
            if(content.equals("ON")){
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("c1");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(type.equals("Lcd")){
            if(client_islink==true){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        String temps = "d"+content ;
                        tcp_client.sendmessage(temps);
                        app.setType("Lcd");
                    }
                };
                new Thread(runnable).start();
            }else
            {
                Toast.makeText(MainActivity.this,"连接未建立",
                        Toast.LENGTH_SHORT).show();
            }

        }
        if(type.equals("Temp")){
            if(client_islink==true){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        tcp_client.sendmessage("e");
                        app.setType("Temp");
                    }
                };
                new Thread(runnable).start();
            }else
            {
                Toast.makeText(MainActivity.this,"连接未建立",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if(type.equals("Tube")){
            if(content.equals("ON")){
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("f1");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("OFF")){
                if(client_islink==true){

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("f2");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(type.equals("DS")){
            if(content.equals("ON")){
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("i1");
                            //app.setType("DS");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("OFF")){
                if(client_islink==true){

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("i2");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(type.equals("Eeprom")){
            if(client_islink==true){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        String temps = "h"+content ;
                        tcp_client.sendmessage(temps);
                        app.setType("Eeprom");
                    }
                };
                new Thread(runnable).start();
            }else
            {
                Toast.makeText(MainActivity.this,"连接未建立",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if(type.equals("Touch")){
            if(client_islink==true){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        tcp_client.sendmessage("k");
                        app.setType("Touch");
                    }
                };
                new Thread(runnable).start();
            }else
            {
                Toast.makeText(MainActivity.this,"连接未建立",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if(type.equals("Adda")){
            if(content.equals("1")){
                if(client_islink==true){
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("m1");
                            app.setType("Adda");
                            //app.setType("DS");
                        }
                    };
                    new Thread(runnable).start();

                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("2")){
                if(client_islink==true){

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("m2");
                            app.setType("Adda");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(content.equals("3")){
                if(client_islink==true){

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            tcp_client.sendmessage("m3");
                            app.setType("Adda");
                        }
                    };
                    new Thread(runnable).start();
                }else
                {
                    Toast.makeText(MainActivity.this,"连接未建立",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}

