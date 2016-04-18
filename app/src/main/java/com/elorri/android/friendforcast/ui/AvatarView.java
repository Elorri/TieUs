package com.elorri.android.friendforcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.elorri.android.friendforcast.R;

/**
 * Created by Elorri on 17/04/2016.
 */
public class AvatarView extends FrameLayout {

    public static final String RANDOM_COLOR = "color";
    //    boolean mAsIcon;
    private int mRandomColor;

    public AvatarView(Context context) {
        super(context);
    }


    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context, attrs);
    }

//    private void init(Context context, AttributeSet attrs) {
//        int[] attributes = R.styleable.AvatarViewAttributes;
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, attributes, 0, 0);
//        try {
//            mAsIcon = a.getBoolean(R.styleable.AvatarViewAttributes_asIcon, false);
//        } finally {
//            a.recycle();
//        }
//    }

    //TODO improve this comment

    /**
     * @param uri
     * @param color 0 if we don't know the color we want
     */
    public void loadImage(String uri, int color) {
        if (color == 0) {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            mRandomColor = generator.getRandomColor();
        } else {
            mRandomColor = color;
        }

        Log.e("Color", Thread.currentThread().getStackTrace()[2]+""+mRandomColor);

        removeAllViews();
        if (uri == null) {
            inflate(getContext(), R.layout.view_no_avatar, this);

            setBackgroundColor(mRandomColor);
            //FrameLayout avatarBg = (FrameLayout) findViewById(R.id.avatar_bg);
            ImageView avatar = (ImageView) findViewById(R.id.avatarImg);

            int avatarBestWidthHeight =
                    Math.min(getLayoutParams().height, getLayoutParams().width) * 2 / 3;

            avatar.getLayoutParams().height = avatarBestWidthHeight;
            avatar.getLayoutParams().width = avatarBestWidthHeight;
            //avatar.setLayoutParams(avatar.getLayoutParams());
            // requestLayout();

        } else {
            inflate(getContext(), R.layout.view_avatar, this);
            ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
            Glide.with(getContext())
                    .load(uri)
                    .crossFade()
                    .into(thumbnail);
        }


    }


    /*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth;
        int measuredHeight;
        if (mAsIcon) {
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mAsIcon " + mAsIcon);
            measuredWidth = this.getLayoutParams().width = 100;
            measuredHeight = this.getLayoutParams().height = 100;
            this.setLayoutParams(this.getLayoutParams());

        } else {

        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }*/
}
