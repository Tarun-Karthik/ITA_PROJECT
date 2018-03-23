package com.example.abc.ita_v30;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.example.abc.ita_v30.ui.camera.GraphicOverlay;
import java.io.IOException;

public abstract class LiveCapture extends AppCompatActivity implements Detector.Processor<TextBlock> {

    SurfaceView cameraView;
    static TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    Context context=this;
        
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    LiveCapture(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionID:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.part = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_capture);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        textRecognizer.setProcessor(new LiveCapture(mGraphicOverlay) {
            @Override
            public void release() { mGraphicOverlay.clear();

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                mGraphicOverlay.clear();
                SparseArray<TextBlock> items = detections.getDetectedItems();
                    /*if(items.size()!=0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {


                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0;i<items.size();i++)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }


                                //String languagePair = "en-fr";
                                String text = stringBuilder.toString();
                                AsyncTask<String, Void, String> result = Translate(text,MainActivity.languagePair);
                                //textView.setText(result.toString());
                            }
                        });
                    }*/
                for(int i=0;i<items.size();i++)
                {
                    TextBlock item = items.valueAt(i);
                    if(item!=null && item.getValue()!=null){
                        Log.d("Processor", "Text detected! " + item.getValue());
                    }
                    OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                    mGraphicOverlay.add(graphic);
                }
            }
        });
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Dectector dependcies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(15.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LiveCapture.this,new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });


        }

    }


    AsyncTask<String, Void, String> Translate(String textToBeTranslated, String languagePair){

        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(context);


        AsyncTask<String, Void, String> translationResult = translatorBackgroundTask.execute(textToBeTranslated,languagePair);


        Log.d("Translation Result", String.valueOf(translationResult)); // Logs the result in Android Monitor


        return translationResult;
    }

}
