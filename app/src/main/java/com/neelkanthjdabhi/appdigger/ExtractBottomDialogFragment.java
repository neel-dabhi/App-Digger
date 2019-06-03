package com.neelkanthjdabhi.appdigger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;


public class ExtractBottomDialogFragment extends BottomSheetDialogFragment {

    TextView textView,cancel_tv,extract_tv,openInGoogle;
    String appName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    String packageName;
    ImageView imageView;
    Drawable iconDrawable;
    View view;

    public static ExtractBottomDialogFragment newInstance() {
        return new ExtractBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottomsheet_layout, container, false);

        textView = (TextView) view.findViewById(R.id.tv_bottom_sheet_heading);
        imageView = (ImageView) view.findViewById(R.id.imageviewBottomSheet);
        openInGoogle = (TextView) view.findViewById(R.id.openInGoogle);
        textView.setText(getAppName());
        imageView.setImageDrawable(getIconDrawable());
        extract_tv = (TextView) view.findViewById(R.id.extract_tv);
        cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);

        extract_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extract(getPackageName(),getAppName());
            }
        });

        openInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInGoogle(getPackageName());

            }
        });

        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // get the views and attach the listener
        return view;

    }

    private void openInGoogle(String packageName) {
        final String appPackageName = packageName; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public void extract(String packageName, String AppName) {
        if (isStoragePermissionGranted()) {
            new APKBackup(getContext()).extractapk(packageName);


        }
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }





}