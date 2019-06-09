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
import android.widget.TextView;

import com.example.stdcontroller.control.ActivityToFragment;
import com.example.stdcontroller.control.BottomFragmentActivity;
import com.example.stdcontroller.control.FragmentListener;

public class DsLayout extends Fragment implements ActivityToFragment {
    private Button OnBtn;
    private Button OffBtn;
    private TextView tvContent;
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

        View rootView =inflater.inflate(R.layout.fragment_ds_layout,container,false);
        OnBtn = (Button) rootView.findViewById(R.id.OnTouchBtn);
        OffBtn = (Button) rootView.findViewById(R.id.OffBtn);
        tvContent=(TextView)rootView.findViewById(R.id.tvContent);
        return  rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new SendAsyncTask().execute("d");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("DS", "ON");
                }
                Log.d("react", "DS.click");
            }
        });
        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("DS", "OFF");
                }
                Log.d("react", "DS.click");

            }

        });
    }

    @Override
    public void setTemp(String temp) {
        tvContent.setText(temp);
    }
}
