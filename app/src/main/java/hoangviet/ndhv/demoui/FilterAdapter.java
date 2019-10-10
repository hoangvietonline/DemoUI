package hoangviet.ndhv.demoui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.wysaid.nativePort.CGENativeLibrary;

import java.util.List;

import hoangviet.ndhv.demoui.model.FilterData;

import static android.support.constraint.Constraints.TAG;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FilterData> filterDataList;
    private int currentPosition = 0;
    private onCLickItemFilterLitener onCLickItemFilterLitener;

    public FilterAdapter(onCLickItemFilterLitener onCLickItemFilterLitener, Context mContext, List<FilterData> filterDataList) {
        this.onCLickItemFilterLitener = onCLickItemFilterLitener;
        this.mContext = mContext;
        this.filterDataList = filterDataList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_filter, viewGroup, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterViewHolder filterViewHolder, final int i) {
        final FilterData filterData = filterDataList.get(i);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 6;
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.image_backgoung, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), filterData.getFilterId(),options);
        bitmap = CGENativeLibrary.filterImage_MultipleEffects(bitmap, FilterFragment.EFFECT_CONFIGS[i], 1.0f);
        Log.d(TAG, "onBindViewHolder:bitmap byte " + bitmap.getByteCount());
        filterViewHolder.txtFilter.setText(filterData.getFilterName());
        filterViewHolder.imgFilter.setImageBitmap(bitmap);
        filterViewHolder.imgFilter.setScaleType(ImageView.ScaleType.CENTER_CROP);

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

    }

    @Override
    public int getItemCount() {
        return filterDataList.size();
    }

    interface onCLickItemFilterLitener {
        void clickItem(int position);
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
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
