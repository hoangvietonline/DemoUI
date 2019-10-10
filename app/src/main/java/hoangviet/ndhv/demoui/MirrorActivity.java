package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.wysaid.nativePort.CGENativeLibrary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MirrorActivity extends AppCompatActivity implements MirrorFragment.OnClickTypeMirror, FrameFragment.setTypeFrameListener, FilterFragment.OnTypeFilterListener {
    private static final String TAG = "MirrorActivity";

    public CGENativeLibrary.LoadImageCallback mLoadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        @Override
        public Bitmap loadImage(String name, Object arg) {
            AssetManager assetManager = getAssets();
            InputStream inputStream;

            try {
                inputStream = assetManager.open(name);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return BitmapFactory.decodeStream(inputStream);
        }

        @Override
        public void loadImageOK(Bitmap bmp, Object arg) {
            bmp.recycle();

        }
    };
    private String type = "";
    private TabLayout tabLayoutMirror;
    private CustomView customView;
    private TextView txtTitleToobar;
    private Bitmap bitmap;


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        Toolbar toolbar = findViewById(R.id.toolBar_mirror);
        txtTitleToobar = toolbar.findViewById(R.id.txt_tittle_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.previou);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        CGENativeLibrary.setLoadImageCallback(mLoadImageCallback, null);

        ViewPager viewPagerMirror = findViewById(R.id.viewPagerMirror);
        customView = findViewById(R.id.customViewMirror);
        tabLayoutMirror = findViewById(R.id.tabLayoutMirror);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");


        Glide.with(this)
                .asBitmap()
                .load(uri)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        bitmap = resource;
                        customView.setBitmapFlip(bitmap);
                        Log.d(TAG, "onResourceReady:resource " + resource);
                        return true;
                    }
                }).submit();


        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        customView.setDownX((int) event.getX());
                        customView.setDownY((int) event.getY());
                        Log.d(TAG, "onTouch:action down " + event.getX());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        customView.setCurrentX((int) event.getX());
                        customView.setCurrentY((int) event.getY());
                        customView.invalidate();
                        Log.d(TAG, "onTouch: " + event.getAction());
                        break;
                    case MotionEvent.ACTION_UP:
                        customView.setUpX((int) event.getX());
                        customView.setUpY((int) event.getY());
                        Log.d(TAG, "onTouch:action up " + event.getX());
                        break;
                }
                return true;
            }
        });
        Log.d(TAG, "onCreate:type   " + type);
        ViewPagerMirrorAdapter adapter = new ViewPagerMirrorAdapter(getSupportFragmentManager());
        viewPagerMirror.setAdapter(adapter);
        viewPagerMirror.setOffscreenPageLimit(3);
//        SparseArray sparseArray = new SparseArray();
        tabLayoutMirror.setupWithViewPager(viewPagerMirror);
        createIconTabLayout();
        int currentItem = viewPagerMirror.getCurrentItem();
        if (currentItem == 0) {
            txtTitleToobar.setText("Mirror");
        }
        viewPagerMirror.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d(TAG, "onPageSelected: " + i);
                if (i == 0) {
                    txtTitleToobar.setText("Mirror");
                } else if (i == 1) {
                    txtTitleToobar.setText("Frame");
                } else {
                    txtTitleToobar.setText("Filter");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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

    @Override
    public void onTypeM1(String M1) {
        type = M1;
        customView.setTypeMirror(M1);
        Log.d(TAG, "onTypeM1: " + type);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.next:
                Intent intent = new Intent(this, SaveImageActivity.class);
                Log.d(TAG, "onOptionsItemSelected:custom view " + customView);
                Bitmap bitmapCustom = getBitmapFromView(customView);
                Bundle bundle = new Bundle();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmapCustom.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                byte[] byteArray = outputStream.toByteArray();

                bundle.putByteArray("bitmap", byteArray);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTypeF1(String F1) {
        customView.setTypeFrame(F1);
    }

    @Override
    public void setTypeF2(String F2) {
        customView.setTypeFrame(F2);
    }

    @Override
    public void setTypeF3(String F3) {
        customView.setTypeFrame(F3);
    }

    @Override
    public void setTypeF4(String F4) {
        customView.setTypeFrame(F4);
    }

    @Override
    public void setTypeF5(String F5) {
        customView.setTypeFrame(F5);
    }

    @Override
    public void setTypeF6(String F6) {
        customView.setTypeFrame(F6);
    }

    @Override
    public void setTypeF7(String F7) {
        customView.setTypeFrame(F7);
    }

    @Override
    public void onTypeFilter0() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[0], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter1() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[1], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter2() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[2], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter3() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[3], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter4() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[4], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter5() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[5], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter6() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[6], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter7() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[7], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter8() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[8], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter9() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[9], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter10() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[10], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter11() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[11], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter12() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[12], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter13() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[13], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter14() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[14], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    @Override
    public void onTypeFilter15() {
        Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[15], 1.0f);
        customView.setBitmapFlip(bitmapFilter);
    }

    private Bitmap getBitmapFromView(CustomView view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}
