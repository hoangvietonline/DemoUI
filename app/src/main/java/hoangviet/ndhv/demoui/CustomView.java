package hoangviet.ndhv.demoui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class CustomView extends View {
    private static final String TAG = "CustomView";
    private Paint mPaint;
    private String uriFlip;

    public CustomView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attributeSet) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        Bitmap bitmap = null;
        Glide.with(this)
                .asBitmap()
                .load(uriFlip)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady:bitmap " + resource);
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        Matrix matrix = new Matrix();
                        matrix.preScale(-1.0f, 1.0f);
                        Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, 100, 100, matrix, true);
                        Bitmap bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
                        canvas.drawBitmap(bitmap, 0, 0, mPaint);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    public void setBitmapFlip(String uri) {
        uriFlip = uri;
        Log.d(TAG, "setBitmapFlip: uriFlip: " + uriFlip);
    }
}
