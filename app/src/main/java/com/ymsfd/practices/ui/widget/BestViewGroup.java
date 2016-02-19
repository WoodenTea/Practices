package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/11/15
 * Time: 15:48
 */
public class BestViewGroup extends ViewGroup {
    private View mProfilePhoto;
    private View mMenu;
    private View mTitle;
    private View mSubtitle;

    public BestViewGroup(Context context) {
        super(context);
    }

    public BestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        D("Child Count: " + getChildCount());

        int widthConstraints = getPaddingLeft() + getPaddingRight();
        int heightConstraints = getPaddingTop() + getPaddingBottom();
        int width = 0;
        int height = 0;

        mProfilePhoto = getChildAt(0);
        mMenu = getChildAt(1);
        mTitle = getChildAt(2);
        mSubtitle = getChildAt(3);

        // 2. Measure the ProfilePhoto
        measureChildWithMargins(
                mProfilePhoto,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        // 3. Update the constraints.
        widthConstraints += mProfilePhoto.getMeasuredWidth();
        width += mProfilePhoto.getMeasuredWidth();
        height = Math.max(mProfilePhoto.getMeasuredHeight(), height);

        // 4. Measure the Menu.
        measureChildWithMargins(
                mMenu,
                widthMeasureSpec,
                widthConstraints,
                heightMeasureSpec,
                heightConstraints);

        // 5. Update the constraints.
        widthConstraints += mMenu.getMeasuredWidth();
        width += mMenu.getMeasuredWidth();
        height = Math.max(mMenu.getMeasuredHeight(), height);

        // 6. Prepare the vertical MeasureSpec.
        int verticalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - widthConstraints,
                MeasureSpec.getMode(widthMeasureSpec));

        int verticalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
                MeasureSpec.getMode(heightMeasureSpec));

        // 7. Measure the Title.
        measureChildWithMargins(
                mTitle,
                verticalWidthMeasureSpec,
                0,
                verticalHeightMeasureSpec,
                0);

        // 8. Measure the Subtitle.
        measureChildWithMargins(
                mSubtitle,
                verticalWidthMeasureSpec,
                0,
                verticalHeightMeasureSpec,
                mTitle.getMeasuredHeight());

        // 9. Update the sizes.
        width += Math.max(mTitle.getMeasuredWidth(), mSubtitle.getMeasuredWidth());
        height = Math.max(mTitle.getMeasuredHeight() + mSubtitle.getMeasuredHeight(), height);

        // 10. Set the dimension for this ViewGroup.
        setMeasuredDimension(
                resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    private void D(String msg) {
        Log.d("BestGroup", msg);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        D("Photo Width: " + mProfilePhoto.getMeasuredWidth() + " Height: " + mProfilePhoto
                .getMeasuredHeight());
        D("Title Width: " + mTitle.getMeasuredWidth() + " Height: " + mTitle.getMeasuredHeight());
        D("Sub Width: " + mSubtitle.getMeasuredWidth() + " Height: " + mSubtitle
                .getMeasuredHeight());
        D("Menu Width: " + mMenu.getMeasuredWidth() + " Height: " + mMenu.getMeasuredHeight());

        int width = 0;
        mProfilePhoto.layout(width, 0, mProfilePhoto.getMeasuredWidth(), mProfilePhoto
                .getMeasuredHeight());
        width += mProfilePhoto.getMeasuredWidth();

        mTitle.layout(width, 0, width + mTitle.getMeasuredWidth(), mTitle.getMeasuredHeight());
        mSubtitle.layout(width, mTitle.getMeasuredHeight(), width + mSubtitle.getMeasuredWidth(),
                mSubtitle.getMeasuredHeight() + mTitle.getMeasuredHeight());

        width += mTitle.getMeasuredWidth();
        mMenu.layout(width, 0, width + mMenu.getMeasuredWidth(), mMenu.getMeasuredHeight());
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BestViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        BestViewGroup.LayoutParams lp = (BestViewGroup.LayoutParams) child.getLayoutParams();

        int childWidthMeasureSpec = getChildMeasureSpec(
                parentWidthMeasureSpec,
                widthUsed + lp.leftMargin + lp.rightMargin,
                lp.width);

        int childHeightMeasureSpec = getChildMeasureSpec(
                parentHeightMeasureSpec,
                heightUsed + lp.topMargin + lp.bottomMargin,
                lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
