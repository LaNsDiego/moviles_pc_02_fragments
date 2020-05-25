package com.desarrollo.fragmentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements SimpleFragment.OnFragmentInteractionListener {

    private Button mButton, btn_toggle_detail;
    private boolean isFragmentDisplayed = false , isFragmentDetailDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";

    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);
        btn_toggle_detail = findViewById(R.id.btn_toggle_detail);

        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
        btn_toggle_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DISPLAYED ?" , String.valueOf(isFragmentDetailDisplayed));
                int orientation = getResources().getConfiguration().orientation;

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    if(!isFragmentDetailDisplayed){

                        displayFragmentDetail();
                    }else{
                        closeFragmentDetail();
                    }

                } else {
                    // In portrait
                    Log.d("STATE","PORTRAIT IS ON");
                }

            }
        });


    }


    private void displayFragmentDetail(){
        DetailFragment detailFragment = DetailFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.frame_layout_detail,
                detailFragment).addToBackStack(null).commit();

        btn_toggle_detail.setText("CERRAR DETALLE");
        isFragmentDetailDisplayed = true;
    }

    private void closeFragmentDetail(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailFragment = (DetailFragment) fragmentManager
                .findFragmentById(R.id.frame_layout_detail);
        if (detailFragment != null) {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(detailFragment).commit();
        }else{

        }
        btn_toggle_detail.setText("ABRIR DETALLE");
        isFragmentDetailDisplayed = false;
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        // TODO: Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // TODO: Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();
// Update the Button text.
        mButton.setText(R.string.close);
// Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text.
        mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        // Keep the radio button choice to pass it back to the fragment.
        mRadioButtonChoice = choice;
        Toast.makeText(this, "La elección es " + Integer.toString(choice),
                Toast.LENGTH_SHORT).show();
    }
}
