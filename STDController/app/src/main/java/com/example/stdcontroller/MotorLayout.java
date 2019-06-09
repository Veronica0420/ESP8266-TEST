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

public class MotorLayout extends Fragment {
    private FragmentListener mFragmentListener;
    private Button OnBtn,OffBtn;

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
        View rootView=inflater.inflate(R.layout.fragment_motor_layout,container,false);
        OnBtn= (Button) rootView.findViewById(R.id.OnMotorBtn);
        OffBtn=(Button)rootView.findViewById(R.id.OffMotorBtn);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ledLayout", "on click: ");
        OnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SendAsyncTask().execute("b1");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Motor","ON");
                }
            }
        });
        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new SendAsyncTask().execute("b2");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Motor","OFF");
                }
            }
        });
    }
}
