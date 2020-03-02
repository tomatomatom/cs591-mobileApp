package com.example.sse.interfragmentcommratingbar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.lang.reflect.Field;


public class ControlFragment extends Fragment {
    private Button btnLeft;
    private Button btnRight;

    private int currDrawableIndex;
    private int arrayCount;

    public ControlFragment(){
        //required empty constructor
    }

    public interface ControlFragmentListener{
        public void sendIndex(int controlIndex);
    }

    ControlFragmentListener CFL;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CFL = (ControlFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_controls, container, false);

        btnRight = (Button) v.findViewById(R.id.btnRight);
        btnLeft = (Button) v.findViewById(R.id.btnLeft);

        currDrawableIndex = 0;

        getSize(); //get the array size


        //setting up navigation call backs.  (Left and Right Buttons)
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currDrawableIndex == 0)
                    currDrawableIndex = arrayCount - 1;
                else
                    currDrawableIndex--;
                CFL.sendIndex(currDrawableIndex);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currDrawableIndex == arrayCount - 1)
                    currDrawableIndex = 0;
                else
                    currDrawableIndex++;
                CFL.sendIndex(currDrawableIndex);
            }
        });

        return v;
    }

    //count the number of items in the array
    public void getSize() {
        Field[] drawablesFields = com.example.sse.interfragmentcommratingbar.R.drawable.class.getFields();  //getting array of ALL drawables.

        String fieldName;
        for (Field field : drawablesFields) {   //1. Looping over the Array of All Drawables...
            try {
                fieldName = field.getName();    //2. Identifying the Drawables Name, eg, "animal_bewildered_monkey"
                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith("animals_"))  //3. Adding drawable resources that have our prefix, specifically "animal_".
                    arrayCount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
