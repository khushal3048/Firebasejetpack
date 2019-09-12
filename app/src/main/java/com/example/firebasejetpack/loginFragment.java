package com.example.firebasejetpack;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginFragment extends Fragment implements View.OnClickListener {

    EditText edt_email, edt_pass;
    Button btn_log;
    TextView txt_reg;

    private FirebaseAuth auth;
    FirebaseUser user;

    Controller navCon;


    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        navCon = new Controller();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_email = view.findViewById(R.id.edt_email);
        edt_pass = view.findViewById(R.id.edt_pass);
        btn_log = view.findViewById(R.id.btn_log);
        txt_reg = view.findViewById(R.id.txt_lrge);

        btn_log.setOnClickListener(this);
        txt_reg.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();

        user = auth.getCurrentUser();

        if (user != null){

            updateUI(user);
            Toast.makeText(getActivity().getApplicationContext(),"User Already Login",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_log) {

            if (TextUtils.isEmpty(edt_email.getText().toString())) {

                edt_email.setError("Email can't be blank!!");
                edt_email.requestFocus();

            } else if (TextUtils.isEmpty(edt_pass.getText().toString())) {

                edt_pass.setError("Password can't blank!!");
                edt_pass.requestFocus();

            } else {

                if (edt_pass.getText().toString().length() < 6) {

                    edt_pass.setError("Password should be at least 6 character!!");
                    edt_pass.requestFocus();

                } else {

                    loginUser(edt_email.getText().toString(), edt_pass.getText().toString());

                }

            }

        } else if (id == R.id.txt_lrge) {

            /*NavController navController = Navigation.findNavController(getActivity(),R.id.host_frag);
            navController.navigate(R.id.registerFragment);*/

            navCon.navigateToFragment(R.id.registerFragment,getActivity(),null);

        }

    }

    public void loginUser(String email, String pass) {

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    user = auth.getCurrentUser();
                    Toast.makeText(getActivity().getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();

                    updateUI(user);

                } else {

                    Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void updateUI(FirebaseUser user){

        Bundle bundle = new Bundle();

        bundle.putParcelable("user",user);

        navCon.navigateToFragment(R.id.dashboardFragment,getActivity(),bundle);

    }
}
