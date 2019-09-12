package com.example.firebasejetpack;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class dashboardFragment extends Fragment {

    TextView txt_name;
    Button btn_logout;
    FirebaseFirestore db;
    FirebaseUser user;
    Controller navCon;

    public dashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getArguments().getParcelable("user");
        db = FirebaseFirestore.getInstance();
        navCon = new Controller();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readFireStore();
        txt_name = view.findViewById(R.id.txt_dashname);
        btn_logout = view.findViewById(R.id.btn_logout);



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                navCon.navigateToFragment(R.id.loginFragment,getActivity(),null);
            }
        });

    }

    public void readFireStore(){

        DocumentReference docref = db.collection("users").document(user.getUid());

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){

                        Log.d("snap data",documentSnapshot.getData().toString());
                        txt_name.setText("Welcome " + documentSnapshot.get("Name"));

                    }

                }else {


                }

            }
        });

    }
}
