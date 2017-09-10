package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.utils.rq_code.PreferencesActivity;
import com.touyuanren.hahahuyu.utils.rq_code.camera.CameraManager;
import com.touyuanren.hahahuyu.utils.rq_code.decoding.CaptureActivityHandler;
import com.touyuanren.hahahuyu.utils.rq_code.decoding.InactivityTimer;
import com.touyuanren.hahahuyu.utils.rq_code.util.AmbientLightManager;
import com.touyuanren.hahahuyu.utils.rq_code.util.BeepManager;
import com.touyuanren.hahahuyu.utils.rq_code.util.IntentSource;
import com.touyuanren.hahahuyu.utils.rq_code.widgets.ViewfinderView;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

/**
 * 扫码功能
 */
public class CaptureActivity extends Activity implements Callback {

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private IntentSource source;

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();
        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        if (prefs.getBoolean(PreferencesActivity.KEY_DISABLE_AUTO_ORIENTATION,
                true)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//getCurrentOrientation()
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public int getCurrentOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_90:
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            default:
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
                               int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (holder == null) {
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        hasSurface = false;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }

        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    decodeHints, characterSet, cameraManager);
        }
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult
     *            The contents of the barcode.
     * @param scaleFactor
     *            amount by which thumbnail was scaled
     * @param barcode
     *            A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(final Result rawResult, Bitmap barcode,
                             float scaleFactor) {
        inactivityTimer.onActivity();
        // ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(
        // this, rawResult);

        boolean fromLiveScan = barcode != null;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        if (barcode == null) {
            dialog.setIcon(null);
        } else {
            Drawable drawable = new BitmapDrawable(barcode);
            dialog.setIcon(drawable);
        }
        dialog.setTitle("扫描结果");
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.create().show();

        if (fromLiveScan) {
            // delete 2017.17.29 dzt
            // historyManager.addHistoryItem(rawResult, resultHandler);
            // Then not from history, so beep/vibrate and we have an image to
            // draw on
            beepManager.playBeepSoundAndVibrate();
            // drawResultPoints(barcode, scaleFactor, rawResult);
        }
        switch (source) {
            case NATIVE_APP_INTENT:
            case PRODUCT_SEARCH_LINK:
                // handleDecodeExternally(rawResult, resultHandler, barcode);
                break;
            case ZXING_LINK:
                // if (scanFromWebPageManager == null
                // || !scanFromWebPageManager.isScanFromWebPage()) {
                // handleDecodeInternally(rawResult, resultHandler, barcode);
                // } else {
                // handleDecodeExternally(rawResult, resultHandler, barcode);
                // }
                break;
            case NONE:
                // SharedPreferences prefs = PreferenceManager
                // .getDefaultSharedPreferences(this);
                // if (fromLiveScan
                // && prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE,
                // false)) {
                // Toast.makeText(
                // getApplicationContext(),
                // getResources()
                // .getString(R.string.msg_bulk_mode_scanned)
                // + " (" + rawResult.getText() + ')',
                // Toast.LENGTH_SHORT).show();
                // // Wait a moment or else it will scan the same barcode
                // // continuously about 3 times
                // restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
                // } else {
                // handleDecodeInternally(rawResult, resultHandler, barcode);
                // }
                break;
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }
}
