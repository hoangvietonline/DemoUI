package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View {
    private static final String TAG = "CustomView";
    private Paint mPaint;
    private String uriFlip;
    private int currentX = 0;
    private int currentY = 0;

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

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void init(Context context, AttributeSet attributeSet) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(final Canvas canvas) {

        @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        @SuppressLint("DrawAllocation") Rect des = new Rect(0, 0, getWidth() / 2, getHeight());
        @SuppressLint("DrawAllocation") Rect des1 = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (currentX <= bitmap.getWidth()) {
            canvas.drawBitmap(bitmapMatrix, new Rect(currentX, currentY, currentX + getWidth() / 2, getHeight()), des, mPaint);
            canvas.drawBitmap(bitmap, new Rect(getWidth() / 2 - currentX, currentY, getWidth() - currentX, getHeight()), des1, mPaint);
        }

        Log.d(TAG, "onDraw: currentx == " + currentX);
//        Bitmap bitmap = null;
//        Glide.with(this)
//                .asBitmap()
//                .load(uriFlip)
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Log.d(TAG, "onResourceReady:bitmap " + resource);
//                        int width = resource.getWidth();
//                        int height = resource.getHeight();
//                        Matrix matrix = new Matrix();
//                        matrix.preScale(-1.0f, 1.0f);
//                        Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, 100, 100, matrix, true);
//                        Bitmap bitmapWithFlip = Bitmap.createBitmap((width + width), height, Bitmap.Config.ARGB_8888);
//                        canvas.drawBitmap(bitmap, 0, 0, mPaint);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//                    }
//                });

    }


    public void setBitmapFlip(String uri) {
        uriFlip = uri;
        Log.d(TAG, "setBitmapFlip: uriFlip: " + uriFlip);
    }
}
