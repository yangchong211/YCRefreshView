
package org.yczbj.ycrefreshview.scroll.pager;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import android.view.View;


/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/6/20
 *     desc  : BaseTransformer
 *     revise:
 * </pre>
 */
public abstract class BaseTransformer implements PageTransformer {


	/**
	 * 子类实现
	 * @param page							view
	 * @param position						索引
	 */
	protected abstract void onTransform(View page, float position);


	@Override
	public void transformPage(@NonNull View page, float position) {
		onPreTransform(page, position);
		onTransform(page, position);
	}

	protected boolean hideOffscreenPages() {
		return true;
	}


	protected boolean isPagingEnabled() {
		return false;
	}


	protected void onPreTransform(View page, float position) {
		final float width = page.getWidth();

		page.setRotationX(0);
		page.setRotationY(0);
		page.setRotation(0);
		page.setScaleX(1);
		page.setScaleY(1);
		page.setPivotX(0);
		page.setPivotY(0);
		page.setTranslationY(0);
		page.setTranslationX(isPagingEnabled() ? 0f : -width * position);

		if (hideOffscreenPages()) {
			page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
		} else {
			page.setAlpha(1f);
		}
	}

}
