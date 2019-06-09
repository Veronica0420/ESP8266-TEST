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

import com.example.stdcontroller.control.BottomFragmentActivity;
import com.example.stdcontroller.control.FragmentListener;

public class LedLayout extends Fragment {
    //FragmentActivity listener;
    private  Button OnBtn,OffBtn;
    private FragmentListener mFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener = (FragmentListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_led_layout,container,false);
        OnBtn= (Button) rootView.findViewById(R.id.OnLedbtn);
        OffBtn=(Button)rootView.findViewById(R.id.OffLedBtn);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        OnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Led","ON");
                    Log.d("ledLayout", "ONbtnClicked ");
                }

            }
        });
        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  new SendAsyncTask().execute("a2");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Led","OFF");
                    Log.d("ledLayout", "OFFbtnClicked ");
                }
            }
        });
    }
}
