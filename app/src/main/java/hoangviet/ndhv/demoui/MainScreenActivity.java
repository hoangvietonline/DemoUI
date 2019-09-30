package hoangviet.ndhv.demoui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainScreenActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_GALLERY = 1242;
    public static final int REQUEST_CODE_CAMERA = 565;
    private static final String TAG = "MainScreenActivity";
    private String currentImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button btnCamera = findViewById(R.id.buttonCamera);
        Button btnGallery = findViewById(R.id.buttonGallery);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainScreenActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentGallery = new Intent(Intent.ACTION_PICK);
                    intentGallery.setType("image/*");
                    startActivityForResult(intentGallery, REQUEST_CODE_GALLERY);
                }
                break;
            case REQUEST_CODE_CAMERA:
                dispathTakePictureIntent();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                currentImagePath = uri.toString();
            }
            Log.d(TAG, "onActivityResult: " + data.toString());
            try {
                assert uri != null;
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Log.d(TAG, "onActivityResult:bit map đó " + bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(MainScreenActivity.this, MirrorActivity.class);
            intent.putExtra("uri", currentImagePath);
            startActivity(intent);
        }
        else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            Intent intent = new Intent(MainScreenActivity.this, MirrorActivity.class);
            intent.putExtra("uri", currentImagePath);
            startActivity(intent);
        }
}

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentImagePath = image.getAbsolutePath();
        return image;
    }

    private void dispathTakePictureIntent() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.camera.android.FileProvider", photoFile);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
            }
        }
    }
}
