package hoangviet.ndhv.demoui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MirrorActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        Glide.with(this).load(uri).into(imageView);
    }
}
