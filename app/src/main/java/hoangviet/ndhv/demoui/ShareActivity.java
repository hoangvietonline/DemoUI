package hoangviet.ndhv.demoui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hoangviet.ndhv.demoui.model.ItemShare;

public class ShareActivity extends AppCompatActivity implements ItemShareAdapter.OnClickItemShareListener {
    private static final String TAG = "ShareActivity";
    private ImageView imgSave;
    private Toolbar toolbarSave;
    private TextView txtUriImage;
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
        imgSave = findViewById(R.id.imgSaveActivity);
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_previous);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        imageUri = Uri.parse(bundle.getString("UriFileImage"));
        String addressFile = bundle.getString("addressFile");
        Glide.with(this).load(imageUri).into(imgSave);
        txtUriImage.setText(addressFile);
    }

    private void addItemsShare() {
        itemShareList.add(new ItemShare(R.drawable.facebook_circle, "Facebook", "com.facebook.katana"));
        itemShareList.add(new ItemShare(R.drawable.twitter_circle, "Twitter", "com.twitter.android"));
        itemShareList.add(new ItemShare(R.drawable.google_plus, "Google+", "com.google.android.apps.plus"));
        itemShareList.add(new ItemShare(R.drawable.instagram, "Instagram", "com.instagram.android"));
        itemShareList.add(new ItemShare(R.drawable.messenger, "Messenger", "com.facebook.orca"));
        itemShareList.add(new ItemShare(R.drawable.more_icon1, "Share", null));
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
                Intent intent = new Intent(ShareActivity.this, MainScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onclickItemShare(int position) {
        if (position < 5) {
            shareApplicationOther(itemShareList.get(position).getAppName());
        }
        if (position == 5) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Mirror");
            shareIntent.setType("image/jpeg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share"));
        }

    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    private void shareApplicationOther(String application) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Mirror");
        shareIntent.setType("image/jpeg");
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
