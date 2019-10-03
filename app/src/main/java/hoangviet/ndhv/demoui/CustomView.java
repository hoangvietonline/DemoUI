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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class CustomView extends View {
    private static final String TAG = "CustomView";
    private Paint mPaint;
    private int currentX = 0;
    private int currentY = 0;
    private int downX = 0;
    private int upX = 0;
    private String typeMirror = "M1";
    private Bitmap bitmap;
    private String typeFrame = "";
    private int leng = 0;
    private int left = 0;
    private int right =0;

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

    public int getDownX() {
        return downX;
    }

    public void setDownX(int downX) {
        this.downX = downX;
    }

    public int getUpX() {
        return upX;
    }

    public void setUpX(int upX) {
        this.upX = upX;
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
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(final Canvas canvas) {
        leng = upX - downX;
        if (bitmap != null) {
            int btmGalleryWidth = bitmap.getWidth();
            int btmGalleryHeight = bitmap.getHeight();
            int scale = Math.min(btmGalleryWidth / (getWidth() / 2), btmGalleryHeight / getHeight());

            Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);

            Log.d(TAG, "onDraw:width " + bitmapScale.getWidth());
            Log.d(TAG, "onDraw:height " + bitmapScale.getHeight());
            Log.d(TAG, "onDraw: bitmap " + bitmap);
            if (typeMirror.equals("M1")) {
                @SuppressLint("DrawAllocation") Rect desM1L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM1R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);

                int leftM1L = (bitmapScale.getWidth() - getWidth() / 2);
                int rightM1L = bitmapScale.getWidth();
                if (currentX <= getWidth()) {
//                    if ((leftM1L - currentX) >= 0 && rightM1L <= bitmapScale.getWidth()) {
                    left += (leftM1L + leng);
                    right += rightM1L + leng;
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L, 0, rightM1L, getHeight()), desM1L, null);
//                        canvas.drawBitmap(bitmapMatrix, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);
//                    } else if ((leftM1L - currentX) < 0) {
//                        currentX = bitmapScale.getWidth() - getWidth() / 2;
//                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentX, 0, rightM1L - currentX, getHeight()), desM1L, null);
//                        canvas.drawBitmap(bitmapMatrix, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);
//                    }
//                } else if (currentX > getWidth() / 2 && currentX <= getWidth()) {
//                    int currentXR = getWidth() - currentX;
//                    int leftM1R = 0;
//                    int rightM1R = getWidth() / 2;
//                    if (rightM1R + currentXR <= bitmapScale.getWidth()) {
//                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM1L, null);
//                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM1R, null);
//                    } else if (rightM1R + currentXR > bitmapScale.getWidth()) {
//                        currentXR = bitmapScale.getWidth() - getWidth() / 2;
//                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM1L, null);
//                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM1R, null);
//                    }

                }
//                Log.d(TAG, "onDraw: currentX == " + currentX);
            }
            if (typeMirror.equals("M2")) {
                @SuppressLint("DrawAllocation") Rect desM2L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM2R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);

                int leftM1L = (bitmapScale.getWidth() - getWidth() / 2);
                int rightM1L = bitmapScale.getWidth();
                if (currentX <= getWidth() / 2) {
                    if ((leftM1L - currentX) >= 0 && rightM1L <= bitmapScale.getWidth()) {
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1L - currentX, 0, rightM1L - currentX, getHeight()), desM2L, null);
                        canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM2R, null);
                    } else if ((leftM1L - currentX) < 0) {
                        currentX = bitmapScale.getWidth() - getWidth() / 2;
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1L - currentX, 0, rightM1L - currentX, getHeight()), desM2L, null);
                        canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM2R, null);
                    }
                } else if (currentX > getWidth() / 2 && currentX <= getWidth()) {
                    int currentXR = getWidth() - currentX;
                    int leftM1R = 0;
                    int rightM1R = getWidth() / 2;
                    if (rightM1R + currentXR <= bitmapScale.getWidth()) {
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM2L, null);
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM2R, null);
                    } else if (rightM1R + currentXR > bitmapScale.getWidth()) {
                        currentXR = bitmapScale.getWidth() - getWidth() / 2;
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM2L, null);
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM2R, null);
                    }
                }
            }
            if (typeMirror.equals("M3")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(), (bitmap.getHeight() / 2), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());

                Matrix matrixM3 = new Matrix();
                matrixM3.preScale(1.0f, -1.0f);

                Bitmap bitmapM3 = Bitmap.createBitmap(bitmapScaleM3, 0, 0, bitmapScaleM3.getWidth(), bitmapScaleM3.getHeight(), matrixM3, true);
                int topM3T = bitmapScaleM3.getHeight() - getHeight() / 2;
                int bottomM3T = bitmapScaleM3.getHeight();
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, topM3T - currentY, getWidth(), bottomM3T - currentY), desM3T, null);
                canvas.drawBitmap(bitmapM3, new Rect(0, currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
            }
            if (typeMirror.equals("M4")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(), (bitmap.getHeight() / 2), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());

                Matrix matrixM3 = new Matrix();
                matrixM3.preScale(1.0f, -1.0f);

                Bitmap bitmapM3 = Bitmap.createBitmap(bitmapScaleM3, 0, 0, bitmapScaleM3.getWidth(), bitmapScaleM3.getHeight(), matrixM3, true);
                int topM3T = bitmapScaleM3.getHeight() - getHeight() / 2;
                int bottomM3T = bitmapScaleM3.getHeight();
                canvas.drawBitmap(bitmapM3, new Rect(0, topM3T - currentY, getWidth(), bottomM3T - currentY), desM3T, null);
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
            }
            if (typeMirror.equals("M5")) {
                @SuppressLint("DrawAllocation") Rect desM1L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM1R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());


                canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1L, null);
                canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);

                Log.d(TAG, "onDraw: currentX == " + currentX);
            }
            if (typeMirror.equals("M6")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(), (bitmap.getHeight() / 2), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, currentY, getWidth(), getHeight() / 2 + currentY), desM3T, null);
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
            }
            if (typeMirror.equals("M7")) {
                @SuppressLint("DrawAllocation") Rect desM1L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM1R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, -1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);

                int leftM1L = (bitmapScale.getWidth() - getWidth() / 2);
                int rightM1L = bitmapScale.getWidth();
                if (currentX <= getWidth() / 2) {
                    if ((leftM1L - currentX) >= 0 && rightM1L <= bitmapScale.getWidth()) {
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentX, 0, rightM1L - currentX, getHeight()), desM1L, null);
                        canvas.drawBitmap(bitmapMatrix, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);
                    } else if ((leftM1L - currentX) < 0) {
                        currentX = bitmapScale.getWidth() - getWidth() / 2;
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentX, 0, rightM1L - currentX, getHeight()), desM1L, null);
                        canvas.drawBitmap(bitmapMatrix, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);
                    }
                } else if (currentX > getWidth() / 2 && currentX <= getWidth()) {
                    int currentXR = getWidth() - currentX;
                    int leftM1R = 0;
                    int rightM1R = getWidth() / 2;
                    if (rightM1R + currentXR <= bitmapScale.getWidth()) {
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM1L, null);
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM1R, null);
                    } else if (rightM1R + currentXR > bitmapScale.getWidth()) {
                        currentXR = bitmapScale.getWidth() - getWidth() / 2;
                        canvas.drawBitmap(bitmapScale, new Rect(leftM1L - currentXR, 0, rightM1L - currentXR, getHeight()), desM1L, null);
                        canvas.drawBitmap(bitmapMatrix, new Rect(leftM1R + currentXR, 0, rightM1R + currentXR, getHeight()), desM1R, null);
                    }
                }
                Log.d(TAG, "onDraw: currentX == " + currentX);
            }
            if (typeMirror.equals("M8")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(), (bitmap.getHeight() / 2), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());

                Matrix matrixM3 = new Matrix();
                matrixM3.preScale(-1.0f, -1.0f);

                Bitmap bitmapM3 = Bitmap.createBitmap(bitmapScaleM3, 0, 0, bitmapScaleM3.getWidth(), bitmapScaleM3.getHeight(), matrixM3, true);
                int topM3T = bitmapScaleM3.getHeight() - getHeight() / 2;
                int bottomM3T = bitmapScaleM3.getHeight();
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, topM3T - currentY, getWidth(), bottomM3T - currentY), desM3T, null);
                canvas.drawBitmap(bitmapM3, new Rect(0, currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
            }

            if (typeFrame.equals("F2")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_image);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            if (typeFrame.equals("F3")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flow_frame);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            if (typeFrame.equals("F4")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_1);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            Log.d(TAG, "onDraw:type " + typeFrame);
        }
    }

    public void setTypeMirror(String type) {
        typeMirror = type;
        invalidate();
    }

    public void setTypeFrame(String type) {
        typeFrame = type;
        invalidate();
    }

    public void setBitmapFlip(String uri) {
        Log.d(TAG, "setBitmapFlip: uriFlip: " + uri);
        Glide.with(getContext())
                .asBitmap()
                .load(uri)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        bitmap = resource;
                        Log.d(TAG, "onResourceReady:resource " + resource);
                        invalidate();
                        return true;
                    }
                }).submit();
    }

}
