package hoangviet.ndhv.demoui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MirrorAdapter extends RecyclerView.Adapter<MirrorAdapter.MirrorViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Mirror> mirrorList;
    private OnClickItemListener onClickItemListener;

    public MirrorAdapter(OnClickItemListener onClickItemListener,Context mContext, List<Mirror> mirrorList) {
        this.mContext = mContext;
        this.mirrorList = mirrorList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MirrorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.line_mirror,viewGroup,false);
        return new MirrorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MirrorViewHolder mirrorViewHolder, final int i) {
        final Mirror mirror = mirrorList.get(i);
        mirrorViewHolder.txtMiror.setText(mirror.getTxtMirror());
        mirrorViewHolder.imgMirror.setImageResource(mirror.getImgMirror());
        if (mirror.isClick()){
            mirrorViewHolder.itemView.setBackgroundColor(Color.BLUE);
        }else {
            mirrorViewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
        mirrorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mirrorList.size();
    }

    public class MirrorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMirror;
        private TextView txtMiror;
        public MirrorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMirror = itemView.findViewById(R.id.img_line_mirror);
            txtMiror = itemView.findViewById(R.id.txt_line_mirror);
        }
    }
    interface OnClickItemListener{
        void onClickItem(int position);
    }

}
