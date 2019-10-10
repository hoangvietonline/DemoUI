package hoangviet.ndhv.demoui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemShareAdapter extends RecyclerView.Adapter<ItemShareAdapter.ItemViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ItemShare> itemShareList;
    private OnClickItemShareListener onClickItemShareListener;

    public ItemShareAdapter(OnClickItemShareListener onClickItemShareListener,Context mContext, List<ItemShare> itemShareList) {
        this.mContext = mContext;
        this.itemShareList = itemShareList;
        this.inflater = LayoutInflater.from(mContext);
        this.onClickItemShareListener = onClickItemShareListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.items_share, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i) {
        ItemShare itemShare = itemShareList.get(i);
        itemViewHolder.imgShare.setImageResource(itemShare.getImageShare());
        itemViewHolder.txtTittleShare.setText(itemShare.getTittleShare());
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemShareListener.onclickItemShare(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemShareList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgShare;
        private TextView txtTittleShare;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShare = itemView.findViewById(R.id.img_item_share);
            txtTittleShare = itemView.findViewById(R.id.txt_item_tittle_share);
        }
    }
    interface OnClickItemShareListener{
        void onclickItemShare(int position);
    }
}
