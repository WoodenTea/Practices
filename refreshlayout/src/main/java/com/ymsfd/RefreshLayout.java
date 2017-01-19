package com.ymsfd;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.ymsfd.refreshlayout.R;

public class RefreshLayout extends ViewGroup {

  private static final int INVALID_POINTER = -1;
  private static final float DRAG_RATE = .5f;
  private View mContent;
  private View mHeader;
  private View mFooter;
  private Indicator mIndicator;
  private Mode mMode;
  private boolean mReturningToStart;
  private int mActivePointerId = INVALID_POINTER;
  private float mInitialDownY;
  private boolean mIsBeingDragged;
  private float mInitialMotionY;
  private int mTouchSlop;
  private int mCircleViewIndex = -1;
  private int mFooterViewIndex = -1;

  public RefreshLayout(Context context) {
    this(context, null);
  }

  public RefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    ensureContent();

    final int action = MotionEventCompat.getActionMasked(ev);
    int pointerIndex;

    if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
      mReturningToStart = false;
    }

    if (!isEnabled() || mReturningToStart || canChildScrollUp()) {
      // Fail fast if we're not in a state where a swipe is possible
      return false;
    }

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        setTargetOffsetTopAndBottom(mIndicator.getOriginalOffsetTop() - mHeader.getTop(), true);
        mActivePointerId = ev.getPointerId(0);
        mIsBeingDragged = false;

        pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }
        mInitialDownY = ev.getY(pointerIndex);
        break;

      case MotionEvent.ACTION_MOVE:
        if (mActivePointerId == INVALID_POINTER) {
          D("Got ACTION_MOVE event but don't have an active pointer id.");
          return false;
        }

        pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }
        final float y = ev.getY(pointerIndex);
        startDragging(y);
        break;

      case MotionEventCompat.ACTION_POINTER_UP:
        onSecondaryPointerUp(ev);
        break;

      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        mIsBeingDragged = false;
        mActivePointerId = INVALID_POINTER;
        break;
    }

    return mIsBeingDragged;
  }

  void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
    mHeader.bringToFront();
    ViewCompat.offsetTopAndBottom(mHeader, offset);
    mIndicator.setCurrentOffsetTop(mHeader.getTop());
    D("Offset->" + offset + " Current Target Offset Top->" + mIndicator.getCurrentOffsetTop());
    if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
      invalidate();
    }
  }

  private void startDragging(float y) {
    final float yDiff = y - mInitialDownY;
    if (yDiff > mTouchSlop && !mIsBeingDragged) {
      mInitialMotionY = mInitialDownY + mTouchSlop;
      D("InitialMotionY->" + mInitialMotionY);
      mIsBeingDragged = true;
    }
  }

  private void onSecondaryPointerUp(MotionEvent ev) {
    final int pointerIndex = MotionEventCompat.getActionIndex(ev);
    final int pointerId = ev.getPointerId(pointerIndex);
    if (pointerId == mActivePointerId) {
      // This was our active pointer going up. Choose a new
      // active pointer and adjust accordingly.
      final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
      mActivePointerId = ev.getPointerId(newPointerIndex);
    }
  }

  private boolean canChildScrollUp() {
    return ViewCompat.canScrollVertically(mContent, -1);
  }

  private boolean canChildScrollBottom() {
    return ViewCompat.canScrollVertically(mContent, 1);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    final int action = MotionEventCompat.getActionMasked(event);
    int pointerIndex;

    if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
      mReturningToStart = false;
    }

    if (!isEnabled() || mReturningToStart || canChildScrollUp()) {
      // Fail fast if we're not in a state where a swipe is possible
      return false;
    }

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        mActivePointerId = event.getPointerId(0);
        mIsBeingDragged = false;
        break;

      case MotionEvent.ACTION_MOVE: {
        pointerIndex = event.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          D("Got ACTION_MOVE event but have an invalid active pointer id.");
          return false;
        }

        final float y = event.getY(pointerIndex);
        startDragging(y);

        if (mIsBeingDragged) {
          final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
          if (overScrollTop > 0) {
            moveSpinner(overScrollTop);
          } else {
            return false;
          }
        }
        break;
      }

      case MotionEventCompat.ACTION_POINTER_DOWN: {
        pointerIndex = MotionEventCompat.getActionIndex(event);
        if (pointerIndex < 0) {
          D("Got ACTION_POINTER_DOWN event but have an invalid action index.");
          return false;
        }
        mActivePointerId = event.getPointerId(pointerIndex);
        break;
      }

      case MotionEventCompat.ACTION_POINTER_UP:
        onSecondaryPointerUp(event);
        break;

      case MotionEvent.ACTION_UP: {
        pointerIndex = event.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
          D("Got ACTION_UP event but don't have an active pointer id.");
          return false;
        }

        if (mIsBeingDragged) {
          final float y = event.getY(pointerIndex);
          final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
          mIsBeingDragged = false;
          finishSpinner(overScrollTop);
        }
        mActivePointerId = INVALID_POINTER;
        return false;
      }

      case MotionEvent.ACTION_CANCEL:
        return false;
    }

    return true;
  }

  private void moveSpinner(float overScrollTop) {
    float originalDragPercent = overScrollTop / mIndicator.getHeaderHeight();

    float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
    float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
    float extraOS = Math.abs(overScrollTop) - mIndicator.getHeaderHeight();
    float slingshotDist = mIndicator.getHeaderHeight();
    float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2)
        / slingshotDist);
    float tensionPercent = (float) ((tensionSlingshotPercent / 4)
        - Math.pow((tensionSlingshotPercent / 4), 2)) * 2f;
    float extraMove = (slingshotDist) * tensionPercent * 2;

    int targetY =
        mIndicator.getOriginalOffsetTop() + (int) ((slingshotDist * dragPercent) + extraMove);
    setTargetOffsetTopAndBottom(targetY - mIndicator.getCurrentOffsetTop(), true /* requires update */);
  }

  private void finishSpinner(float overScrollTop) {
    mIndicator.setCurrentOffsetTop(-1);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    final int width = getMeasuredWidth();
    final int height = getMeasuredHeight();
    if (getChildCount() == 0) {
      return;
    }
    if (mContent == null) {
      ensureContent();
    }
    if (mContent == null) {
      return;
    }

    final View child = mContent;
    final int childLeft = getPaddingLeft();
    final int childTop = getPaddingTop();
    final int childWidth = width - childLeft - getPaddingRight();
    final int childHeight = height - childTop - getPaddingBottom();
    child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

    if (mHeader != null) {
      int headerWidth = mHeader.getMeasuredWidth();
      int headerHeight = mHeader.getMeasuredHeight();
      D("Header Height->" + headerHeight + " OffsetTop->" + mIndicator.getCurrentOffsetTop());
      mHeader.layout((width / 2 - headerWidth / 2), mIndicator.getCurrentOffsetTop(),
          (width / 2 + headerWidth / 2), mIndicator.getCurrentOffsetTop() + headerHeight);
    }
  }

  void D(String message) {
    Log.d("RefreshLayout", message);
  }

  public void setHeader(View header) {
    if (header == null) {
      return;
    }

    if (mHeader != null && mHeader != header) {
      removeView(mHeader);
    }
    ViewGroup.LayoutParams lp = header.getLayoutParams();
    if (lp == null) {
      lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      header.setLayoutParams(lp);
    }
    mHeader = header;
    addView(header);
  }

  @Override
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new LayoutParams(getContext(), attrs);
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new LayoutParams(p);
  }

  @Override
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (mContent == null) {
      ensureContent();
    }

    if (mContent == null) {
      return;
    }

    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();

    widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - paddingLeft -
        paddingRight, MeasureSpec.EXACTLY);
    heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() -
        getPaddingBottom(), MeasureSpec.EXACTLY);
    mContent.measure(widthMeasureSpec, heightMeasureSpec);

    if (mHeader != null) {
      measureChildWithMargins(mHeader, widthMeasureSpec, 0, heightMeasureSpec, 0);
      MarginLayoutParams lp = (MarginLayoutParams) mHeader.getLayoutParams();
      int headerHeight = mHeader.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
      mIndicator.setHeaderHeight(headerHeight);
      mIndicator.setOriginalOffsetTop(-headerHeight);
    }

    if (mFooter != null) {
      measureChildWithMargins(mFooter, widthMeasureSpec, 0, heightMeasureSpec, 0);
      MarginLayoutParams lp = (MarginLayoutParams) mFooter.getLayoutParams();
      int headerHeight = mFooter.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
      mIndicator.setFooterHeight(headerHeight);
    }

    mCircleViewIndex = -1;
    // Get the index of the circleview.
    for (int index = 0; index < getChildCount(); index++) {
      if (getChildAt(index) == mHeader) {
        mCircleViewIndex = index;
        break;
      }
    }
  }

  private void ensureContent() {
    if (mContent == null) {
      for (int i = 0; i < getChildCount(); i++) {
        View child = getChildAt(i);
        if (!child.equals(mHeader) && !child.equals(mFooter)) {
          mContent = child;
          break;
        }
      }
    }
  }

  @Override
  protected int getChildDrawingOrder(int childCount, int i) {
    if (mCircleViewIndex < 0) {
      return i;
    } else if (i == childCount - 1) {
      // Draw the selected child last
      return mCircleViewIndex;
    } else if (i >= mCircleViewIndex) {
      // Move the children after the selected child earlier one
      return i + 1;
    } else {
      // Keep the children before the selected child the same
      return i;
    }
  }

  private void init(Context context, AttributeSet attrs) {
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    mIndicator = new Indicator();
    if (attrs != null) {
      TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, 0,
          0);
      mIndicator.setResistance(arr.getFloat(R.styleable.RefreshLayout_resistance,
          mIndicator.getResistance()));
      mIndicator
          .setDuration(arr.getInt(R.styleable.RefreshLayout_duration, mIndicator.getDuration()));
      mIndicator.setRadio(arr.getFloat(R.styleable.RefreshLayout_ratio_to_refresh,
          mIndicator.getRadio()));
      mMode = getModeFromIndex(arr.getInt(R.styleable.RefreshLayout_mode, 3));
      arr.recycle();
    }
  }

  private Mode getModeFromIndex(int index) {
    switch (index) {
      case 1:
        return Mode.REFRESH;
      case 2:
        return Mode.LOAD_MORE;
      case 3:
        return Mode.BOTH;
      default:
        return Mode.BOTH;
    }
  }

  public enum Mode {
    REFRESH, LOAD_MORE, BOTH
  }

  public static class LayoutParams extends MarginLayoutParams {

    public LayoutParams(Context c, AttributeSet attrs) {
      super(c, attrs);
    }

    public LayoutParams(int width, int height) {
      super(width, height);
    }

    @SuppressWarnings({"unused"})
    public LayoutParams(MarginLayoutParams source) {
      super(source);
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }
  }
}
