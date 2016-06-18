/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.tieus.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 17/04/2016.
 * Custom view that will load the user contact thumbnail if there is one or a person drawing
 * instead.
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
