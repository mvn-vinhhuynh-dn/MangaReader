package coder.victorydst3.mangareader.ui.reader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.BaseActivity;
import coder.victorydst3.mangareader.Common.Constant;
import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.util.ScriptParser;
import uk.co.senab.photoview.PhotoView;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/28/16.
 */
@EActivity(R.layout.activity_reader)
public class ReaderActivity extends BaseActivity {
    @ViewById(R.id.viewPager)
    ViewPager mPager;

    @Extra
    String mUrl;
    private List<String> mListUrl = new ArrayList<>();
    private ReaderAdapter mAdapter;

    @AfterViews
    void AfterViews() {
        mAdapter = new ReaderAdapter(this, mListUrl);
        getData();
    }

    @Background
    void getData() {
        Document document = null;
        try {
            document = Jsoup.connect(Constant.BASE_URL + mUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document != null) {
            parserData(document);
        }
    }

    private void parserData(Document document) {
        Element div = document.select("div.pageread_img").first();
        Elements script = div.select("script");
        for (Element element : script) {
            for (DataNode node : element.dataNodes()) {
                if (node.getWholeData() != null) {
                    Log.d("VINH", "parserData: " + node.getWholeData());
                    mListUrl.addAll(ScriptParser.extractLinks(node.getWholeData()));
                    updateUI();
                    return;
                }
            }
        }
    }

    @UiThread
    void updateUI() {
        mPager.setAdapter(mAdapter);
    }

    /**
     * Class adapter for viewPager
     */
    private static class ReaderAdapter extends PagerAdapter {
        final LayoutInflater mLayoutInflater;
        private List<String> mUrls;

        ReaderAdapter(Context context, List<String> urls) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mUrls = urls;
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_manga_reader, container, false);
            PhotoView imgCampaign = (PhotoView) itemView.findViewById(R.id.imgContent);

            Glide.with(mLayoutInflater.getContext())
                    .load(mUrls.get(position))
                    .dontAnimate()
                    .into(imgCampaign);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
