package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Objects;

public class MirrorActivity extends AppCompatActivity {
    private TabLayout tabLayoutMirror;
    private static final int FLIP_VERTICAL = 1;
    private static final int FLIP_HORIZONTAL = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        final ImageView imgMirror = findViewById(R.id.imgMirror);
        ViewPager viewPagerMirror = findViewById(R.id.viewPagerMirror);
        tabLayoutMirror = findViewById(R.id.tabLayoutMirror);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        imgMirror.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                          Bitmap bitmap = flipHorizontal(resource);
                          imgMirror.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        ViewPagerMirrorAdapter adapter = new ViewPagerMirrorAdapter(getSupportFragmentManager());
        viewPagerMirror.setAdapter(adapter);
        tabLayoutMirror.setupWithViewPager(viewPagerMirror);
        createIconTabLayout();
    }
    private void createIconTabLayout(){
        @SuppressLint("InflateParams") TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab,null);
        tabOne.setText(getString(R.string.mirror));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.icon_mirror,0,0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(0)).setCustomView(tabOne);

        @SuppressLint("InflateParams") TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab,null);
        tabTwo.setText(getString(R.string.frame));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.icon_frame,0,0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(1)).setCustomView(tabTwo);

        @SuppressLint("InflateParams") TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab,null);
        tabThree.setText(getString(R.string.filter));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.filter_icon,0,0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(2)).setCustomView(tabThree);
    }
    private Bitmap flip(Bitmap bitmap,int type){
        Matrix matrix = new Matrix();
        if (type == FLIP_VERTICAL){
            matrix.preScale(1.0f,-1.0f);
        }else if (type == FLIP_HORIZONTAL){
            matrix.preScale(-1.0f,1.0f);
        }else{
            return null;
        }
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
    private Bitmap flipHorizontal(Bitmap bitmap){
        int flipGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f,1.0f);
        Bitmap bitmapFlip = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        Bitmap bitmapWithFlip = Bitmap.createBitmap((width+width+flipGap),height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithFlip);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(bitmapFlip,width+flipGap,0,null);
        return bitmapWithFlip;

    }

}
