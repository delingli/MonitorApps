package com.dc.commonlib.weiget.richTextview;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.Gravity;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Glide target for fixed sized Drawables that require sync return.
 * It bridges the gap between sync and async by providing a drawable that can be used immediately,
 * but filling in the contents later.
 * To make it work with animation the user will have subscribe to the Drawable's invalidate events:
 * <pre><code>
 * Drawable asyncWrapper = imageTarget.getLazyDrawable();
 * asyncWrapper.setCallback((Drawable.Callback)this);
 *
 * public void invalidateDrawable(Drawable who) {
 *     targetView.invalidate();
 * }
 * </code></pre>
 */
class WrapperTarget extends SimpleTarget<GlideDrawable> {
	/** Workaround because the AppCompat DrawableWrapper doesn't support null drawable as the API23 version does */
	private final ColorDrawable nullObject = new ColorDrawable(Color.TRANSPARENT);
	private final DrawableWrapper wrapper = new DrawableWrapper(null);/* temporarily null until a setDrawable call*/
	public WrapperTarget(int height,int width) {
		super(height, width);
		setDrawable(null);
		// set wrapper bounds to fix the size of the view, TextViews don't like ImageSpans git  dimensions
		wrapper.setBounds(0, 0, height, width);
	}

	public Drawable getLazyDrawable() {
		return wrapper;
	}

	@Override public void onLoadStarted(Drawable placeholder) {
		setDrawable(placeholder);
	}

	
	@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
		setDrawable(errorDrawable);
	}

	@Override public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation glideAnimation) {
		// start GlideDrawable, even if it's not animated (these methods are No-op in that case)
		glideDrawable.setLoopCount(GlideDrawable.LOOP_FOREVER);
		glideDrawable.start();
		setDrawable(glideDrawable);
	}

	@Override public void onLoadCleared(Drawable placeholder) {
		setDrawable(placeholder);
	}

	private void setDrawable(Drawable drawable) {
		if (drawable == null) {
			drawable = nullObject;
		}
		drawable.setBounds(calcBounds(drawable, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM));
		wrapper.setWrappedDrawable(drawable);
		// invalidate wrapper drawable so it re-draws itself and displays the new wrapped drawable
		wrapper.invalidateSelf();
	}

	/** Align drawable in wrapper in case the image is smaller than the target size. */
	private Rect calcBounds(Drawable drawable, int gravity) {
		Rect bounds = new Rect();
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Rect container = wrapper.getBounds();
		if (w == -1 && h == -1) {
			w = container.width();
			h = container.height();
		}
		Gravity.apply(gravity, w, h, container, bounds);
		return bounds;
	}
}