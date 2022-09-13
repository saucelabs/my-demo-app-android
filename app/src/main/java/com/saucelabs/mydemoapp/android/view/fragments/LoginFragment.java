package com.saucelabs.mydemoapp.android.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricPrompt;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentLoginBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.testfairy.TestFairy;


import java.util.Random;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static com.saucelabs.mydemoapp.android.utils.Network.fetch;


public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "none";
    private FragmentLoginBinding binding;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public static LoginFragment newInstance(String param1, String param2, int param3) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        args.putInt(Constants.ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAct = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(Constants.ARG_PARAM1, "");
            mParam2 = getArguments().getString(Constants.ARG_PARAM2, "");
            mParam3 = getArguments().getInt(Constants.ARG_PARAM3, -1);
        }

//        TestFairy.hideView(R.id.nameET);
        TestFairy.hideView(R.id.password1TV);
        TestFairy.hideView(R.id.password2TV);
        TestFairy.hideView(R.id.passwordET);
//        ST.logActivityToFirebase(getActivity(),"HomeActivity", ST.SCREEN_HOME, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        init();
        setListeners();
        return binding.getRoot();
    }

    private void init() {
        binding.username1TV.setPaintFlags(binding.username1TV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.username2TV.setPaintFlags(binding.username2TV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if (Constants.is_biometric) {
            binding.bioMetricIB.setVisibility(View.VISIBLE);
        } else {
            binding.bioMetricIB.setVisibility(View.GONE);
        }

//        if (mParam1.equals(ST.LOGOUT)) {
//            showAlert();
//        }
    }

    private void setListeners() {
        binding.loginBtn.setOnClickListener(this);
        binding.username1TV.setOnClickListener(this);
        binding.bioMetricIB.setOnClickListener(this);
        binding.username2TV.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view.equals(binding.loginBtn)) {
            loginWithEmail();
        } else if (view.equals(binding.username1TV)) {
            binding.nameET.setText(binding.username1TV.getText().toString().trim());
            binding.passwordET.setText(binding.password1TV.getText().toString().trim());
            binding.nameRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.passwordRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.nameErrorTV.setVisibility(View.INVISIBLE);
            binding.usernameErrorIV.setVisibility(View.INVISIBLE);
            binding.passwordErrorTV.setVisibility(View.INVISIBLE);
            binding.passwordErrorIV.setVisibility(View.INVISIBLE);
        } else if (view.equals(binding.bioMetricIB)) {
            loginWithBiometrics();
        } else if (view.equals(binding.username2TV)) {
            binding.nameET.setText("alice@example.com");
            binding.passwordET.setText(binding.password1TV.getText().toString().trim());
            binding.nameRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.passwordRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.nameErrorTV.setVisibility(View.INVISIBLE);
            binding.usernameErrorIV.setVisibility(View.INVISIBLE);
            binding.passwordErrorTV.setVisibility(View.INVISIBLE);
            binding.passwordErrorIV.setVisibility(View.INVISIBLE);
        }
    }

    private void loginWithBiometrics() {
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Login using you biometric credential")
                .setDescription("Unlock using fingerprint")
                .setAllowedAuthenticators(BIOMETRIC_WEAK)
                .setConfirmationRequired(true)
                .setNegativeButtonText("Nothing")
                .build();

        biometricPrompt = new BiometricPrompt(this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG, "onAuthenticationError: ");

                fetch404();

                TestFairy.addEvent("Authentication errored with biometrics");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.e(TAG, "onAuthenticationSucceeded: ");

                fetchWiki();

                TestFairy.setUserId(getMockBiometricUserName());
                TestFairy.addEvent("User signed in with biometrics");

//                ST.isLogin = true;
                ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_CLEAR_BACK_STACK);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG, "onAuthenticationFailed: ");

                fetch404();

                TestFairy.addEvent("Authentication failed with biometrics");
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }

    private String getMockBiometricUserName() {
        Random random = new Random();
        String[] names = new String[] { "oliver", "william", "james", "benjamin", "henry", "alexander", "guy", "michael", "daniel", "jacob", "roy", "jonathan", "olivia", "charlotte", "sophia", "sarah", "isabella", "evelyn", "harper", "camila", "gianna", "abigail", "ella" };
        return names[Math.abs(random.nextInt()) % names.length] + "@example.com";
    }

    @SuppressLint("NewApi")
    private void loginWithEmail() {
        if (binding.nameET.getText().toString().trim().isEmpty()) {
            binding.nameRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_red));
            binding.nameErrorTV.setVisibility(View.VISIBLE);
            binding.usernameErrorIV.setVisibility(View.VISIBLE);
            binding.passwordRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.passwordErrorTV.setVisibility(View.INVISIBLE);
            binding.passwordErrorIV.setVisibility(View.INVISIBLE);
        } else if (binding.passwordET.getText().toString().trim().isEmpty()) {
            binding.nameRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_grey));
            binding.nameErrorTV.setVisibility(View.INVISIBLE);
            binding.usernameErrorIV.setVisibility(View.INVISIBLE);
            binding.passwordRL.setBackground(getActivity().getDrawable(R.drawable.edit_bg_red));
            binding.passwordErrorTV.setVisibility(View.VISIBLE);
            binding.passwordErrorIV.setVisibility(View.VISIBLE);
            binding.passwordErrorTV.setText(getString(R.string.enter_password));
        } else if (binding.nameET.getText().toString().contains("alice@example.com")) {
            binding.passwordErrorTV.setText(getString(R.string.soory_this_user_has_been_locked_out));
            binding.passwordErrorTV.setVisibility(View.VISIBLE);

            fetch404();

            TestFairy.addEvent("Authentication failed for locked user");
        } else {
            fetchWiki();

            TestFairy.setUserId(binding.nameET.getText().toString().trim());
            TestFairy.addEvent("User signed in with password");

//            ST.isLogin = true;
            if (mParam1.equals(ST.CHECKOUT)) {
                Bundle bundle = ST.getBundle(MainActivity.FRAGMENT_CHECKOUT_INFO, 1);
                ST.startActivityWithDataBundle(mAct, MainActivity.class, bundle, ST.START_ACTIVITY_WITH_FINISH);
            } else
                ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_CLEAR_BACK_STACK);
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(requireActivity(), R.style.MyDialogTheme)
                .setTitle("")
                .setMessage(getString(R.string.you_have_successfully_loggedout))
                .setPositiveButton(getString(R.string.ok), null)
                .setCancelable(false)
                .show();
    }

    private String getRandomMathTopic() {
        String[] topics = new String[] {
                "Complex_number",
                "Polar_coordinate_system",
                "Spherical_coordinate_system",
                "Trigonometric_functions",
                "Hyperbolic_functions",
                "De_Moivre's_formula",
                "Spherical_harmonics",
                "Inverse_trigonometric_functions",
                "Borel–Kolmogorov_paradox",
                "Theta_function",
                "Tangent_half-angle_formula",
                "Table_of_spherical_harmonics",
                "Leibniz_integral_rule",
                "Multiple_integral",
                "List_of_common_coordinate_transformations",
                "Sine_and_cosine",
                "Proofs_of_trigonometric_identities",
                "Vector_spherical_harmonics",
                "List_of_trigonometric_identities",
                "Trigonometric_functions_of_matrices"
        };

        Random random = new Random();

        return topics[random.nextInt(topics.length)];
    }

    private void fetchWiki() {
//        fetch("https://en.wikipedia.org/api/rest_v1/page/summary/" + getRandomMathTopic());
    }

    private void fetch404() {
//        fetch("https://en.wikipedia.org/api/rest_v1/page/summary/AdminLogin");
    }
}