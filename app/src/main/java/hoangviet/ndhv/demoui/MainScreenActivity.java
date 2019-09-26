package hoangviet.ndhv.demoui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {
    private static final String TAG = "MainScreenActivity";
    public static final int REQUEST_CODE_GALLERY = 1242;
    public static final int REQUEST_CODE_CAMERA = 565;
    Button btnCammera, btnGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        btnCammera = findViewById(R.id.buttonCamera);
        btnGallery = findViewById(R.id.buttonGallery);
        btnCammera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainScreenActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainScreenActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intentGallery = new Intent(Intent.ACTION_PICK);
                    intentGallery.setType("image/*");
                    startActivityForResult(intentGallery,REQUEST_CODE_GALLERY);
                }
                break;
            case REQUEST_CODE_CAMERA:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            Intent intent = new Intent(MainScreenActivity.this,MirrorActivity.class);
            if (uri != null) {
                intent.putExtra("uri",uri.toString());
            }
            startActivity(intent);
            Log.d(TAG, "onActivityResult: " + data.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
