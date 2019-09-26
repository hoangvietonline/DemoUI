package hoangviet.ndhv.demoui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MirrorActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        InputStream inputStream = null;
        try {
            inputStream = (InputStream) getContentResolver().openInputStream(Uri.parse(uri));
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
