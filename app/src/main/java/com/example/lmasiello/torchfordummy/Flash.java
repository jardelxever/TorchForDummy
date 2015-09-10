package com.example.lmasiello.torchfordummy;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Flash extends ActionBarActivity {
    private CameraCaptureSession mSession;
    private CaptureRequest.Builder mBuilder;
    private CameraDevice mCameraDevice;
    private CameraManager mCameraManager;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;
    private flashingTorch taskFlash;
    private Timer timerFlash;
    private MorseTorch taskMorse;
    private Thread threadMorse;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        try {
            init();
            Switch flashSwitch = (Switch) findViewById(R.id.swTurnOnOff);
            SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
            //default of flash mode  is on
            flashSwitch.setChecked(true);
            flashSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                //@Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Toast.makeText(Flash.this, "Seek bar changed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_flash, menu);
    //    return true;
    //}

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_settings) {
    //        return true;
    //    }

    //    return super.onOptionsItemSelected(item);
    //}
    void init(){
        try {
            mCameraManager = (CameraManager) Flash.this.getSystemService(Context.CAMERA_SERVICE);

            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
            boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            if (flashAvailable == true){
                mCameraManager.openCamera("0",new MyCameraDeviceStateCallback(),null);
            }else{
                Toast.makeText(Flash.this, "Flash is not available", Toast.LENGTH_SHORT).show();
            }
            mCameraManager.openCamera("0", new MyCameraDeviceStateCallback(), null);
        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }
    class MyCameraDeviceStateCallback extends CameraDevice.StateCallback {

        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            //get builder
            try {
                mBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                //flash on, default is on
                mBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                List<Surface> list = new ArrayList<Surface>();
                mSurfaceTexture = new SurfaceTexture(1);
                Size size = getSmallestSize(mCameraDevice.getId());
                mSurfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
                mSurface = new Surface(mSurfaceTexture);
                list.add(mSurface);
                mBuilder.addTarget(mSurface);
                camera.createCaptureSession(list, new MyCameraCaptureSessionStateCallback(), null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    }

    private Size getSmallestSize(String cameraId) throws CameraAccessException {
        Size[] outputSizes = mCameraManager.getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(SurfaceTexture.class);
        if (outputSizes == null || outputSizes.length == 0) {
            throw new IllegalStateException(
                    "Camera " + cameraId + "doesn't support any outputSize.");
        }
        Size chosen = outputSizes[0];
        for (Size s : outputSizes) {
            if (chosen.getWidth() >= s.getWidth() && chosen.getHeight() >= s.getHeight()) {
                chosen = s;
            }
        }
        return chosen;
    }

    /**
     * session callback
     */
    class MyCameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            mSession = session;
            try {
                mSession.setRepeatingRequest(mBuilder.build(), null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    }

    private void close() {
        if (mCameraDevice == null || mSession == null) {
            return;
        }
        if (timerFlash != null && taskFlash != null){
            timerFlash.cancel();
            timerFlash.purge();
            taskFlash.cancel();
            timerFlash = null;
            taskFlash = null;
        }
        mSession.close();
        mCameraDevice.close();
        mCameraDevice = null;
        mSession = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_flash);
        try {

            init();
            final Switch flashSwitch = (Switch) findViewById(R.id.swTurnOnOff);
            final Switch morseSwitch = (Switch) findViewById(R.id.swMorse);
            final SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
            //default of flash mode  is on
            morseSwitch.setChecked(false);
            morseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        flashSwitch.setEnabled(false);
                        taskMorse = new MorseTorch();
                        threadMorse = new Thread(new MorseTorch());
                        threadMorse.setPriority(Thread.MAX_PRIORITY);
                        threadMorse.setDaemon(true);
                        threadMorse.start();
                    } else {
                        flashSwitch.setEnabled(true);
                        taskMorse = null;
                        threadMorse.interrupt();
                    }
                }
            });
            flashSwitch.setChecked(false);
            flashSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                //@Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        morseSwitch.setEnabled(false);
                        seekbar.setProgress(0);
                        seekbar.setVisibility(View.VISIBLE);
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        morseSwitch.setEnabled(true);
                        if (timerFlash != null && taskFlash != null) {
                            timerFlash.cancel();
                            timerFlash.purge();
                            taskFlash.cancel();
                            timerFlash = null;
                            taskFlash = null;
                        }
                        seekbar.setVisibility(View.GONE);
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            seekbar.setVisibility(View.GONE);
            seekbar.setProgress(0);
            seekbar.setMax(1500);
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress_value = 0;
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (taskFlash == null && timerFlash == null && progress_value != 0) {
                        taskFlash = new flashingTorch();
                        timerFlash = new Timer();
                        timerFlash.schedule(taskFlash, 0, progress_value);
                    } else {
                        if (progress_value != 0){
                            if (timerFlash != null && taskFlash != null) {
                                timerFlash.cancel();
                                timerFlash.purge();
                                taskFlash.cancel();
                                timerFlash = null;
                                taskFlash = null;
                            }
                            timerFlash = new Timer();
                            taskFlash = new flashingTorch();
                            timerFlash.schedule(taskFlash, 0, progress_value);
                        }
                        else {
                            if (timerFlash != null && taskFlash != null) {
                                timerFlash.cancel();
                                timerFlash.purge();
                                taskFlash.cancel();
                                timerFlash = null;
                                taskFlash = null;
                            }
                            mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                            try {
                                mSession.setRepeatingRequest(mBuilder.build(), null, null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Toast.makeText(Flash.this, progress_value + "/" + seekbar.getMax(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress >= 1450 ){
                        progress = 1450;
                    } else if (progress == 0 ){
                        progress = 1500;
                    }
                    progress_value = 1500 - progress;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
        Toast.makeText(Flash.this, "Seek bar changed", Toast.LENGTH_SHORT).cancel();
    }
    public class flashingTorch extends TimerTask {
        boolean blTorch = true;
        @Override
        public void run() {

            if (blTorch ==true){
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                try {
                    mSession.setRepeatingRequest(mBuilder.build(), null, null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                blTorch = false;
            }else{
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                try {
                    mSession.setRepeatingRequest(mBuilder.build(), null, null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                blTorch = true;
            }
        }
    }
    public class MorseTorch implements Runnable{
        int index = 0;
        String ciao= "ciao danilo";

        @Override
        public void run() {
            Util util = new Util();
            ciao = ciao.trim();
            ciao = util.ReturnMorseString(ciao);
            while (ciao != ""){
                switch (ciao.charAt(index)) {
                    case '.':
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        SystemClock.sleep(500);
                        index++;
                    case '-':
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        SystemClock.sleep(1500);
                        index++;
                    case '?':
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        SystemClock.sleep(3500);
                        index++;
                    case '#':
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        SystemClock.sleep(500);
                        index++;
                    case '_':
                        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                        try {
                            mSession.setRepeatingRequest(mBuilder.build(), null, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        SystemClock.sleep(1500);
                        index++;
                }
                ciao = ciao.substring(1);
            }
        }
    }
}

