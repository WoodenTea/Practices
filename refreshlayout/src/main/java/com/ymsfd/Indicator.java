package com.ymsfd;

public class Indicator {

  private float mResistance = 1.7f;
  private float mRadio = 1.2f;
  private int mCurrentOffsetTop = -1;
  private int mOriginalOffsetTop = -1;
  private int mDuration = 1000;
  private int mHeaderHeight;

  public float getResistance() {
    return mResistance;
  }

  public void setResistance(float resistance) {
    this.mResistance = resistance;
  }

  public float getRadio() {
    return mRadio;
  }

  public void setRadio(float mRadio) {
    this.mRadio = mRadio;
  }

  public int getHeaderHeight() {
    return mHeaderHeight;
  }

  public void setHeaderHeight(int headerHeight) {
    mHeaderHeight = headerHeight;
  }

  public void setFooterHeight(int footerHeight) {

  }

  public int getCurrentOffsetTop() {
    return mCurrentOffsetTop == -1 ? getOriginalOffsetTop() : mCurrentOffsetTop;
  }

  public void setCurrentOffsetTop(int currentOffsetTop) {
    mCurrentOffsetTop = currentOffsetTop;
  }

  public int getDuration() {
    return mDuration;
  }

  public void setDuration(int duration) {
    this.mDuration = duration;
  }

  public int getOriginalOffsetTop() {
    return mOriginalOffsetTop;
  }

  public void setOriginalOffsetTop(int originalOffsetTop) {
    this.mOriginalOffsetTop = originalOffsetTop;
  }
}
