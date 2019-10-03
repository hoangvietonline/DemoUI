package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.Objects;

public class MirrorActivity extends AppCompatActivity implements MirrorFragment.OnClickTypeMirror,FrameFragment.setTypeFrameListener {
    private static final int FLIP_VERTICAL = 1;
    private static final int FLIP_HORIZONTAL = 2;
    private static final String TAG = "MirrorActivity";
    private String type = "";
    private TabLayout tabLayoutMirror;
    private CustomView customView;
    private Toolbar toolbar;
    private TextView txtTitleToobar;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        toolbar = findViewById(R.id.toolBar_mirror);
        txtTitleToobar = toolbar.findViewById(R.id.txt_tittle_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.previou);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ViewPager viewPagerMirror = findViewById(R.id.viewPagerMirror);
        customView = findViewById(R.id.customViewMirror);
        tabLayoutMirror = findViewById(R.id.tabLayoutMirror);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        customView.setBitmapFlip(uri);

        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        customView.setDownX((int) event.getX());
                        customView.invalidate();
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
                        customView.invalidate();
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
                Toast.makeText(this, "next", Toast.LENGTH_SHORT).show();
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
}
