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

/**
 * This Activity needs Manifest.permission.CAMERA permissions because the
 * ArchitectView will try to start the camera and use the
 * basic functionality for Image-/Instant- and Object Tracking.
 */

public class ArActivity extends AppCompatActivity {

    private static final String TAG = ArActivity.class.getSimpleName();
    protected ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Used to enabled remote debugging of the ArExperience with google chrome https://developers.google.com/web/tools/chrome-devtools/remote-debugging
        WebView.setWebContentsDebuggingEnabled(true);

        final ArchitectStartupConfiguration c = new ArchitectStartupConfiguration(); // Creates a config with its default values.

        // Has to be set, to get a trial license key visit http://www.wikitude.com/developer/licenses.
        c.setLicenseKey(getString(R.string.wikitude_license_key));

        // Use back camera
        c.setCameraPosition(CameraSettings.CameraPosition.BACK);

        // The default resolution is 640x480.
        c.setCameraResolution(CameraSettings.CameraResolution.SD_640x480);

        // The default focus mode is continuous focusing.
        c.setCameraFocusMode(CameraSettings.CameraFocusMode.CONTINUOUS);

        c.setCamera2Enabled(true);

        architectView = new ArchitectView(this);
        architectView.onCreate(c);
        setContentView(architectView); // create ArchitectView with configuration
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
        architectView.onResume(); // Mandatory ArchitectView lifecycle call
    }
    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause(); // Mandatory ArchitectView lifecycle call
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.clearCache();
        architectView.onDestroy(); // Mandatory ArchitectView lifecycle call
    }
}
