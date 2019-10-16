package hoangviet.ndhv.demoui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.Frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends Fragment implements FrameAdapter.onClickItemFrameListener {
    private static final String TAG = "FrameFragment";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RecyclerView recyclerViewFrame;
    private List<Frame> frameList;
    private FrameAdapter adapter;
    private setTypeFrameListener mListener;


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
//        saveToInternalStorage(frame);
        mListener.setTypeFrame(frame.getNameFrame(),frame.getPathFrame());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        mListener = (setTypeFrameListener) getActivity();
        super.onAttach(context);
    }

    private void saveToInternalStorage(final Frame frame) {
        dialogDownload();
        Glide.with(Objects.requireNonNull(getActivity())).asBitmap().load(frame.getPathFrame()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(getActivity()).getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, frame.getNameFrame());
                if (!file.exists()) {
                    Log.d("path", file.toString());
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        Toast.makeText(getActivity(), "save image success", Toast.LENGTH_SHORT).show();
                        fos.flush();
                        fos.close();
                        mListener.setTypeFrame(frame.getNameFrame(),frame.getPathFrame());
                        adapter.notifyDataSetChanged();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    private void dialogDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageButton imageButton = view.findViewById(R.id.btnClause);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    interface setTypeFrameListener {
        void setTypeFrame(String type,String pathFrame);
    }


}
