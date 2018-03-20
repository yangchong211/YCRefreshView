package org.yczbj.ycrefreshviewlib.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.EventDelegateAble;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author          杨充
 * @version         1.0
 * @date            2017/5/2
 */
abstract public class RecyclerArrayAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>   {

    private List<T> mObjects;
    private EventDelegateAble mEventDelegate;
    private ArrayList<ItemView> headers = new ArrayList<>();
    private ArrayList<ItemView> footers = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    private final Object mLock = new Object();
    private boolean mNotifyOnChange = true;
    private Context mContext;


    public RecyclerArrayAdapter(Context context) {
        init(context,  new ArrayList<T>());
    }


    public RecyclerArrayAdapter(Context context, T[] objects) {
        init(context, Arrays.asList(objects));
    }


    public RecyclerArrayAdapter(Context context, List<T> objects) {
        init(context, objects);
    }


    private void init(Context context , List<T> objects) {
        mContext = context;
        mObjects = new ArrayList<>(objects);
    }


    public interface ItemView {
         View onCreateView(ViewGroup parent);
         void onBindView(View headerView);
    }

    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public interface OnMoreListener{
        void onMoreShow();
        void onMoreClick();
    }


    public interface OnNoMoreListener{
        void onNoMoreShow();
        void onNoMoreClick();
    }

    public interface OnErrorListener{
        void onErrorShow();
        void onErrorClick();
    }

    public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{
        private int mMaxCount;
        GridSpanSizeLookup(int maxCount){
            this.mMaxCount = maxCount;
        }
        @Override
        public int getSpanSize(int position) {
            if (headers.size()!=0){
                if (position<headers.size()) {
                    return mMaxCount;
                }
            }
            if (footers.size()!=0) {
                int i = position - headers.size() - mObjects.size();
                if (i >= 0) {
                    return mMaxCount;
                }
            }
            return 1;
        }
    }

    public GridSpanSizeLookup obtainGridSpanSizeLookUp(int maxCount){
        return new GridSpanSizeLookup(maxCount);
    }


    public void stopMore(){
        if (mEventDelegate == null) {
            throw new NullPointerException("You should invoking setLoadMore() first");
        }
        mEventDelegate.stopLoadMore();
    }

    public void pauseMore(){
        if (mEventDelegate == null) {
            throw new NullPointerException("You should invoking setLoadMore() first");
        }
        mEventDelegate.pauseLoadMore();
    }

    public void resumeMore(){
        if (mEventDelegate == null) {
            throw new NullPointerException("You should invoking setLoadMore() first");
        }
        mEventDelegate.resumeLoadMore();
    }


    public void addHeader(ItemView view){
        if (view==null) {
            throw new NullPointerException("ItemView can't be null");
        }
        headers.add(view);
        notifyItemInserted(headers.size()-1);
    }

    public void addFooter(ItemView view){
        if (view==null) {
            throw new NullPointerException("ItemView can't be null");
        }
        footers.add(view);
        notifyItemInserted(headers.size()+getCount()+footers.size()-1);
    }

    public void removeAllHeader(){
        int count = headers.size();
        headers.clear();
        notifyItemRangeRemoved(0,count);
    }

    public void removeAllFooter(){
        int count = footers.size();
        footers.clear();
        notifyItemRangeRemoved(headers.size()+getCount(),count);
    }

    public ItemView getHeader(int index){
        return headers.get(index);
    }

    public ItemView getFooter(int index){
        return footers.get(index);
    }

    public int getHeaderCount(){return headers.size();}

    public int getFooterCount(){return footers.size();}

    public void removeHeader(ItemView view){
        int position = headers.indexOf(view);
        headers.remove(view);
        notifyItemRemoved(position);
    }

    public void removeFooter(ItemView view){
        int position = headers.size()+getCount()+footers.indexOf(view);
        footers.remove(view);
        notifyItemRemoved(position);
    }


    private EventDelegateAble getEventDelegate(){
        if (mEventDelegate == null) {
            mEventDelegate = new DefaultEventDelegate(this);
        }
        return mEventDelegate;
    }

    @Deprecated
    public void setMore(final int res, final OnLoadMoreListener listener){
        getEventDelegate().setMore(res, new OnMoreListener() {
            @Override
            public void onMoreShow() {
                listener.onLoadMore();
            }

            @Override
            public void onMoreClick() {

            }
        });
    }

    public void setMore(final View view,final OnLoadMoreListener listener){
        getEventDelegate().setMore(view, new OnMoreListener() {
            @Override
            public void onMoreShow() {
                listener.onLoadMore();
            }

            @Override
            public void onMoreClick() {

            }
        });
    }

    public void setMore(final int res, final OnMoreListener listener){
        getEventDelegate().setMore(res, listener);
    }

    public void setMore(final View view,OnMoreListener listener){
        getEventDelegate().setMore(view, listener);
    }

    public void setNoMore(final int res) {
        getEventDelegate().setNoMore(res,null);
    }

    public void setNoMore(final View view) {
        getEventDelegate().setNoMore(view,null);
    }

    public void setNoMore(final View view,OnNoMoreListener listener) {
        getEventDelegate().setNoMore(view,listener);
    }

    public void setNoMore(final int res,OnNoMoreListener listener) {
        getEventDelegate().setNoMore(res,listener);
    }


    public void setError(final int res) {
        getEventDelegate().setErrorMore(res,null);
    }

    public void setError(final View view) {
        getEventDelegate().setErrorMore(view,null);
    }

    public void setError(final int res,OnErrorListener listener) {
        getEventDelegate().setErrorMore(res,listener);
    }

    public void setError(final View view,OnErrorListener listener) {
        getEventDelegate().setErrorMore(view,listener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //增加对RecyclerArrayAdapter奇葩操作的修复措施
        registerAdapterDataObserver(new FixDataObserver(recyclerView));
    }

    private class FixDataObserver extends RecyclerView.AdapterDataObserver {

        private RecyclerView recyclerView;
        FixDataObserver(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
                RecyclerArrayAdapter adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
                if (adapter.getFooterCount() > 0 && adapter.getCount() == itemCount) {
                    recyclerView.scrollToPosition(0);
                }
            }
        }
    }

    /**
     * 添加数据
     * @param object            数据
     */
    public void add(T object) {
        if (mEventDelegate!=null) {
            mEventDelegate.addData(object == null ? 0 : 1);
        }
        if (object!=null){
            synchronized (mLock) {
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) {
            notifyItemInserted(headers.size() + getCount());
        }
        log("add notifyItemInserted "+(headers.size()+getCount()));
    }

    /**
     * 添加所有数据
     * @param collection        Collection集合数据
     */
    public void addAll(Collection<? extends T> collection) {
        if (mEventDelegate!=null) {
            mEventDelegate.addData(collection == null ? 0 : collection.size());
        }
        if (collection!=null&&collection.size()!=0){
            synchronized (mLock) {
                mObjects.addAll(collection);
            }
        }
        int dataCount = collection==null?0:collection.size();
        if (mNotifyOnChange) {
            notifyItemRangeInserted(headers.size() + getCount() - dataCount, dataCount);
        }
        log("addAll notifyItemRangeInserted "+(headers.size()+getCount()-dataCount)+","+(dataCount));

    }

    /**
     * 添加所有数据
     * @param items            数据
     */
    public void addAll(T[] items) {
        if (mEventDelegate!=null) {
            mEventDelegate.addData(items == null ? 0 : items.length);
        }
        if (items!=null&&items.length!=0) {
            synchronized (mLock) {
                Collections.addAll(mObjects, items);
            }
        }
        int dataCount = items==null?0:items.length;
        if (mNotifyOnChange) {
            notifyItemRangeInserted(headers.size() + getCount() - dataCount, dataCount);
        }
        log("addAll notifyItemRangeInserted "+((headers.size()+getCount()-dataCount)+","+(dataCount)));
    }

    /**
     * 插入，不会触发任何事情
     * @param object            数据
     * @param index             索引
     */
    public void insert(T object, int index) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }
        if (mNotifyOnChange) {
            notifyItemInserted(headers.size() + index);
        }
        log("insert notifyItemRangeInserted "+(headers.size()+index));
    }

    /**
     * 插入数组，不会触发任何事情
     * @param object            数据
     * @param index             索引
     */
    public void insertAll(T[] object, int index) {
        synchronized (mLock) {
            mObjects.addAll(index, Arrays.asList(object));
        }
        int dataCount = object.length;
        if (mNotifyOnChange) {
            notifyItemRangeInserted(headers.size() + index, dataCount);
        }
        log("insertAll notifyItemRangeInserted "+((headers.size()+index)+","+(dataCount)));
    }

    /**
     * 插入数组，不会触发任何事情
     * @param object            数据
     * @param index             索引
     */
    public void insertAll(Collection<? extends T> object, int index) {
        synchronized (mLock) {
            mObjects.addAll(index, object);
        }
        int dataCount = object==null?0:object.size();
        if (mNotifyOnChange) {
            notifyItemRangeInserted(headers.size() + index, dataCount);
        }
        log("insertAll notifyItemRangeInserted "+((headers.size()+index)+","+(dataCount)));
    }


    /**
     * 更新数据
     * @param object            数据
     * @param pos               索引
     */
    public void update(T object,int pos){
        synchronized (mLock) {
            mObjects.set(pos,object);
        }
        if (mNotifyOnChange) {
            notifyItemChanged(pos);
        }
        log("insertAll notifyItemChanged "+pos);
    }

    /**
     * 删除，不会触发任何事情
     * @param object            要移除的数据
     */
    public void remove(T object) {
        int position = mObjects.indexOf(object);
        synchronized (mLock) {
            if (mObjects.remove(object)){
                if (mNotifyOnChange) {
                    notifyItemRemoved(headers.size() + position);
                }
                log("remove notifyItemRemoved "+(headers.size()+position));
            }
        }
    }

    /**
     * 删除，不会触发任何事情
     * @param position          要移除数据的索引
     */
    public void remove(int position) {
        synchronized (mLock) {
            mObjects.remove(position);
        }
        if (mNotifyOnChange) {
            notifyItemRemoved(headers.size() + position);
        }
        log("remove notifyItemRemoved "+(headers.size()+position));
    }


    /**
     * 触发清空
     * 与{@link #clear()}的不同仅在于这个使用notifyItemRangeRemoved.
     * 猜测这个方法与add伪并发执行的时候会造成"Scrapped or attached views may not be recycled"的Crash.
     * 所以建议使用{@link #clear()}
     */
    public void removeAll() {
        int count = mObjects.size();
        if (mEventDelegate!=null) {
            mEventDelegate.clear();
        }
        synchronized (mLock) {
            mObjects.clear();
        }
        if (mNotifyOnChange) {
            notifyItemRangeRemoved(headers.size(), count);
        }
        log("clear notifyItemRangeRemoved "+(headers.size())+","+(count));
    }

    /**
     * 触发清空
     */
    public void clear() {
        int count = mObjects.size();
        if (mEventDelegate!=null) {
            mEventDelegate.clear();
        }
        synchronized (mLock) {
            mObjects.clear();
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
        log("clear notifyItemRangeRemoved "+(headers.size())+","+(count));
    }

    /**
     * 使用指定的比较器对此适配器的内容进行排序
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(mObjects, comparator);
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }


    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context ctx) {
        mContext = ctx;
    }

    /**
     * 这个函数包含了头部和尾部view的个数，不是真正的item个数。
     */
    @Deprecated
    @Override
    public final int getItemCount() {
        return mObjects.size()+headers.size()+footers.size();
    }

    /**
     * 应该使用这个获取item个数
     */
    public int getCount(){
        return mObjects.size();
    }

    private View createSpViewByType(ViewGroup parent, int viewType){
        for (ItemView headerView:headers){
            if (headerView.hashCode() == viewType){
                View view = headerView.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null) {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                } else {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }

        for (ItemView footerView:footers){
            if (footerView.hashCode() == viewType){
                View view = footerView.onCreateView(parent);
                StaggeredGridLayoutManager.LayoutParams layoutParams;
                if (view.getLayoutParams()!=null) {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(view.getLayoutParams());
                } else {
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
                return view;
            }
        }
        return null;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createSpViewByType(parent, viewType);
        if (view!=null){
            return new StateViewHolder(view);
        }
        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType);
        //itemView 的点击事件
        if (mItemClickListener!=null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(
                            viewHolder.getAdapterPosition()-headers.size());
                }
            });
        }
        if (mItemLongClickListener!=null){
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(
                            viewHolder.getAdapterPosition()-headers.size());
                }
            });
        }
        return viewHolder;
    }

    /**
     * 抽象方法，子类继承
     */
    public abstract BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        if (headers.size()!=0 && position<headers.size()){
            headers.get(position).onBindView(holder.itemView);
            return ;
        }

        int i = position - headers.size() - mObjects.size();
        if (footers.size()!=0 && i>=0){
            footers.get(i).onBindView(holder.itemView);
            return ;
        }
        OnBindViewHolder(holder,position-headers.size());
    }


    @SuppressWarnings("unchecked")
    private void OnBindViewHolder(BaseViewHolder holder, final int position){
        holder.setData(getItem(position));
    }


    @Deprecated
    @Override
    public final int getItemViewType(int position) {
        if (headers.size()!=0){
            if (position<headers.size()) {
                return headers.get(position).hashCode();
            }
        }
        if (footers.size()!=0){
            /*
            eg:
            0:header1
            1:header2   2
            2:object1
            3:object2
            4:object3
            5:object4
            6:footer1   6(position) - 2 - 4 = 0
            7:footer2
             */
            int i = position - headers.size() - mObjects.size();
            if (i >= 0){
                return footers.get(i).hashCode();
            }
        }
        return getViewType(position-headers.size());
    }

    public int getViewType(int position){
        return 0;
    }

    /**
     * 获取所有的数据list集合
     * @return
     */
    public List<T> getAllData(){
        return new ArrayList<>(mObjects);
    }

    /**
     * 获取item
     */
    public T getItem(int position) {
        return mObjects.get(position);
    }

    /**
     * 获取item索引位置
     * @param item      item
     * @return          索引位置
     */
    public int getPosition(T item) {
        return mObjects.indexOf(item);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private class StateViewHolder extends BaseViewHolder{
        StateViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    /**
     * 设置条目点击事件
     * @param listener              监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    /**
     * 设置条目长按事件
     * @param listener              监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    /**
     * 打印日志
     * @param content               内容
     */
    private static void log(String content){
        if (YCRefreshView.DEBUG){
            Log.i(YCRefreshView.TAG,content);
        }
    }
}
