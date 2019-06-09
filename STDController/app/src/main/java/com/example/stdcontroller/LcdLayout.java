package com.example.stdcontroller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stdcontroller.control.ActivityToFragment;
import com.example.stdcontroller.control.FragmentListener;

public class LcdLayout extends Fragment implements ActivityToFragment {
    private Button sendBtn;
    private TextView tvContent;
    private EditText etSend;
    private FragmentListener mFragmentListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener =  (FragmentListener)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //eturn inflater.inflate(R.layout.fragment_lcd_layout,container,false);
        View rootView=inflater.inflate(R.layout.fragment_lcd_layout,container,false);
        sendBtn=(Button)rootView.findViewById(R.id.sendBtn);
        tvContent=(TextView)rootView.findViewById(R.id.tvContent);
        etSend=(EditText)rootView.findViewById(R.id.etSend);
        return  rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new SendAsyncTask().execute("9");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Lcd",etSend.getText().toString());
                }

                Log.d("react","Lcd.click");

            }
        });
    }


    @Override
    public void setTemp(String temp) {
        String num= temp.substring(0,1);
        String temps= temp.substring(1);
        tvContent.setText(num+":"+temps);
    }


}
