package com.example.sse.interfragmentcommunication;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;

//this will get inflated down below.

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private TextView txtFunnyMessage;
    private TextView txtFunnyMessage2;

    RelativeLayout relativeLayout;


    public BottomFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
        txtFunnyMessage = (TextView)view.findViewById(R.id.txtFunnyMessage);      //need a chance to do this other stuff,
        txtFunnyMessage2 = (TextView)view.findViewById(R.id.txtFunnyMessage2);    //before returning the inflated view.

        relativeLayout = (RelativeLayout)view.findViewById(R.id.r1);

        return view;

        //       return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_bottom, container, false);  //separate me from return statement.
//        txtFunnyMessage = (TextView)view.findViewById(R.id.txtFunnyMessage);      //need a chance to do this other stuff,
//        txtFunnyMessage2 = (TextView)view.findViewById(R.id.txtFunnyMessage2);    //before returning the inflated view.
//        return view;
////        return super.onCreateView(inflater, container, savedInstanceState);
//
//    }

    //Receiving Team
    //It is best practice that this should be accessed via the main activity, not other fragments.
    public void setFunnyMessage(String msg, String msg2){
        txtFunnyMessage.setText(msg);
        txtFunnyMessage2.setText(msg2);
    }
    public void setBackgroundImage(String img){
        if(img.equals("Chicken Sandwich")){
            relativeLayout.setBackgroundResource(R.drawable.chicken);
        } else if(img.equals("Fish Sandwich")) {
            relativeLayout.setBackgroundResource(R.drawable.fish);
        } else if(img.equals("Double Double")) {
            relativeLayout.setBackgroundResource(R.drawable.doubledouble);
        } else if(img.equals("Vegan Sandwich")) {
            relativeLayout.setBackgroundResource(R.drawable.vegan);
        } else if(img.equals("Big Mac")) {
            relativeLayout.setBackgroundResource(R.drawable.bigmac);
        }

    }

}
