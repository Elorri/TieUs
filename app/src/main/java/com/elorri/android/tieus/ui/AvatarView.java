package com.elorri.android.tieus.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 17/04/2016.
 */
public class AvatarView extends FrameLayout {


    public AvatarView(Context context) {
        super(context);
    }


    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param uri
     * @param color 0 if we don't know the color we want
     */
    public void loadImage(String uri, int color) {
        removeAllViews();
        if (uri == null) {
            inflate(getContext(), R.layout.view_no_avatar, this);

            //need this line only because in my test the - sign is mistakely removed.
            color = color < 0 ? color : color * -1;
            setBackgroundColor(color);

            //FrameLayout avatarBg = (FrameLayout) findViewById(R.id.avatar_bg);
            ImageView avatar = (ImageView) findViewById(R.id.avatarImg);

            int avatarBestWidthHeight =
                    Math.min(getLayoutParams().height, getLayoutParams().width) * 2 / 3;

            avatar.getLayoutParams().height = avatarBestWidthHeight;
            avatar.getLayoutParams().width = avatarBestWidthHeight;
        } else {
            inflate(getContext(), R.layout.view_avatar, this);
            ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
            Glide.with(getContext())
                    .load(uri)
                    .crossFade()
                    .into(thumbnail);
        }


    }
}
