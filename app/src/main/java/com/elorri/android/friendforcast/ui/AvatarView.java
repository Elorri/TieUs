package com.elorri.android.friendforcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 17/04/2016.
 */
public class AvatarView extends FrameLayout {

    boolean mAsIcon;

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

    public void loadImage(String uri) {
        removeAllViews();
        if (uri == null) {
            inflate(getContext(), R.layout.view_no_avatar, this);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int randomColor = generator.getRandomColor();
            setBackgroundColor(randomColor);
            //FrameLayout avatarBg = (FrameLayout) findViewById(R.id.avatar_bg);
            ImageView avatar = (ImageView) findViewById(R.id.avatarImg);

            Log.e("FF", Thread.currentThread().getStackTrace()[2]
                    + "avatarBg.getLayoutParams().width before "
                    + Tools.convertPixelsToDp(getLayoutParams().width, getContext())
                    + "avatarBg.getLayoutParams().height before"
                    + Tools.convertPixelsToDp(getLayoutParams().height, getContext()));


            int avatarBestWidthHeight =
                    Math.min(getLayoutParams().height, getLayoutParams().width) * 2 / 3;

//            int avatarBestWidthHeight =
//                    Math.min(avatarBg.getLayoutParams().height, avatarBg.getLayoutParams().width) * 2 / 3;
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "avatarBestWidthHeight " + avatarBestWidthHeight);
            avatar.getLayoutParams().height = avatarBestWidthHeight;
            avatar.getLayoutParams().width = avatarBestWidthHeight;
            Log.e("FF", Thread.currentThread().getStackTrace()[2]
                    + "avatar.getLayoutParams().width before "
                    + Tools.convertPixelsToDp(avatar.getLayoutParams().width, getContext())
                    + "avatar.getLayoutParams().height before "
                    + Tools.convertPixelsToDp(avatar.getLayoutParams().height, getContext()));
            //avatar.setLayoutParams(avatar.getLayoutParams());

            Log.e("FF", Thread.currentThread().getStackTrace()[2]
                    + "avatar.getLayoutParams().width "
                    + Tools.convertPixelsToDp(avatar.getLayoutParams().width, getContext())
                    + "avatar.getLayoutParams().height "
                    + Tools.convertPixelsToDp(avatar.getLayoutParams().height, getContext()));
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
