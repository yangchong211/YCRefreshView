package org.yczbj.ycrefreshviewlib.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.yczbj.ycrefreshviewlib.view.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.AbsEventDelegate;
import org.yczbj.ycrefreshviewlib.inter.ItemView;
import org.yczbj.ycrefreshviewlib.inter.OnErrorListener;
import org.yczbj.ycrefreshviewlib.inter.OnMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnNoMoreListener;

/**
 * @author          杨充
 * @version         1.0
 * @date            2017/1/29
 */
public class DefaultEventDelegate implements AbsEventDelegate {

    private RecyclerArrayAdapter adapter;
    private EventFooter footer ;

    private OnMoreListener onMoreListener;
    private OnNoMoreListener onNoMoreListener;
    private OnErrorListener onErrorListener;

    private boolean hasData = false;
    private boolean isLoadingMore = false;
    private boolean hasMore = false;
    private boolean hasNoMore = false;
    private boolean hasError = false;
    private int status = STATUS_INITIAL;
    private static final int STATUS_INITIAL = 291;
    private static final int STATUS_MORE = 260;
    private static final int STATUS_NOMORE = 408;
    private static final int STATUS_ERROR = 732;

    DefaultEventDelegate(RecyclerArrayAdapter adapter) {
        this.adapter = adapter;
        footer = new EventFooter();
        adapter.addFooter(footer);
    }

    private void onMoreViewShowed() {
        log("onMoreViewShowed");
        if (!isLoadingMore&& onMoreListener !=null){
            isLoadingMore = true;
            onMoreListener.onMoreShow();
        }
    }

    private void onMoreViewClicked() {
        if (onMoreListener !=null){
            onMoreListener.onMoreClick();
        }
    }

    private void onErrorViewShowed() {
        if (onErrorListener!=null){
            onErrorListener.onErrorShow();
        }
    }

    private void onErrorViewClicked() {
        if (onErrorListener!=null){
            onErrorListener.onErrorClick();
        }
    }

    private void onNoMoreViewShowed() {
        if (onNoMoreListener!=null){
            onNoMoreListener.onNoMoreShow();
        }
    }

    private void onNoMoreViewClicked() {
        if (onNoMoreListener!=null){
            onNoMoreListener.onNoMoreClick();
        }
    }

    //-------------------5个状态触发事件-------------------

    @Override
    public void addData(int length) {
        log("addData" + length);
        if (hasMore){
            if (length == 0){
                //当添加0个时，认为已结束加载到底
                if (status==STATUS_INITIAL || status == STATUS_MORE){
                    footer.showNoMore();
                    status = STATUS_NOMORE;
                }
            }else {
                //当Error或初始时。添加数据，如果有More则还原。
                footer.showMore();
                status = STATUS_MORE;
                hasData = true;
            }
        }else{
            if (hasNoMore){
                footer.showNoMore();
                status = STATUS_NOMORE;
            }
        }
        isLoadingMore = false;
    }

    @Override
    public void clear() {
        log("clear");
        hasData = false;
        status = STATUS_INITIAL;
        footer.hide();
        isLoadingMore = false;
    }

    @Override
    public void stopLoadMore() {
        log("stopLoadMore");
        footer.showNoMore();
        status = STATUS_NOMORE;
        isLoadingMore = false;
    }

    @Override
    public void pauseLoadMore() {
        log("pauseLoadMore");
        footer.showError();
        status = STATUS_ERROR;
        isLoadingMore = false;
    }

    @Override
    public void resumeLoadMore() {
        isLoadingMore = false;
        footer.showMore();
        status = STATUS_MORE;
        onMoreViewShowed();
    }

    //-------------------3种View设置-------------------

    @Override
    public void setMore(View view, OnMoreListener listener) {
        this.footer.setMoreView(view);
        this.onMoreListener = listener;
        hasMore = true;
        // 为了处理setMore之前就添加了数据的情况
        if (adapter.getCount()>0){
            addData(adapter.getCount());
        }
        log("setMore");
    }

    @Override
    public void setNoMore(View view, OnNoMoreListener listener) {
        this.footer.setNoMoreView(view);
        this.onNoMoreListener = listener;
        hasNoMore = true;
        log("setNoMore");
    }

    @Override
    public void setErrorMore(View view, OnErrorListener listener) {
        this.footer.setErrorView(view);
        this.onErrorListener = listener;
        hasError = true;
        log("setErrorMore");
    }

    @Override
    public void setMore(int res, OnMoreListener listener) {
        this.footer.setMoreViewRes(res);
        this.onMoreListener = listener;
        hasMore = true;
        // 为了处理setMore之前就添加了数据的情况
        if (adapter.getCount()>0){
            addData(adapter.getCount());
        }
        log("setMore");
    }

    @Override
    public void setNoMore(int res, OnNoMoreListener listener) {
        this.footer.setNoMoreViewRes(res);
        this.onNoMoreListener = listener;
        hasNoMore = true;
        log("setNoMore");
    }

    @Override
    public void setErrorMore(int res, OnErrorListener listener) {
        this.footer.setErrorViewRes(res);
        this.onErrorListener = listener;
        hasError = true;
        log("setErrorMore");
    }



    private class EventFooter implements ItemView {

        private View moreView = null;
        private View noMoreView = null;
        private View errorView = null;
        private int moreViewRes = 0;
        private int noMoreViewRes = 0;
        private int errorViewRes = 0;
        private int flag = Hide;
        private static final int Hide = 0;
        private static final int ShowMore = 1;
        private static final int ShowError = 2;
        private static final int ShowNoMore = 3;
        private boolean skipError = false;
        private boolean skipNoMore = false;

        private EventFooter(){}

        @Override
        public View onCreateView(ViewGroup parent) {
            log("onCreateView");
            return refreshStatus(parent);
        }

        @Override
        public void onBindView(View headerView) {
            log("onBindView");
            headerView.post(new Runnable() {
                @Override
                public void run() {
                    switch (flag){
                        case ShowMore:
                            onMoreViewShowed();
                            break;
                        case ShowNoMore:
                            if (!skipNoMore) {
                                onNoMoreViewShowed();
                            }
                            skipNoMore = false;
                            break;
                        case ShowError:
                            if (!skipError) {
                                onErrorViewShowed();
                            }
                            skipError = false;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        View refreshStatus(ViewGroup parent){
            View view = null;
            switch (flag){
                case ShowMore:
                    if (moreView!=null) {
                        view = moreView;
                    } else if (moreViewRes!=0) {
                        view = LayoutInflater.from(parent.getContext()).inflate(moreViewRes, parent, false);
                    }
                    if (view!=null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onMoreViewClicked();
                            }
                        });
                    }
                    break;
                case ShowError:
                    if (errorView!=null) {
                        view = errorView;
                    } else if (errorViewRes!=0) {
                        view = LayoutInflater.from(parent.getContext()).inflate(errorViewRes, parent, false);
                    }
                    if (view!=null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onErrorViewClicked();
                            }
                        });
                    }
                    break;
                case ShowNoMore:
                    if (noMoreView!=null) {
                        view = noMoreView;
                    } else if (noMoreViewRes!=0) {
                        view = LayoutInflater.from(parent.getContext()).inflate(noMoreViewRes, parent, false);
                    }
                    if (view!=null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onNoMoreViewClicked();
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
            if (view == null) {
                view = new FrameLayout(parent.getContext());
            }
            return view;
        }

        void showError(){
            log("footer showError");
            skipError = true;
            flag = ShowError;
            //noinspection deprecation
            if (adapter.getItemCount()>0){
                adapter.notifyItemChanged(adapter.getItemCount()-1);
            }
        }
        void showMore(){
            log("footer showMore");
            flag = ShowMore;
            //noinspection deprecation
            if (adapter.getItemCount()>0){
                adapter.notifyItemChanged(adapter.getItemCount()-1);
            }
        }
        void showNoMore(){
            log("footer showNoMore");
            skipNoMore = true;
            flag = ShowNoMore;
            //noinspection deprecation
            if (adapter.getItemCount()>0){
                adapter.notifyItemChanged(adapter.getItemCount()-1);
            }
        }

        //初始化
        void hide(){
            log("footer hide");
            flag = Hide;
            //noinspection deprecation
            if (adapter.getItemCount()>0){
                adapter.notifyItemChanged(adapter.getItemCount()-1);
            }
        }

        void setMoreView(View moreView) {
            this.moreView = moreView;
            this.moreViewRes = 0;
        }

        void setNoMoreView(View noMoreView) {
            this.noMoreView = noMoreView;
            this.noMoreViewRes = 0;
        }

        void setErrorView(View errorView) {
            this.errorView = errorView;
            this.errorViewRes = 0;
        }

        void setMoreViewRes(int moreViewRes) {
            this.moreView = null;
            this.moreViewRes = moreViewRes;
        }

        void setNoMoreViewRes(int noMoreViewRes) {
            this.noMoreView = null;
            this.noMoreViewRes = noMoreViewRes;
        }

        void setErrorViewRes(int errorViewRes) {
            this.errorView = null;
            this.errorViewRes = errorViewRes;
        }

        @Override
        public int hashCode() {
            return flag+13589;
        }
    }



    private static void log(String content){
        if (YCRefreshView.DEBUG){
            Log.i(YCRefreshView.TAG,content);
        }
    }


}
