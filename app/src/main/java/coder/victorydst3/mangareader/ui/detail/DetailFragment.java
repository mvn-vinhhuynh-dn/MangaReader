package coder.victorydst3.mangareader.ui.detail;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.BaseListFragment;
import coder.victorydst3.mangareader.Common.Constant;
import coder.victorydst3.mangareader.LoadMoreAdapter;
import coder.victorydst3.mangareader.OnReadMangaListener;
import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.Chapter;
import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.model.MangaDetail;
import coder.victorydst3.mangareader.ui.reader.ReaderActivity_;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */
@EFragment(R.layout.fragment_detail)
public class DetailFragment extends BaseListFragment<LoadMoreAdapter> implements OnReadMangaListener {
    private static final String TAG = DetailFragment.class.getSimpleName();

    @FragmentArg
    Parcelable mManga;

    private Manga mData;

    private MangaDetail mDetail = new MangaDetail();
    private List<Chapter> mChapters = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void afterView() {
        super.afterView();
        mData = Parcels.unwrap(mManga);
        getDetail();
    }

    @Background
    void getDetail() {
        mIsLoading = true;
        mFinished = false;
        Document document = null;
        try {
            document = Jsoup.connect(Constant.BASE_URL + mData.getProcessUrl())
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document != null) {
            parserData(document);
        }
    }

    private void parserData(Document document) {
        // Get chapters
        Elements div = document.select("div.boxpageleft");
        for (int i = 0; i < div.size(); i++) {
            if (div.get(i).getElementById("listChapterBlock") != null) {
                Element element = div.get(i).select("div.divtab_list").first();
                Element ul = element.select("ul.ul_listchap").first();
                Elements lis = ul.select("li");
                for (int j = 0; j < lis.size(); j++) {
                    String title = lis.get(j).select("a").attr("title");
                    String process = lis.get(j).select("a").attr("href");
                    Chapter chapter = Chapter.builder().processUrl(process).title(title).build();
                    mChapters.add(chapter);
                }
            }
        }
        // Get detail
        Element element = div.select("div.divtab_list").first();
        Element ul = element.select("ul.ulpro_line").first();
        Elements lis = ul.select("li");
        Element div_divthum = lis.get(0).select("div.divthum2").first();
        Element div_divListtext = lis.get(0).select("div.divListtext").first();
        String imgThub = div_divthum.select("a").select("img").attr("src");

        Element ul_ullist_item = div_divListtext.select("ul.ullist_item").first();
        Elements lis_detail = ul_ullist_item.select("li");
        String status = lis_detail.get(4).select("div.item2").text();
        mData.setStatus(status);
        mData.setImageUrl(imgThub);
        mDetail.setChapters(mChapters);
        mDetail.setManga(mData);
        UpdateUI();
    }

    @UiThread
    void UpdateUI() {
        mFinished = true;
        mIsLoading = false;
        getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        onPreRequest(false);
        getDetail();
    }

    @Override
    public void onReadMangaClick(String url) {
        Log.d(TAG, "onReadMangaClick: " + url);
        ReaderActivity_.intent(this).mUrl(url).start();
    }

    @Override
    protected LoadMoreAdapter initAdapter() {
        return new LoadMoreAdapter(getActivity(), new DetailAdapter(getActivity(), mDetail, this));
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        return mLinearLayoutManager;
    }
}
