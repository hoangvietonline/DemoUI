package hoangviet.ndhv.demoui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MirrorActivity extends AppCompatActivity implements MirrorFragment.OnClickTypeMirror, FrameFragment.setTypeFrameListener, FilterFragment.OnTypeFilterListener {
    private static final String TAG = "MirrorActivity";
    private static final int REQUEST_CODE_FILE = 124;
    public CGENativeLibrary.LoadImageCallback mLoadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        @Override
        public Bitmap loadImage(String name, Object arg) {
            List<String> assetsFileName = getListAssetsName();
            for (int i = 0; i < assetsFileName.size(); i++) {
                if (name.equals(assetsFileName.get(i))) {
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = null;
                    try {
                        inputStream = assetManager.open(name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return BitmapFactory.decodeStream(inputStream);
                }
            }
            List<String> filterName = getListFilterName();
            for (int j = 0; j < filterName.size(); j++) {
                if (name.equals(filterName.get(j))) {
                    ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getApplicationContext()));
                    File directory = cw.getDir("filterDir", Context.MODE_PRIVATE);
                    File file = new File(directory, name);
                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                }
            }
            return null;
        }

        @Override
        public void loadImageOK(Bitmap bmp, Object arg) {
            bmp.recycle();
        }
    };
    public ProgressBar progressBarFilter;
    private File new_file;
    private TabLayout tabLayoutMirror;
    private CustomView customView;
    private TextView txtTitleToobar;
    private Bitmap bitmapResource, bitmapCustom;
    private TextView tabOne, tabTwo, tabThree;

    private List<String> getListAssetsName() {
        AssetManager assetManager = getAssets();
        List<String> listName = new ArrayList<>();
        String[] fileList;
        try {
            fileList = assetManager.list("");
            if (fileList != null) {
                listName.addAll(Arrays.asList(fileList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listName;
    }

    private List<String> getListFilterName() {
        List<String> filterNameList = new ArrayList<>();
        ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getApplicationContext()));
        File directory = cw.getDir("filterDir", Context.MODE_PRIVATE);
        String path = directory.getAbsolutePath();
        File filePath = new File(path);
        File[] filelist = filePath.listFiles();
        for (File file : filelist) {
            filterNameList.add(file.getName());
        }
        return filterNameList;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        Toolbar toolbar = findViewById(R.id.toolBar_mirror);
        txtTitleToobar = toolbar.findViewById(R.id.txt_tittle_toolbar);
        progressBarFilter = findViewById(R.id.progressBarFilter);
        progressBarFilter.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_previous);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


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
                        bitmapResource = resource;
                        customView.setBitmapFlip(bitmapResource);
                        return true;
                    }
                }).submit();
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
        tabOne.setTextColor(Color.BLUE);
        viewPagerMirror.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onPageSelected(int i) {
                Log.d(TAG, "onPageSelected: " + i);
                if (i == 0) {
                    txtTitleToobar.setText("Mirror");
                    tabOne.setTextColor(Color.BLUE);
                    tabTwo.setTextColor(R.color.colorBlack);
                    tabThree.setTextColor(R.color.colorBlack);
                } else if (i == 1) {
                    txtTitleToobar.setText("Frame");
                    tabTwo.setTextColor(Color.BLUE);
                    tabOne.setTextColor(R.color.colorBlack);
                    tabThree.setTextColor(R.color.colorBlack);
                } else {
                    txtTitleToobar.setText("Filter");
                    tabThree.setTextColor(Color.BLUE);
                    tabTwo.setTextColor(R.color.colorBlack);
                    tabOne.setTextColor(R.color.colorBlack);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        CGENativeLibrary.setLoadImageCallback(mLoadImageCallback, null);
    }

    @SuppressLint("InflateParams")
    private void createIconTabLayout() {
        tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabOne.setText(getString(R.string.mirror));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mirror, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(0)).setCustomView(tabOne);

        tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabTwo.setText(getString(R.string.frame));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_frame, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(1)).setCustomView(tabTwo);

        tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custum_tab, null);
        tabThree.setText(getString(R.string.filter));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_filter, 0, 0);
        Objects.requireNonNull(tabLayoutMirror.getTabAt(2)).setCustomView(tabThree);
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
                Intent intent = new Intent(this, ShareActivity.class);
                Log.d(TAG, "onOptionsItemSelected:custom view " + customView);
                bitmapCustom = getBitmapFromView(customView);
                isStoragePermissionGranted();
                Uri imageUri = FileProvider.getUriForFile(MirrorActivity.this, "com.camera.android.FileProvider", new_file);
                Log.d(TAG, "onOptionsItemSelected: " + imageUri.toString());
                String addressFile = new_file.getAbsolutePath();
                Bundle bundle = new Bundle();
                bundle.putString("UriFileImage", imageUri.toString());
                bundle.putString("addressFile", addressFile);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTypeFrame(String type) {
        customView.setTypeFrame(type);
    }

    @Override
    public void onTypeFilter(final String filterRule) {
        progressBarFilter.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmapFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmapResource, filterRule, 1.0f);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customView.setBitmapFlip(bitmapFilter);
                        progressBarFilter.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onTypeMirror(String mirror) {
        customView.setTypeMirror(mirror);
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


    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                File file = fileDisc();
                startSaveImage(file);
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_FILE);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            File file = fileDisc();
            startSaveImage(file);
        }
    }

    @SuppressLint("SetTextI18n")
    private void startSaveImage(File fileDir) {
        FileOutputStream fileOutputStream;
        if (!fileDir.exists()) {
            boolean success = fileDir.mkdirs();
            if (!success) {
                Toast.makeText(this, "can't create folder Image", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = formatDate.format(new Date());
        String name = "Img" + date + ".jpg";
        String file_name = fileDir.getAbsolutePath() + "/" + name;
        new_file = new File(file_name);
        try {
            fileOutputStream = new FileOutputStream(new_file);
            bitmapCustom.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Toast.makeText(this, "Save image success", Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanGallery(this, new_file.getAbsolutePath());
    }

    private File fileDisc() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Mirror");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                File file = fileDisc();
                startSaveImage(file);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // scanning the gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }


}
