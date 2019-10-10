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
    private int currentX = 0;
    private int currentY = 0;
    private int downX = 0;
    private int upX = 0;
    private int downY = 0;
    private int upY = 0;
    private String typeMirror = "M1";
    private Bitmap bitmap;
    private String typeFrame = "";
    private int newStartX;
    private int startX;
    private int newStartY;
    private int startY;

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

    public int getDownY() {
        return downY;
    }

    public void setDownY(int downY) {
        this.downY = downY;
    }

    public int getUpY() {
        return upY;
    }

    public void setUpY(int upY) {
        this.upY = upY;
        this.startY = newStartY;
        this.downY = currentY;
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
        this.startX = newStartX;
        this.downX = currentX;
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
        startX = 0;
        startY = 0;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(final Canvas canvas) {
        if (bitmap != null) {
            Log.d(TAG, "onDraw:bitmap " + bitmap.toString());
            int btmGalleryWidth = bitmap.getWidth();
            int btmGalleryHeight = bitmap.getHeight();
            int scale = Math.min(btmGalleryWidth / (getWidth() / 2), btmGalleryHeight / getHeight());

            Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
            if (typeMirror.equals("M1")) {
                @SuppressLint("DrawAllocation") Rect desM1L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM1R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);
                int newEndX = initDrawBitmapTouchX(bitmapScale);
                canvas.drawBitmap(bitmapScale, new Rect(newStartX, 0, newEndX, getHeight()), desM1L, null);
                canvas.drawBitmap(bitmapMatrix, new Rect(bitmapMatrix.getWidth() - newEndX, 0, bitmapMatrix.getWidth() - newStartX, getHeight()), desM1R, null);
            }
            if (typeMirror.equals("M2")) {
                @SuppressLint("DrawAllocation") Rect desM2L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM2R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);
                int newEndX = initDrawBitmapTouchX(bitmapScale);
                canvas.drawBitmap(bitmapMatrix, new Rect(newStartX, 0, newEndX, getHeight()), desM2L, null);
                canvas.drawBitmap(bitmapScale, new Rect(bitmapMatrix.getWidth() - newEndX, 0, bitmapMatrix.getWidth() - newStartX, getHeight()), desM2R, null);
            }
            if (typeMirror.equals("M3")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(),(bitmap.getHeight()/(bitmap.getWidth()/getWidth())), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());
                Matrix matrixM3 = new Matrix();
                matrixM3.preScale(1.0f, -1.0f);
                Bitmap bitmapFlipM3 = Bitmap.createBitmap(bitmapScaleM3, 0, 0, bitmapScaleM3.getWidth(), bitmapScaleM3.getHeight(), matrixM3, true);
                int newEndY = initDrawBitmapTouchY(bitmapScaleM3);
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, newStartY, getWidth(), newEndY), desM3T, null);
                canvas.drawBitmap(bitmapFlipM3, new Rect(0, bitmapFlipM3.getHeight() - newEndY, getWidth(), bitmapFlipM3.getHeight() - newStartY), desM3B, null);
            }
            if (typeMirror.equals("M4")) {
                Bitmap bitmapScaleM4 = Bitmap.createScaledBitmap(bitmap, getWidth(),(bitmap.getHeight()/(bitmap.getWidth()/getWidth())), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());
                Matrix matrixM4 = new Matrix();
                matrixM4.preScale(1.0f, -1.0f);
                Bitmap bitmapFlipM4 = Bitmap.createBitmap(bitmapScaleM4, 0, 0, bitmapScaleM4.getWidth(), bitmapScaleM4.getHeight(), matrixM4, true);
                int newEndY = initDrawBitmapTouchY(bitmapScaleM4);
                canvas.drawBitmap(bitmapFlipM4, new Rect(0, newStartY, getWidth(), newEndY), desM3T, null);
                canvas.drawBitmap(bitmapScaleM4, new Rect(0, bitmapFlipM4.getHeight() - newEndY, getWidth(), bitmapFlipM4.getHeight() - newStartY), desM3B, null);
            }
            if (typeMirror.equals("M5")) {
                @SuppressLint("DrawAllocation") Rect desM5L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM5R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                int delta = downX - currentX;

                newStartX = startX + delta;

                int newEndX = newStartX + getWidth() / 2;
                if (newEndX >= bitmapScale.getWidth()) {
                    newEndX = bitmapScale.getWidth();
                    newStartX = newEndX - getWidth() / 2;
                } else if (newStartX < 0) {
                    newStartX = 0;
                    newEndX = newStartX + getWidth() / 2;
                }

                canvas.drawBitmap(bitmapScale, new Rect(newStartX, 0, newEndX, getHeight()), desM5L, null);
                canvas.drawBitmap(bitmapScale, new Rect(newStartX, 0, newEndX, getHeight()), desM5R, null);
            }
            if (typeMirror.equals("M6")) {
                Bitmap bitmapScaleM6 = Bitmap.createScaledBitmap(bitmap, getWidth(),(bitmap.getHeight()/(bitmap.getWidth()/getWidth())), true);
                Rect desM6T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM6B = new Rect(0, getHeight() / 2, getWidth(), getHeight());
                int delta = downY - currentY;
                newStartY = startY + delta;
                int newEndY = newStartY + getHeight() / 2;
                if (newEndY >= bitmapScaleM6.getHeight()) {
                    newEndY = bitmapScaleM6.getHeight();
                    newStartY = newEndY - getHeight() / 2;
                } else if (newStartY < 0) {
                    newStartY = 0;
                    newEndY = newStartY + getHeight() / 2;
                }
                canvas.drawBitmap(bitmapScaleM6, new Rect(0, newStartY, getWidth(), newEndY), desM6T, null);
                canvas.drawBitmap(bitmapScaleM6, new Rect(0, newStartY, getWidth(), newEndY), desM6B, null);

            }
            if (typeMirror.equals("M7")) {
                @SuppressLint("DrawAllocation") Rect desM7L = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect desM7R = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, -1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);
                int newEndX = initDrawBitmapTouchX(bitmapScale);
                canvas.drawBitmap(bitmapScale, new Rect(newStartX, 0, newEndX, getHeight()), desM7L, null);
                canvas.drawBitmap(bitmapMatrix, new Rect(bitmapMatrix.getWidth() - newEndX, 0, bitmapMatrix.getWidth() - newStartX, getHeight()), desM7R, null);
            }
            if (typeMirror.equals("M8")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(),(bitmap.getHeight()/(bitmap.getWidth()/getWidth())), true);
                Rect desM8T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM8B = new Rect(0, getHeight() / 2, getWidth(), getHeight());
                Matrix matrixM3 = new Matrix();
                matrixM3.preScale(-1.0f, -1.0f);
                Bitmap bitmapFlipM8 = Bitmap.createBitmap(bitmapScaleM3, 0, 0, bitmapScaleM3.getWidth(), bitmapScaleM3.getHeight(), matrixM3, true);
                int newEndY = initDrawBitmapTouchY(bitmapScaleM3);
                canvas.drawBitmap(bitmapScaleM3, new Rect(0, newStartY, getWidth(), newEndY), desM8T, null);
                canvas.drawBitmap(bitmapFlipM8, new Rect(0, bitmapFlipM8.getHeight() - newEndY, getWidth(), bitmapFlipM8.getHeight() - newStartY), desM8B, null);
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
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_1);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            if (typeFrame.equals("F5")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_2);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            if (typeFrame.equals("F6")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_4);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
            if (typeFrame.equals("F7")) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frame_7);
                Bitmap bitscaleFrame = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
                canvas.drawBitmap(bitscaleFrame, 0, 0, mPaint);
            }
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

    public void setBitmapFlip(Bitmap bitmapFlip) {
        bitmap = bitmapFlip;
        invalidate();
    }


    private int initDrawBitmapTouchX(Bitmap bitmapScale) {
        int newEndX;
        int delta = downX - currentX;
        if (downX > getWidth() / 2)
            delta = -delta;
        newStartX = startX + delta;

        newEndX = newStartX + getWidth() / 2;
        if (newEndX >= bitmapScale.getWidth()) {
            newEndX = bitmapScale.getWidth();
            newStartX = newEndX - getWidth() / 2;
        } else if (newStartX < 0) {
            newStartX = 0;
            newEndX = newStartX + getWidth() / 2;
        }
        return newEndX;
    }

    private int initDrawBitmapTouchY(Bitmap bitmapScale) {
        int newEndY;
        int delta = downY - currentY;
        if (downY > getHeight() / 2)
            delta = -delta;

        newStartY = startY + delta;
        newEndY = newStartY + getHeight() / 2;
        if (newEndY >= bitmapScale.getHeight()) {
            newEndY = bitmapScale.getHeight();
            newStartY = newEndY - getHeight() / 2;
        } else if (newStartY < 0) {
            newStartY = 0;
            newEndY = newStartY + getHeight() / 2;
        }
        return newEndY;
    }
}
