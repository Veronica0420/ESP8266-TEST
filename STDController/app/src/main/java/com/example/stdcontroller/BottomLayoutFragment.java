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


public class BottomLayoutFragment extends Fragment
{

    private BottomFragmentActivity mFragmentListener;
    private Button ledBtn,motorBtn,buzzerBtn,lcdBtn,temprtrBtn,tubeBtn,dsBtn,eepromBtn,touchBtn,addaBtn;

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener =  (BottomFragmentActivity)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("react", "bottom: ");
         View rootView=inflater.inflate(R.layout.fragment_bottom_layout,container,false);
        ledBtn = (Button) rootView.findViewById(R.id.ledBtn);
        motorBtn=(Button)rootView.findViewById(R.id.motorBtn);
        buzzerBtn=(Button)rootView.findViewById(R.id.buzzerBtn);
        lcdBtn=(Button)rootView.findViewById(R.id.lcdBtn);
        temprtrBtn=(Button)rootView.findViewById(R.id.temprtrBtn);
        tubeBtn=(Button)rootView.findViewById(R.id.tubeBtn);
        dsBtn=(Button)rootView.findViewById(R.id.dsBtn);
        eepromBtn=(Button)rootView.findViewById(R.id.eepromBtn);
        touchBtn=(Button)rootView.findViewById(R.id.touchBtn);
      //  keyBtn=(Button)rootView.findViewById(R.id.keyBtn);
        addaBtn=(Button)rootView.findViewById(R.id.addaBtn);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("react", "bottom1: ");
        ledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(0);
                }

            }
        });
        motorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(1);
                }
            }
        });
        buzzerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(2);
                }
            }
        });
        lcdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(3);
                }
            }
        });
        temprtrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(4);
                }
            }
        });
        tubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(5);
                }
            }
        });

        dsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(6);
                }
            }
        });
        eepromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(7);
                }
            }
        });
        touchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(8);
                }
            }
        });

        addaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    mFragmentListener.onSuccess(9);
                }
            }
        });
    }






}
