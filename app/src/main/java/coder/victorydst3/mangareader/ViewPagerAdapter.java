package coder.victorydst3.mangareader;
/**
 * Created by lantm-mac-air on 7/20/16
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.model.HomePageItem;
import coder.victorydst3.mangareader.util.ScreenUtil;

/**
 * Created by TienLQ on 4/13/16
 */
public class ViewPagerAdapter extends BaseAdapter<ViewPagerAdapter.ViewHolder> {

    private List<HomePageItem> mHomePage = new ArrayList<>();
    private Context mContext;

    private ImpOnClickItem mImpOnClickItem;
    private int mPositionSelection = 0;

    public ViewPagerAdapter(Context context, List<HomePageItem> homePage, ImpOnClickItem impOnClickItem) {
        super(context);
        mImpOnClickItem = impOnClickItem;
        mHomePage = homePage;
        mContext = context;


    }

    public void setPositionSelection(int positionSelection) {
        mPositionSelection = positionSelection;
        notifyDataSetChanged();
    }


    @Override
    public ViewPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.itemView.setSelected(position == mPositionSelection);
        viewHolder.mImgIcon.setImageResource(mHomePage.get(position).getDrawableResource());
        viewHolder.mTvTitle.setText(mHomePage.get(position).getTitle());
//        viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_white));
        ViewGroup.LayoutParams layoutParams = viewHolder.mRLayout.getLayoutParams();
        layoutParams.width = ScreenUtil.getWidthScreen(mContext) / 5;
        viewHolder.mRLayout.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPositionSelection = position;
                mImpOnClickItem.onClick(position);
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mHomePage.size();
    }

    public interface ImpOnClickItem {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImgIcon;
        private final TextView mTvTitle;
        private final TextView mTvNumber;
        private final RelativeLayout mRLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mImgIcon = (ImageView) itemView.findViewById(R.id.thumb);
            mTvTitle = (TextView) itemView.findViewById(R.id.title);
            mTvNumber = (TextView) itemView.findViewById(R.id.tvNotificationCount);
            mRLayout = (RelativeLayout) itemView.findViewById(R.id.rlTab);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImpOnClickItem.onClick(getLayoutPosition());

                }
            });

        }
    }
}
