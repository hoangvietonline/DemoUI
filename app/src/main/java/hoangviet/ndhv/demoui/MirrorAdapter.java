package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hoangviet.ndhv.demoui.model.Mirror;

public class MirrorAdapter extends RecyclerView.Adapter<MirrorAdapter.MirrorViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Mirror> mirrorList;
    private OnClickItemListener onClickItemListener;

    MirrorAdapter(OnClickItemListener onClickItemListener, Context mContext, List<Mirror> mirrorList) {
        this.mContext = mContext;
        this.mirrorList = mirrorList;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MirrorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_mirror, viewGroup, false);
        return new MirrorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MirrorViewHolder mirrorViewHolder, @SuppressLint("RecyclerView") final int i) {
        final Mirror mirror = mirrorList.get(i);
        mirrorViewHolder.txtMiror.setText(mirror.getTxtMirror());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 6;
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bridge_san, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmapScale = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bridge_san,options);

        Bitmap bitmap = FlipHorizontally(bitmapScale,i);
        mirrorViewHolder.imgMirror.setImageBitmap(bitmap);
        mirrorViewHolder.imgMirror.setScaleType(ImageView.ScaleType.FIT_XY);
        if (mirror.isClick()) {
            mirrorViewHolder.itemView.setBackgroundColor(Color.BLUE);
        } else {
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

    private Bitmap FlipHorizontally(Bitmap originalImage, int position) {
        // The gap we want between the flipped image and the original image
        Bitmap bitmapWithFlip = null;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        if (position == 0){
            Matrix matrix = new Matrix();
            matrix.preScale(-1, 1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(flipImage, width, 0, null);
        }
        if (position == 1){
            Matrix matrix = new Matrix();
            matrix.preScale(-1, 1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(flipImage, 0, 0, null);
            canvas.drawBitmap(originalImage, width, 0, null);
        }
        if (position == 2){
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap((width), height+height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(flipImage, 0, height, null);
        }
        if (position == 3){
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap(width, height + height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(flipImage, 0, 0, null);
            canvas.drawBitmap(originalImage, 0, height, null);
        }
        if (position == 4){
            bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(originalImage, width, 0, null);
        }
        if (position == 5){
            bitmapWithFlip = Bitmap.createBitmap((width), height+height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(originalImage, 0, height, null);
        }
        if (position ==6){
            Matrix matrix = new Matrix();
            matrix.preScale(-1, -1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(flipImage, width, 0, null);
        }
        if (position ==7){
            Matrix matrix = new Matrix();
            matrix.preScale(-1, -1);
            Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);
            bitmapWithFlip = Bitmap.createBitmap((width), height+height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithFlip);
            canvas.drawBitmap(originalImage, 0, 0, null);
            canvas.drawBitmap(flipImage, 0, height, null);
        }

        return bitmapWithFlip;
    }

    interface OnClickItemListener {
        void onClickItem(int position);
    }

    class MirrorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMirror;
        private TextView txtMiror;

        MirrorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMirror = itemView.findViewById(R.id.img_line_mirror);
            txtMiror = itemView.findViewById(R.id.txt_line_mirror);
        }
    }
}
