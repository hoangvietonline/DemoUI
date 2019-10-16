package hoangviet.ndhv.demoui;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.Frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends Fragment implements FrameAdapter.onClickItemFrameListener{
    private static final String TAG = "FrameFragment";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecyclerView recyclerViewFrame;
    private List<Frame> frameList;
    private FrameAdapter adapter;
    private setTypeFrameListener mListener;
    private ProgressBar progressBar;
    private TextView txtProgressBar;
    private TextView txtPercentLoad;
    private ImageView imgDowdload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame, container, false);
        recyclerViewFrame = view.findViewById(R.id.recyclerViewFrame);
        frameList = new ArrayList<>();
        frameList.add(new Frame("Frame0", "https://firebasestorage.googleapis.com/v0/b/mirror-644b6.appspot.com/o/thumb%2Fdisabled.png?alt=media&token=e86b221e-73d5-4f72-aa54-d1d3a7974d63", "", false));
        adapter = new FrameAdapter(this, getActivity(), frameList);
        recyclerViewFrame.setAdapter(adapter);
        recyclerViewFrame.setLayoutManager(new GridLayoutManager(getActivity(), 6, LinearLayoutManager.VERTICAL, false));
        DatabaseReference myRef = database.getReference();


        myRef.child("frameDatabase").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Frame frame = dataSnapshot.getValue(Frame.class);
                frameList.add(frame);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, frame.getNameFrame());
        if (!file.exists() && position != 0) {
            String[] pathDown = {frame.getPathFrame(), frame.getNameFrame()};
            dialogDownload(frame.getThubFrame());
            new DownloadFileFromUrl().execute(pathDown);
            mListener.setTypeFrame(frame.getNameFrame());
            adapter.notifyDataSetChanged();
        }
        mListener.setTypeFrame(frame.getNameFrame());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        mListener = (setTypeFrameListener) getActivity();
        super.onAttach(context);
    }

    @SuppressLint("SetTextI18n")
    private void dialogDownload(String urithumb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageButton imageButton = view.findViewById(R.id.btnClause);
        progressBar = view.findViewById(R.id.progressBar);
        txtPercentLoad = view.findViewById(R.id.txtPercentLoad);
        imgDowdload = view.findViewById(R.id.imgImageDownload);
        imgDowdload.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(Objects.requireNonNull(getActivity())).load(urithumb).into(imgDowdload);
        progressBar.setMax(100);
        txtProgressBar = view.findViewById(R.id.txtDialog);
        txtProgressBar.setText("download");
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
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
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
                    mListener.setTypeFrame(path[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(Integer.parseInt(values[0]));
            txtPercentLoad.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtProgressBar.setText("Finish");
            adapter.notifyDataSetChanged();
        }
    }
}
