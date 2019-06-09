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
import com.example.stdcontroller.control.FragmentListener;

public class AddaLayout extends Fragment implements ActivityToFragment {
    private FragmentListener mFragmentListener;
    private Button ad1Btn,ad2Btn,ad3Btn;
    private TextView tvContent;

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
        View rootView=inflater.inflate(R.layout.fragment_adda_layout,container,false);
        ad1Btn= (Button) rootView.findViewById(R.id.ad1Btn);
        ad2Btn= (Button) rootView.findViewById(R.id.ad2Btn);
        ad3Btn= (Button) rootView.findViewById(R.id.ad3Btn);
        tvContent=(TextView) rootView.findViewById(R.id.tvADcontent);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("addaLayout", "on click: ");
        ad1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SendAsyncTask().execute("c");
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Adda","1");
                }
            }
        });
        ad2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Adda","2");
                }
            }
        });

        ad3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.getETContent("Adda","3");
                }
            }
        });
    }

    @Override
    public void setTemp(String temp) {
        tvContent.setText(temp);
    }
}
