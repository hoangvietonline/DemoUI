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
    private String uriFlip;
    private int currentX = 0;
    private int currentY = 0;
    private String typeMirror = "";
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

        int btmGalleryWidth = bitmap.getWidth();
        int btmGalleryHeight = bitmap.getHeight();
        int scale = Math.min(btmGalleryWidth / (getWidth() / 2), btmGalleryHeight / getHeight());
        Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);

        Log.d(TAG, "onDraw:width " + bitmapScale.getWidth());
        Log.d(TAG, "onDraw:height " + bitmapScale.getHeight());
        Log.d(TAG, "onDraw: bitmap " + bitmap);
        if (bitmap != null) {
            if (typeMirror.equals("M1") || typeMirror.equals("")) {
                @SuppressLint("DrawAllocation") Rect des = new Rect(0, 0, getWidth() / 2, getHeight());
                @SuppressLint("DrawAllocation") Rect des1 = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmapScale, 0, 0, bitmapScale.getWidth(), bitmapScale.getHeight(), matrix, true);
                if (currentX <= bitmapScale.getWidth()) {
                    canvas.drawBitmap(bitmapScale, new Rect((bitmapScale.getWidth() - getWidth() / 2)- currentX, currentY, bitmapScale.getWidth() -currentX, getHeight()), des, null);
                    canvas.drawBitmap(bitmapMatrix, new Rect(currentX, 0, getWidth() / 2 +currentX, getHeight()), des1, null);

                }


//                @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
//                @SuppressLint("DrawAllocation") Rect des = new Rect(0, 0, getWidth() / 2, getHeight());
//                @SuppressLint("DrawAllocation") Rect des1 = new Rect(getWidth() / 2, 0, getWidth(), getHeight());
//                Matrix matrix = new Matrix();
//                matrix.preScale(-1.0f, 1.0f);
//                @SuppressLint("DrawAllocation") Bitmap bitmapMatrix = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//                if (currentX <= bitmap.getWidth()) {
//                    canvas.drawBitmap(bitmapMatrix, new Rect(currentX, currentY, currentX + getWidth() / 2, getHeight()), des, mPaint);
//                    canvas.drawBitmap(bitmap, new Rect(getWidth() / 2 - currentX, currentY, getWidth() - currentX, getHeight()), des1, mPaint);
//                }

                Log.d(TAG, "onDraw: currentX == " + currentX);
            }
            if (typeMirror.equals("M2")) {
                Bitmap bitmapsss = BitmapFactory.decodeResource(getResources(), R.drawable.facebook);
                canvas.drawBitmap(bitmapsss, 0, 0, mPaint);
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
