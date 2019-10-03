package hoangviet.ndhv.demoui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hoangviet.ndhv.demoui.model.FilterData;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FilterData> filterDataList;
    private int currentPosition =0;

    public FilterAdapter(Context mContext, List<FilterData> filterDataList) {
        this.mContext = mContext;
        this.filterDataList = filterDataList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_filter,viewGroup,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterViewHolder filterViewHolder, final int i) {
        final FilterData filterData = filterDataList.get(i);
        filterViewHolder.txtFilter.setText(filterData.getFilterName());
        filterViewHolder.imgFilter.setImageResource(filterData.getFilterId());
        filterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = i;
            }
        });
        if (currentPosition == i){
            filterViewHolder.constraintLayout.setBackgroundColor(R.drawable.bg_item_filter_selected);
        }else {
            filterViewHolder.constraintLayout.setBackgroundColor(R.drawable.bg_item_filter_unselected);
        }
    }

    @Override
    public int getItemCount() {
        return filterDataList.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFilter;
        private TextView txtFilter;
        private ConstraintLayout constraintLayout;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.img_item_filter);
            txtFilter = itemView.findViewById(R.id.txt_item_filter);
            constraintLayout = itemView.findViewById(R.id.constrainLayoutFilter);
        }
    }
}
