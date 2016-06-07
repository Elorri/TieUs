package com.elorri.android.tieus.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.elorri.android.tieus.R;


/**
 * Created by Elorri on 13/03/2016.
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
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mThumbnailView.loadImage(uri, color);
    }

    @Override
    public void setContentDescription(String contactName) {
        super.setContentDescription(contactName);
    }


}

