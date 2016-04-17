package com.elorri.android.friendforcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.elorri.android.friendforcast.R;


/**
 * Created by Elorri on 13/03/2016.
 */
public class DynamicHeightGradientTopImageView extends FrameLayout {

    private float mAspectRatio=1.5f;
    private ImageView mThumbnailView;

    public DynamicHeightGradientTopImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.top_gradient_imageview, this);
        FrameLayout thumbnail_container = (FrameLayout) findViewById(R.id.thumbnail_container);
        mThumbnailView = new ImageView(getContext());
        thumbnail_container.addView(mThumbnailView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight=(int) (measuredWidth / mAspectRatio);

        mThumbnailView.getLayoutParams().width = measuredWidth;
        mThumbnailView.getLayoutParams().height = measuredHeight;
        mThumbnailView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }


}

