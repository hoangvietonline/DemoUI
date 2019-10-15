package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.Frame;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.FrameViewHolder> {
    private static final String TAG = "FrameAdapter";
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Frame> frameList;
    private onClickItemFrameListener onClickItemFrameListener;

    FrameAdapter(onClickItemFrameListener onClickItemFrameListener, Context mContext, List<Frame> frameList) {
        this.mContext = mContext;
        this.frameList = frameList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.onClickItemFrameListener = onClickItemFrameListener;
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_frame, viewGroup, false);
        return new FrameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder frameViewHolder, @SuppressLint("RecyclerView") final int i) {
        Frame frame = frameList.get(i);
        if (frame.isChooseFrame()) {
            frameViewHolder.frameLayout.setBackgroundColor(Color.BLUE);
        } else {
            frameViewHolder.frameLayout.setBackgroundColor(Color.WHITE);
        }
        ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(mContext.getApplicationContext()));
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String path = directory.getAbsolutePath();
        File filePath = new File(path);
        File[] filelist = filePath.listFiles();
        for (File file : filelist) {
            Log.d("Files", "FileName:" + file.getName());
            if (file.getName().equals(frameList.get(i).getNameFrame())) {
                frameViewHolder.imgDownload.setVisibility(View.GONE);
            }
        }
        if (i == 0) {
            frameViewHolder.imgDownload.setVisibility(View.GONE);
        }

        Log.d(TAG, "onBindViewHolder: " + frame.getThubFrame());
        Glide.with(mContext).load(frame.getThubFrame()).into(frameViewHolder.imgFrame);
        frameViewHolder.imgFrame.setScaleType(ImageView.ScaleType.FIT_XY);
        frameViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemFrameListener.onClickItemFrame(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return frameList.size();
    }

    interface onClickItemFrameListener {
        void onClickItemFrame(int position);
    }

    class FrameViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFrame;
        private FrameLayout frameLayout;
        private ImageView imgDownload;

        FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFrame = itemView.findViewById(R.id.img_line_frame);
            frameLayout = itemView.findViewById(R.id.frameLayoutFrame);
            imgDownload = itemView.findViewById(R.id.imgDownload);
        }
    }
}
