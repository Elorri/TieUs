package com.elorri.android.friendforcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.elorri.android.friendforcast.R;

/**
 * Created by Elorri on 17/04/2016.
 */
public class AvatarView extends FrameLayout {

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadImage(String uri) {
        if (uri == null) {
            inflate(getContext(), R.layout.view_no_avatar, this);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int randomColor = generator.getRandomColor();
            setBackgroundColor(randomColor);
        }
        else {
            inflate(getContext(), R.layout.view_avatar, this);
            ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
            Glide.with(getContext())
                    .load(uri)
                    .crossFade()
                    .into(thumbnail);
        }


    }
}
