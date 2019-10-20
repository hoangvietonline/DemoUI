package hoangviet.ndhv.demoui;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.wysaid.nativePort.CGENativeLibrary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.FilterData;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements FilterAdapter.onCLickItemFilterLitener {
    public static final String[] FILTER_NAME = {
            "None", "natural1", "natural2", "pure01", "pure02", "lovely01", "lovely02", "lovely03", "lovely04",
            "warm01", "warm02", "cool01", "cool02", "vintage", "gray"};
    private static final String TAG = "FilterFragment";
    private List<FilterData> filterDataList;
    private FilterAdapter adapter;
    private OnTypeFilterListener onTypeFilterListener;
    private TextView txtPercentLoad, txtProgressBar;
    private ProgressBar progressBar;
    private int oldPosition = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFilter);
        filterDataList = new ArrayList<>();
        addFilter();
        adapter = new FilterAdapter(this, getActivity(), filterDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(40);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false));

        Gson gson = new Gson();
        String json = loadJSONFromAsset();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject object = (JSONObject) jsonObject.get(key);
                    FilterData filter = gson.fromJson(object.toString(),FilterData.class);
                    filterDataList.add(filter);
                    adapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreateView: " + filterDataList.size());
        return view;
    }

    private void addFilter() {
        AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        for (String s : FILTER_NAME) {
            try {
                InputStream inputStreamFilter = assetManager.open("img_filter.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStreamFilter);
                Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
                Bitmap bitmapScaleFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmapScale, "@adjust lut" + " " + s + ".png", 1.0f);
                filterDataList.add(new FilterData(s, "", "@adjust lut" + " " + s + ".png", bitmapScaleFilter, false));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onTypeFilterListener = (OnTypeFilterListener) getActivity();
    }

    @Override
    public void clickItem(int position) {
        FilterData filterData = filterDataList.get(position);
        for (int i = 0; i < filterDataList.size(); i++) {
            if (i == position)
                filterDataList.get(i).setChooseFilter(true);
            else
                filterDataList.get(i).setChooseFilter(false);
        }
        ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getActivity()).getApplicationContext());
        File directory = cw.getDir("filterDir", Context.MODE_PRIVATE);
        File file = new File(directory, filterData.getFilterName() + ".png");
        if (!file.exists() && position > FilterFragment.FILTER_NAME.length - 1) {
            String[] pathDown = {filterData.getFilterPath(), filterData.getFilterName()};
            dialogDownload(filterData.getFilterThumb());
            new DownloadFileFilterFromUrl().execute(pathDown);
        }
        onTypeFilterListener.onTypeFilter(filterData.getRule());
        adapter.notifyItemChanged(position);
        adapter.notifyItemChanged(oldPosition);
        oldPosition = position;
    }

    @SuppressLint("SetTextI18n")
    private void dialogDownload(String filterThumb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageButton imageButton = view.findViewById(R.id.btnClause);
        progressBar = view.findViewById(R.id.progressBar);
        txtPercentLoad = view.findViewById(R.id.txtPercentLoad);
        ImageView imgDownloadFilter = view.findViewById(R.id.imgImageDownload);
        imgDownloadFilter.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(Objects.requireNonNull(getActivity())).load(filterThumb).into(imgDownloadFilter);
        progressBar.setMax(100);
        txtProgressBar = view.findViewById(R.id.txtDialog);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    interface OnTypeFilterListener {
        void onTypeFilter(String filterRule);
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadFileFilterFromUrl extends AsyncTask<String, String, String> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtPercentLoad.setText(0 + "%");
            progressBar.setProgress(0);
            txtProgressBar.setText("Downloading file. Please wait...");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(String... path) {
            int count;
            try {
                URL url = new URL(path[0]);
                URLConnection connection = url.openConnection();
                int lengOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getActivity()).getApplicationContext());
                File directory = cw.getDir("filterDir", Context.MODE_PRIVATE);
                File file = new File(directory, path[1] + ".png");
                if (!file.exists()) {
                    OutputStream output = new FileOutputStream(file);
                    byte[] data = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        int temp = (int) ((total * 100) / lengOfFile);
                        publishProgress("" + temp);
                        // writing data to file
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path[1];
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(Integer.parseInt(values[0]));
            txtPercentLoad.setText(values[0] + "%");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtProgressBar.setText("Finish");
            adapter.notifyDataSetChanged();
            onTypeFilterListener.onTypeFilter("@adjust lut" + " " + s + ".png");
            Log.d(TAG, "onPostExecute:ruleFilter " + s);
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = Objects.requireNonNull(getActivity()).getAssets().open("filterDatabase.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
