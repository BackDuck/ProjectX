package com.example.nurshat.projectx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hello world");

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }

    public void onClick(View v) {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(15)
                .singlePhoto(false)
                .hintOfPick("this is pick hint")
                .filterMimeTypes(new String[]{"image/*"})
                .build();
        GalleryActivity.openActivity(MainActivity.this, 1, config);


    }

    public void toView(View view){
        Intent intent = new Intent(this, PanoramaViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //list of photos of seleced
        if (resultCode == RESULT_OK) {
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            System.out.println(photos.get(0));

            File compressedImage = new Compressor.Builder(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tempx")
                    .build()
                    .compressToFile(new File(photos.get(0)));

            System.out.println("--->>> " + compressedImage.getAbsolutePath());
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
