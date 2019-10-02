package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MirrorActivity extends AppCompatActivity implements MirrorFragment.OnClickTypeMirror {
    private static final int FLIP_VERTICAL = 1;
    private static final int FLIP_HORIZONTAL = 2;
    private static final String TAG = "MirrorActivity";
    private String type = "";
    private TabLayout tabLayoutMirror;
    private CustomView customView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        ViewPager viewPagerMirror = findViewById(R.id.viewPagerMirror);
        customView = findViewById(R.id.customViewMirror);
        tabLayoutMirror = findViewById(R.id.tabLayoutMirror);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        customView.setBitmapFlip(uri);
//        customView.setTypeImageChoose(typeImage);
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        event.getX();
                        Log.d(TAG, "onTouch:action down " + event.getX());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        customView.setCurrentX((int) event.getX());
                        customView.setCurrentY((int) event.getY());
                        customView.invalidate();
                        Log.d(TAG, "onTouch: " + event.getAction());
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch:action up " + event.getX());
                        break;

                }

                return true;
            }
        });
        Log.d(TAG, "onCreate:type   " + type);
        ViewPagerMirrorAdapter adapter = new ViewPagerMirrorAdapter(getSupportFragmentManager());
        viewPagerMirror.setAdapter(adapter);
        tabLayoutMirror.setupWithViewPager(viewPagerMirror);
        createIconTabLayout();
    }

    private void createIconTabLayout() {
        @SuppressLint("InflateParams") TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabOne.setText(getString(R.string.mirror));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_mirror, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(0)).setCustomView(tabOne);

        @SuppressLint("InflateParams") TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabTwo.setText(getString(R.string.frame));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_frame, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(1)).setCustomView(tabTwo);

        @SuppressLint("InflateParams") TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabThree.setText(getString(R.string.filter));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.filter_icon, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(2)).setCustomView(tabThree);
    }

    private Bitmap flip(Bitmap bitmap, int type) {
        Matrix matrix = new Matrix();
        if (type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        } else if (type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return null;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap flipHorizontal(Bitmap bitmap) {
        int flipGap = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap bitmapFlip = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        Bitmap bitmapWithFlip = Bitmap.createBitmap((width + width + flipGap), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithFlip);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(bitmapFlip, width + flipGap, 0, null);
        return bitmapWithFlip;
    }

    @Override
    public void onTypeM1(String M1) {
        type = M1;
        customView.setTypeMirror(M1);
        Log.d(TAG, "onTypeM1: " +type);
    }

    @Override
    public void onTypeM2(String M2) {
        customView.setTypeMirror(M2);
    }

    @Override
    public void onTypeM3(String M3) {
        customView.setTypeMirror(M3);
    }

    @Override
    public void onTypeM4(String M4) {
        customView.setTypeMirror(M4);
    }

    @Override
    public void onTypeM5(String M5) {
        customView.setTypeMirror(M5);
    }

    @Override
    public void onTypeM6(String M6) {
        customView.setTypeMirror(M6);
    }

    @Override
    public void onTypeM7(String M7) {
        customView.setTypeMirror(M7);
    }

    @Override
    public void onTypeM8(String M8) {
        customView.setTypeMirror(M8);
    }
}
