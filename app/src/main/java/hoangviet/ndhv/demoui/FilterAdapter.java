package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import hoangviet.ndhv.demoui.model.FilterData;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FilterData> filterDataList;
    private onCLickItemFilterLitener onCLickItemFilterLitener;

    FilterAdapter(onCLickItemFilterLitener onCLickItemFilterLitener, Context mContext, List<FilterData> filterDataList) {
        this.onCLickItemFilterLitener = onCLickItemFilterLitener;
        this.mContext = mContext;
        this.filterDataList = filterDataList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_filter, viewGroup, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterViewHolder filterViewHolder, @SuppressLint("RecyclerView") final int i) {

        final FilterData filterData = filterDataList.get(i);
        if (i < FilterFragment.FILTER_NAME.length) {
            filterViewHolder.imgDownloadFilter.setVisibility(View.GONE);
            filterViewHolder.imgFilter.setImageBitmap(filterData.getThumbBitmap());
        } else {
            ContextWrapper cw = new ContextWrapper(Objects.requireNonNull(mContext.getApplicationContext()));
            File directory = cw.getDir("filterDir", Context.MODE_PRIVATE);
            File file = new File(directory, filterData.getFilterName() + ".png");
            if (!file.exists()) {
                filterViewHolder.imgDownloadFilter.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(filterData.getFilterThumb()).into(filterViewHolder.imgFilter);
            } else {
                AssetManager assetManager = mContext.getAssets();
                try {
                    InputStream inputStream = assetManager.open("img_filter.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
                    Bitmap bitmapScaleFilter = CGENativeLibrary.filterImage_MultipleEffects(bitmap, filterDataList.get(i).getRule(), 1.0f);
                    filterData.setFilterThumb(null);
                    filterData.setThumbBitmap(bitmapScaleFilter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filterViewHolder.imgDownloadFilter.setVisibility(View.GONE);
                filterViewHolder.imgFilter.setImageBitmap(filterData.getThumbBitmap());
            }
        }
        if (filterData.isChooseFilter()) {
            filterViewHolder.constraintLayout.setBackgroundResource(R.drawable.bg_item_filter_selected);
        } else {
            filterViewHolder.constraintLayout.setBackgroundResource(R.drawable.bg_item_filter_unselected);
        }
        filterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickItemFilterLitener.clickItem(i);
            }
        });
        filterViewHolder.txtFilter.setText(filterData.getFilterName());
        filterViewHolder.imgFilter.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return filterDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    interface onCLickItemFilterLitener {
        void clickItem(int position);
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFilter;
        private TextView txtFilter;
        private ConstraintLayout constraintLayout;
        private ImageView imgDownloadFilter;

        FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.img_item_filter);
            txtFilter = itemView.findViewById(R.id.txt_item_filter);
            constraintLayout = itemView.findViewById(R.id.constrainLayoutFilter);
            imgDownloadFilter = itemView.findViewById(R.id.imgDownloadFilter);
        }
    }

}
