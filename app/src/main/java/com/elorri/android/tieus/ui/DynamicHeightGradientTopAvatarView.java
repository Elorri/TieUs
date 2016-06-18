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

import com.elorri.android.tieus.R;


/**
 * Created by Elorri on 13/03/2016.
 * Custom view that dynamically calculate the height of an avatar view base on the total height
 * of the device
 */
public class DynamicHeightGradientTopAvatarView extends FrameLayout implements GradientTopAvatarView{

    private float mAspectRatio=1.5f;
    private AvatarView mThumbnailView;

    public DynamicHeightGradientTopAvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.top_gradient_imageview, this);
        FrameLayout thumbnailContainer = (FrameLayout) findViewById(R.id.thumbnail_container);
        mThumbnailView = new AvatarView(getContext());
        thumbnailContainer.addView(mThumbnailView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight=(int) (measuredWidth / mAspectRatio);

        mThumbnailView.getLayoutParams().width = measuredWidth;
        mThumbnailView.getLayoutParams().height = measuredHeight;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }


    @Override
    public void loadImage(String uri, int color) {
        mThumbnailView.loadImage(uri, color);
    }

    @Override
    public void setContentDescription(String contactName) {
        super.setContentDescription(contactName);
    }


}

