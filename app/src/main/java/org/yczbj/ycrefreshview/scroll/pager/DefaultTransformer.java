

package org.yczbj.ycrefreshview.scroll.pager;

import android.view.View;

public class DefaultTransformer extends BaseTransformer {

	/**
	 * 参照：https://github.com/ToxicBakery/ViewPagerTransforms
	 */


	@Override
	protected void onTransform(View view, float position) {

	}

	@Override
	public boolean isPagingEnabled() {
		return true;
	}

}
