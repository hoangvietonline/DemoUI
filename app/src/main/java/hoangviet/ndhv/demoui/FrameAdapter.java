package hoangviet.ndhv.demoui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import hoangviet.ndhv.demoui.model.Frame;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.FrameViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Frame> frameList;
    private onClickItemFrameListener onClickItemFrameListener;

    public FrameAdapter(onClickItemFrameListener onClickItemFrameListener,Context mContext, List<Frame> frameList) {
        this.mContext = mContext;
        this.frameList = frameList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.onClickItemFrameListener = onClickItemFrameListener;
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_frame,viewGroup,false);
        return new FrameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder frameViewHolder, final int i) {
        Frame frame = frameList.get(i);
        if (frame.isChooseFrame()){
            frameViewHolder.frameLayout.setBackgroundColor(Color.BLUE);
        }else {
            frameViewHolder.frameLayout.setBackgroundColor(Color.WHITE);
        }
        frameViewHolder.imgFrame.setImageResource(frame.getImgFrame());
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

    public class FrameViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFrame;
        private FrameLayout frameLayout;
        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFrame = itemView.findViewById(R.id.img_line_frame);
            frameLayout = itemView.findViewById(R.id.frameLayoutFrame);
        }
    }
    interface onClickItemFrameListener{
        void onClickItemFrame(int position);
    }
}
