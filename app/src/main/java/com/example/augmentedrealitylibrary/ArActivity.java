package com.example.augmentedrealitylibrary;

import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.sdksamples.R;

import java.io.IOException;


public class ArActivity extends AppCompatActivity {

    private static final String TAG = ArActivity.class.getSimpleName();
    protected ArchitectView architectView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView.setWebContentsDebuggingEnabled(true);

        final ArchitectStartupConfiguration c = new ArchitectStartupConfiguration();

        c.setLicenseKey(getString(R.string.wikitude_license_key));

        c.setCameraPosition(CameraSettings.CameraPosition.BACK);

        c.setCameraResolution(CameraSettings.CameraResolution.SD_640x480);

        c.setCameraFocusMode(CameraSettings.CameraFocusMode.CONTINUOUS);

        c.setCamera2Enabled(true);

        c.setFeatures(ArchitectStartupConfiguration.Features.ImageTracking);

        architectView = new ArchitectView(this);
        architectView.onCreate(c);
        setContentView(architectView);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            architectView.load("html/index.html");
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.error_loading_ar_experience), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Exception while loading arExperience.", e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.clearCache();
        architectView.onDestroy();
    }
}
