

package org.yczbj.ycrefreshview.six;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshviewlib.item.StickyHeaderItemLine;



public class StickyHeaderAdapter implements
        StickyHeaderItemLine.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder> {

    private LayoutInflater mInflater;

    public StickyHeaderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public long getHeaderId(int position) {
        return position / 3;
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_item, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText("第"+getHeaderId(position)+"组");
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView header;
        HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }

}
