package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveImageActivity extends AppCompatActivity implements ItemShareAdapter.OnClickItemShareListener {
    private static final String TAG = "SaveImageActivity";
    private ImageView imgSave;
    private Toolbar toolbarSave;
    private Bitmap bitmap;
    private TextView txtUriImage;
    private File new_file;
    private RecyclerView recyclerViewShare;
    private List<ItemShare> itemShareList;
    private ItemShareAdapter itemShareAdapter;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);
        toolbarSave = findViewById(R.id.toolbarSaveActivity);
        txtUriImage = findViewById(R.id.txtUriImage);
        recyclerViewShare = findViewById(R.id.recyclerViewShare);
        itemShareList = new ArrayList<>();
        addItemsShare();
        itemShareAdapter = new ItemShareAdapter(this, this, itemShareList);
        recyclerViewShare.setAdapter(itemShareAdapter);
        recyclerViewShare.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        setSupportActionBar(toolbarSave);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.previou);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        byte[] byteArray = bundle.getByteArray("bitmap");
        bitmap = null;
        if (byteArray != null) {
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        Log.d(TAG, "onCreate:bitmap " + bitmap.getByteCount());
        imgSave = findViewById(R.id.imgSaveActivity);
        imgSave.setImageBitmap(bitmap);
        startSaveImage();
        imageUri = FileProvider.getUriForFile(SaveImageActivity.this, "com.camera.android.FileProvider", new_file);
    }

    private void addItemsShare() {
        itemShareList.add(new ItemShare(R.drawable.facebook_circle, "Facebook"));
        itemShareList.add(new ItemShare(R.drawable.twitter_circle, "Twitter"));
        itemShareList.add(new ItemShare(R.drawable.google_plus, "Google+"));
        itemShareList.add(new ItemShare(R.drawable.instagram, "Instagram"));
        itemShareList.add(new ItemShare(R.drawable.messenger, "Messenger"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            case R.id.homeMirror:
                Intent intent = new Intent(SaveImageActivity.this, MainScreenActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void startSaveImage() {
        FileOutputStream fileOutputStream = null;
        File fileImage = fileDisc();
        if (!fileImage.exists() && !fileImage.mkdirs()) {
            Toast.makeText(this, "can't create directory to save Image", Toast.LENGTH_SHORT).show();
            return;
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = formatDate.format(new Date());
        String name = "Img" + date + ".jpg";
        String file_name = fileImage.getAbsolutePath() + "/" + name;
        new_file = new File(file_name);
        try {
            fileOutputStream = new FileOutputStream(new_file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Toast.makeText(this, "Save image success", Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanGallery(this, new_file.getAbsolutePath());
        Log.d(TAG, "startSaveImage:new_file " + new_file.getAbsolutePath());
        txtUriImage.setText(this.getString(R.string.saveImage) + " " + new_file.getAbsolutePath());
    }

    private File fileDisc() {
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Mirror"
//        File file1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(file, "Mirror");
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

    @Override
    public void onclickItemShare(int position) {

        if (position == 0) {
            String application = "com.facebook.katana";
            shareApplicationOther(application);
        }
        if (position == 1) {
            String application = "com.twitter.android";
            shareApplicationOther(application);
        }
        if (position == 2) {
            String application = "com.google.android.apps.plus";
            shareApplicationOther(application);
        }
        if (position == 3) {
            String application = "com.instagram.android";
            shareApplicationOther(application);
        }
        if (position == 4) {
            String application = "com.facebook.orca";
            shareApplicationOther(application);
        }
    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    private void shareApplicationOther(String application) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        Log.d(TAG, "onclickItemShare:imageUri " + imageUri);
        shareIntent.setType("image/*");

        boolean installed = checkAppInstall(application);
        if (installed) {
            shareIntent.setPackage(application);
            startActivity(shareIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Installed application first", Toast.LENGTH_LONG).show();
        }
    }
}
