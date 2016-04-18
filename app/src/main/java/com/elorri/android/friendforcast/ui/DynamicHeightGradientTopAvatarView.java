package com.elorri.android.friendforcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.elorri.android.friendforcast.R;


/**
 * Created by Elorri on 13/03/2016.
 */
public class DynamicHeightGradientTopAvatarView extends FrameLayout {

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


    public void loadImage(String uri, int color) {
        mThumbnailView.loadImage(uri, color);
    }
}

