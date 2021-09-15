package com.saucelabs.mydemoapp.android.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentDrawingBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingFragment extends BaseFragment implements View.OnClickListener {
    private FragmentDrawingBinding binding;

    public static DrawingFragment newInstance(String param1, String param2, int param3) {
        DrawingFragment fragment = new DrawingFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drawing, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        setListeners();
        checkPermissions();
    }

    private void checkPermissions() {
            int writeStoragePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();

            if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(requireActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1122);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1122: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    } else {
//                        singleton.showCommonDialog(null, "Permission Denied!", "Please go to settings and enable permissions.", getString(R.string.ok), true);
                    }

                }
            }
        }
    }

    private void setListeners() {
        binding.clearBtn.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(binding.clearBtn)) {
            binding.signaturePad.clear();
        } else if (v.equals(binding.saveBtn)) {
                Bitmap b = binding.signaturePad.getTransparentSignatureBitmap();

                ((MainActivity)mAct).saveFile(b);

//            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
//            File myDir = new File(root);
//            if (!myDir.exists()) {
//                myDir.mkdirs();
//            }
//
//            String path = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
//            path = "IMAGE_" + path + ".jpg";
//            File f = new File(myDir.getAbsolutePath() + File.separator + path);
//
//            if (f.exists()) f.delete();
//            try {
//                showAlert();
//                FileOutputStream out = new FileOutputStream(f);
//                b.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                out.flush();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(requireActivity(),R.style.MyDialogTheme)
                .setTitle("Save drawing")
                .setMessage("Drawing saved successfully to gallery")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .show();
    }
}