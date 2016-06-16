package com.elorri.android.tieus.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 06/06/2016.
 */
public class DynamicWidthGradientTopAvatarView extends FrameLayout implements GradientTopAvatarView{

    private float mAspectRatio=1.5f;
    private AvatarView mThumbnailView;

    public DynamicWidthGradientTopAvatarView(Context context, AttributeSet attrs) {
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
        int measuredHeight = getMeasuredHeight();
        int measuredWidth=(int) (measuredHeight / mAspectRatio);

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
