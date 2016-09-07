package com.example.santa.redhare.connfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by santa on 16/8/3.
 */
public class CircleImageView extends ImageView {
    private Path path = new Path();
    private Paint paint = new Paint();
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);


    private Paint mPaint = new Paint();;
    private Bitmap mMaskBitmap;


    /**
     * 图片的类型，圆形or圆角
     */
    private int type = TYPE_CIRCLE;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        float radius = getWidth()/2;
//        float padding = radius / 2;
//        int w = this.getWidth();
//        int h = this.getHeight();
//        path.addCircle(getWidth()/2, getHeight()/2, radius, Path.Direction.CW);
//        canvas.clipPath(path);
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG| Paint.ANTI_ALIAS_FLAG));
//        //切割要放在draw上面
//        super.onDraw(canvas);
//    }


//
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        mPaint.setAntiAlias(true);

        //拿到Drawable
        Drawable drawable = getDrawable();

        if (null == drawable) {
            return;
        }

        //获取drawable的宽和高
        int dWidth = drawable.getIntrinsicWidth();
        int dHeight = drawable.getIntrinsicHeight();

        if (drawable != null) {
            //创建bitmap
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            float scale = 1.0f;
            //创建画布
            Canvas drawCanvas = new Canvas(bitmap);
            //按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
            if (type == TYPE_ROUND) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
            } else {
                scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
            }
            //根据缩放比例，设置bounds，相当于缩放图片了
            drawable.setBounds(0, 0, (int) (scale * dWidth), (int) (scale * dHeight));
            drawable.draw(drawCanvas);
            if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                mMaskBitmap = getBitmap();
            }
            // Draw Bitmap.
            mPaint.reset();
            mPaint.setFilterBitmap(false);
            mPaint.setXfermode(mXfermode);
            //绘制形状
            drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
            //将准备好的bitmap绘制出来
            canvas.drawBitmap(bitmap, 0, 0, null);

        }
    }

        public Bitmap getBitmap()
        {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);

            if (type == TYPE_ROUND)
            {
                canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                        mBorderRadius, mBorderRadius, paint);
            } else
            {
                canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                        paint);
            }

            return bitmap;
        }
    }
