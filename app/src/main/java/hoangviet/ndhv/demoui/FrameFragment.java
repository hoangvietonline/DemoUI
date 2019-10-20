package hoangviet.ndhv.demoui;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.Frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends Fragment implements FrameAdapter.onClickItemFrameListener{
    private List<Frame> frameList;
    private FrameAdapter adapter;
    private setTypeFrameListener mListener;
    private ProgressBar progressBar;
    private TextView txtProgressBar;
    private TextView txtPercentLoad;
    private int oldPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame, container, false);
        RecyclerView recyclerViewFrame = view.findViewById(R.id.recyclerViewFrame);
        frameList = new ArrayList<>();
        frameList.add(new Frame("Frame0", "https://firebasestorage.googleapis.com/v0/b/mirror-644b6.appspot.com/o/thumb%2Fdisabled.png?alt=media&token=e86b221e-73d5-4f72-aa54-d1d3a7974d63", "", false));
        Gson gson = new Gson();
        String json = loadJSONFromAsset();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject object = (JSONObject) jsonObject.get(key);
                    Frame frame = gson.fromJson(object.toString(),Frame.class);
                    frameList.add(frame);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new FrameAdapter(this, getActivity(), frameList);
        recyclerViewFrame.setAdapter(adapter);
        recyclerViewFrame.setLayoutManager(new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false));


        return view;
    }


    @Override
    public void onClickItemFrame(final int position) {
        final Frame frame = frameList.get(position);
        for (int i = 0; i < frameList.size(); i++) {
            if (i == position) {
                frameList.get(i).setChooseFrame(true);
            } else {
                frameList.get(i).setChooseFrame(false);
            }
        }

        ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getActivity()).getApplicationContext());
        File directory = cw.getDir("frameDir", Context.MODE_PRIVATE);
        File file = new File(directory, frame.getNameFrame());
        if (!file.exists() && position != 0) {
            String[] pathDown = {frame.getPathFrame(), frame.getNameFrame()};
            dialogDownload(frame.getThubFrame());
            new DownloadFileFromUrl().execute(pathDown);
            mListener.setTypeFrame(frame.getNameFrame());
            adapter.notifyDataSetChanged();
        }
        mListener.setTypeFrame(frame.getNameFrame());
        adapter.notifyItemChanged(position);
        adapter.notifyItemChanged(oldPosition);
        oldPosition = position;
    }

    @Override
    public void onAttach(Context context) {
        mListener = (setTypeFrameListener) getActivity();
        super.onAttach(context);
    }

    @SuppressLint("SetTextI18n")
    private void dialogDownload(String uriThumb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageButton imageButton = view.findViewById(R.id.btnClause);
        progressBar = view.findViewById(R.id.progressBar);
        txtPercentLoad = view.findViewById(R.id.txtPercentLoad);
        ImageView imgDownload = view.findViewById(R.id.imgImageDownload);
        imgDownload.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(Objects.requireNonNull(getActivity())).load(uriThumb).into(imgDownload);
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



    interface setTypeFrameListener {
        void setTypeFrame(String type);
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadFileFromUrl extends AsyncTask<String, String, String>  {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtPercentLoad.setText(0+"%");
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
                File directory = cw.getDir("frameDir", Context.MODE_PRIVATE);
                File file = new File(directory, path[1]);
                if (!file.exists()) {
                    OutputStream output = new FileOutputStream(file);
                    byte[] data = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        int temp = (int) ((total * 100) / lengOfFile);
                        publishProgress(""+ temp);
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
            mListener.setTypeFrame(s);
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = Objects.requireNonNull(getActivity()).getAssets().open("frameDatabase.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
