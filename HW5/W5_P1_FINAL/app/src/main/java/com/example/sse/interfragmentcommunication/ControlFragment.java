package com.example.sse.interfragmentcommunication;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

//This will get inflated up top.
public class ControlFragment extends Fragment {


    private ListView edtSendMessage;
    private EditText edtSendMessage2;
    private Button btnSendMessage;

    public ControlFragment() {  //todo, why?
        // Required empty public constructor
    }

    //*** MESSAGE PASSING MECHANISM ***//
//Need to create an interface to ensure message passing works between fragments.
//This interface, as with all interfaces serves as a contract.  Implementer of this interface, must implement all of its methods.
//Important Fact: Since the MainActivity will implement this, we are guaranteed to find a sendMessage
//routine there!
    public interface ControlFragmentListener {            //this is just an interface definition.
        public void sendMessage(String msg, String msg2); //it could live in its own file.  placed here for convenience.
        public void sendImage(String i);
    }

    ControlFragmentListener CFL;  //Future reference to an object that implements ControlFragmentListener, Can be anything, as long as it implements all interface methods.
    //Question: Who holds the reference?  Answer: ____________
//*** MESSAGE PASSING MECHANISM ***//


    //onAttach gets called when fragment attaches to Main Activity.  This is the right time to instantiate
    //our ControlFragmentListener, why?  Because we know the Main Activity was successfully created and hooked.
    @Override
    public void onAttach(Context context) {   //The onAttach method, binds the fragment to the owner.  Fragments are hosted by Activities, therefore, context refers to: ____________?
        super.onAttach(context);
        CFL = (ControlFragmentListener) context;  //context is a handle to the main activity, let's bind it to our interface.
    }


//NOTE:
//This old onAttach, still works, but is deprecated,
//better to use the newer one above, which passes a context object, which can also be typecast into an Activity Object.
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        CFL = (ControlFragmentListener) activity;
//    }




    //onCreateView, called to have the fragment instantiate it's GUI.
//this is when it is "safe" to generate references to UI components,
//they are guaranteed to exist.  DO NOT interact with UI components
//during onCreate of a fragment, they "may not" be ready.
//happens in between onCreate(..) and onActivityCreated(..)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_control, container, false);  //this needs to be separated from return statement,

//IMPORTANT: MUST BREAK APART THE default return statement IF THERE ARE
// ADDITIONAL VIEWS THAT NEED REFERENCES AND EVENT HANDLERS.
//WHY? So we can refer to the views objects before passing view to Activity.
//we need to bind our views to references and create event handlers before
//before returning the inflated fragment.
//this is why we had to break apart the initial return statement.
        edtSendMessage = (ListView) view.findViewById(R.id.edtSendMessage);
        edtSendMessage2 = (EditText) view.findViewById(R.id.edtSendMessage2);
        // btnSendMessage = (Button) view.findViewById(R.id.btnSendMessage);


        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Chicken Sandwich");
        arrayList.add("Vegan Sandwich");
        arrayList.add("Fish Sandwich");
        arrayList.add("Big Mac");
        arrayList.add("Double Double");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayList);
        edtSendMessage.setAdapter(arrayAdapter);
        edtSendMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //              getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  //FIX ME!!  //Hide the soft keyboard when we click the button.  Ref: https://stackoverflow.com/questions/18977187/how-to-hide-soft-keyboard-when-activity-starts
                CFL.sendMessage(arrayList.get(i).toString(), edtSendMessage2.getText().toString());

                CFL.sendImage(arrayList.get(i).toString());

            }
        });
        return view;
    }

}