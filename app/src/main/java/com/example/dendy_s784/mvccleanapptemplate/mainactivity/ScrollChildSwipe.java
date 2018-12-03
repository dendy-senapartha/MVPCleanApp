package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

public class ScrollChildSwipe extends SwipeRefreshLayout {

    private View mScrollUpChild;

    public ScrollChildSwipe(Context context) {
        super(context);
    }

    public ScrollChildSwipe(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public boolean canScrollVertically(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }
}
