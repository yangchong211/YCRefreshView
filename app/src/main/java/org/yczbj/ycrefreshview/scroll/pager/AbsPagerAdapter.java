package org.yczbj.ycrefreshview.scroll.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yczbj.ycrefreshview.R;

import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/7/20
 *     desc  : 动态管理的Adapter。
 *             每次都会创建新view，销毁旧View。节省内存消耗性能
 *     revise: 比如使用场景是启动引导页
 * </pre>
 */
public abstract class AbsPagerAdapter<T> extends PagerAdapter {

	private List<T> mDataList;
	private SparseArray<View> mViewSparseArray;

	public AbsPagerAdapter(List<T> dataList) {
		mDataList = dataList;
		mViewSparseArray = new SparseArray<>(dataList.size());
	}

	@Override
	public int getCount() {
		if (mDataList == null) {
			return 0;
		}
		return mDataList.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		View view = mViewSparseArray.get(position);
		if (view == null) {
			view = getView(container,position);
			mViewSparseArray.put(position, view);
		}
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView(mViewSparseArray.get(position));
	}

	/**
	 * 子类重写的方法
	 * @param container						container
	 * @param position						索引
	 * @return								返回view
	 */
	public abstract View getView(ViewGroup container, int position);


}
