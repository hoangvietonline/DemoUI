package hoangviet.ndhv.demoui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
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
    private String uriFlip;
    private int currentX = 0;
    private int currentY = 0;
    private String typeMirror = "M1";
    private Bitmap bitmap;

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
//        if (typeImageMirror.equals("Camera")) {
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(uriFlip, options);
//            Log.d(TAG, "onDraw:option.outWidth : " + options.outWidth);
//            Log.d(TAG, "onDraw:option.outHeight : " + options.outHeight);
//            int photoWidth = options.outWidth;
//            int photoHeight = options.outHeight;
//            int sampleSize = Math.min(photoWidth / (getWidth() / 2), photoHeight / getHeight());
//            Log.d(TAG, "onDraw:sampleSize " + sampleSize);
//            options.inJustDecodeBounds = false;
//            options.inSampleSize = sampleSize;
//            bitmap = BitmapFactory.decodeFile(uriFlip, options);
//            try {
//                ExifInterface exifInterface = new ExifInterface(uriFlip);
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(1));
//                exifInterface.saveAttributes();
//                String exif = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
//                Log.d(TAG, "onDraw:exifBitmap " + exif);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Log.d(TAG, "onDraw: get by count " + bitmap.getByteCount());
//            Log.d(TAG, "onDraw:ViewWidth : " + getWidth() / 2);
//            Log.d(TAG, "onDraw:ViewHeight : " + getHeight());
//            Log.d(TAG, "onDraw:bitmapWidth : " + bitmap.getWidth());
//            Log.d(TAG, "onDraw:bitmapHeight : " + bitmap.getHeight());
//
//        } else {
//            try {
//                InputStream inputStream = getContext().getContentResolver().openInputStream(Uri.parse(uriFlip));
//                Bitmap bitmapGallery = BitmapFactory.decodeStream(inputStream);
//                int btmGalleryWidth = bitmapGallery.getWidth();
//                int btmGalleryHeight = bitmapGallery.getHeight();
//                int scale = Math.min(btmGalleryWidth / (getWidth() / 2), btmGalleryHeight / getHeight());
//                Log.d(TAG, "onDraw:scale : " + scale);
//
//                bitmap = Bitmap.createScaledBitmap(bitmapGallery, (bitmapGallery.getWidth() / scale), (bitmapGallery.getHeight() / scale), true);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


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
                canvas.drawBitmap(bitmapM3, new Rect(0, 0 + currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
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

                int leftM1L = (bitmapScale.getWidth() - getWidth() / 2);
                int rightM1L = bitmapScale.getWidth();

                canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1L, null);
                canvas.drawBitmap(bitmapScale, new Rect(currentX, 0, getWidth() / 2 + currentX, getHeight()), desM1R, null);

                Log.d(TAG, "onDraw: currentX == " + currentX);
            }
            if (typeMirror.equals("M6")) {
                Bitmap bitmapScaleM3 = Bitmap.createScaledBitmap(bitmap, getWidth(), (bitmap.getHeight() / 2), true);
                Rect desM3T = new Rect(0, 0, getWidth(), getHeight() / 2);
                Rect desM3B = new Rect(0, getHeight() / 2, getWidth(), getHeight());

                int topM3T = bitmapScaleM3.getHeight() - getHeight() / 2;
                int bottomM3T = bitmapScaleM3.getHeight();
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
                canvas.drawBitmap(bitmapM3, new Rect(0, 0 + currentY, getWidth(), getHeight() / 2 + currentY), desM3B, null);
            }
        }
    }

    public void setTypeMirror(String type) {
        typeMirror = type;
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
