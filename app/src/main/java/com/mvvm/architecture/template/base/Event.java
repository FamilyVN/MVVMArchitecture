package com.mvvm.architecture.template.base;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class Event<T, P> {
    private T mContent;
    private P mPayload;
    private boolean mHasBeenHandled = false;

    public Event(T content) {
        this.mContent = content;
    }

    public Event(T type, P payload) {
        this.mContent = type;
        this.mPayload = payload;
    }

    public T getContentIfNotHandled() {
        if (mHasBeenHandled) {
            return null;
        } else {
            mHasBeenHandled = true;
            return mContent;
        }
    }

    public P getPayload() {
        return mPayload;
    }

    public T peekContent() {
        return mContent;
    }
}
