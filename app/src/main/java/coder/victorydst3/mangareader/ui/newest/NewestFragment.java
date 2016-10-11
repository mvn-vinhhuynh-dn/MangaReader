package coder.victorydst3.mangareader.ui.newest;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.BaseFragment;
import coder.victorydst3.mangareader.Common.Constant;
import coder.victorydst3.mangareader.MainActivity;
import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.containerFragment.NewestContainerFragment_;
import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.ui.detail.DetailFragment_;
import coder.victorydst3.mangareader.widget.SpacesItemDecoration;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */
@EFragment(R.layout.fragment_newest)
public class NewestFragment extends BaseFragment implements ListMangaAdapter.OnItemMangaClickListenner {
    private static final int NUM_OF_COLUMN = 3;
    @FragmentArg
    String mFragmentTag;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String NEWEST_DOMAIN = "truyen-moi-nhat.html";

    private ListMangaAdapter mAdapter;
    private List<Manga> mData = new ArrayList<>();

    @Override
    protected void afterView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMN);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        mAdapter = new ListMangaAdapter(getActivity(), mData, this);
        mRecyclerView.setAdapter(mAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position != 0) {
                    return 1;
                }
                return NUM_OF_COLUMN;
            }
        });
        loadData();
    }

    @Background
    void loadData() {
        Document document = null;
        try {
            document = Jsoup.connect(Constant.BASE_URL + NEWEST_DOMAIN)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document != null) {
            parserData(document);
        }
    }

    private void parserData(Document document) {
        Element element = document.select("div.divtab_list").first();
        Elements ul = element.select("ul.ulListruyen");
        // Get list [li] from html code
        Elements lis = ul.select("li");
        // Parser data...
        if (lis != null && lis.size() != 0) {
            for (int i = 0; i < lis.size(); i++) {
                // Get image thumb
                Element noteAtElement = lis.get(i).select("div.divthumb").first();
                Element noteAtElement2 = lis.get(i).select("div.newsContent").first();

                String avatarUrl = noteAtElement.select("img").attr("src");
                String name = noteAtElement2.select("a.tile").attr("title");
                String processUrl = noteAtElement2.select("a.tile").attr("href");

                Elements categorys = noteAtElement2.select("span");
                Elements categorys_a = categorys.get(0).select("a");

                List<String> categories = new ArrayList<>();
                for (int j = 0; j < categorys_a.size(); j++) {
                    categories.add(categorys_a.get(j).attr("title"));
                }
                String newChap = "";
                String numRead = "";
                String dateUpdate = "";
                List<String> authors = new ArrayList<>();

                if (categorys.size() == 3) {
                    Elements category_b = categorys.get(1).select("font");
                    for (int k = 0; k < category_b.size(); k++) {
                        newChap = category_b.get(0).text();
                        numRead = category_b.get(1).text();
                    }
                    dateUpdate = categorys.get(2).text();


                } else if (categorys.size() == 4) {
                    Elements category_c = categorys.get(1).select("a");
                    for (int k = 0; k < category_c.size(); k++) {
                        authors.add(category_c.get(k).attr("title"));
                    }

                    Elements category_b = categorys.get(2).select("font");
                    for (int k = 0; k < category_b.size(); k++) {
                        newChap = category_b.get(0).text();
                        numRead = category_b.get(1).text();
                    }
                    dateUpdate = categorys.get(3).text();
                }

                Manga manga = Manga.builder()
                        .name(name)
                        .author(authors)
                        .categories(categories)
                        .dateUpdate(dateUpdate)
                        .imageUrl(avatarUrl)
                        .numRead(numRead)
                        .processUrl(processUrl)
                        .newChap(newChap).build();

                mData.add(manga);
            }
        }
        UpdateUI();
    }

    @UiThread
    void UpdateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Manga manga = mData.get(position);

        if (getActivity() instanceof MainActivity) {
            Fragment fragment = ((MainActivity) getActivity()).getBaseCurrentFragment();
            if (fragment instanceof NewestContainerFragment_) {
                replaceFragment(DetailFragment_.builder().mManga(Parcels.wrap(manga)).build(), false);
            }
        }
    }
}
