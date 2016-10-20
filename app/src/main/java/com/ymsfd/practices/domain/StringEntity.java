package com.ymsfd.practices.domain;

import com.ymsfd.practices.R;

public class StringEntity extends Entity {
    public String name;

    @Override
    public int itemViewType() {
        return R.layout.list_item_card;
    }
}
