package com.example.firebasejetpack;

import android.app.Activity;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Controller {

    public void navigateToFragment(int fregId, Activity activity, Bundle bundle){

        NavController navController = Navigation.findNavController(activity,R.id.host_frag);
        navController.navigate(fregId,bundle);

    }
}
