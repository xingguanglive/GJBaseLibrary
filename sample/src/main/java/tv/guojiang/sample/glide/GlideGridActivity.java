package tv.guojiang.sample.glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import tv.guojiang.base.R;
import tv.guojiang.core.image.ApiImageLoader;
import tv.guojiang.core.image.factory.GlideFactory;

/**
 * @author leo
 */
public class GlideGridActivity extends AppCompatActivity {

    private GridView mGridView;

    private SmartRefreshLayout mRefreshLayout;

    private List<Seven> mItems = new ArrayList<>();

    private SevenAdapter mAdapter = new SevenAdapter(this);

    private String[] urls = {
        "http://img.zcool.cn/community/0173ad5a7fa9e8a8012045b3cd2dc2.jpg@1280w_1l_2o_100sh.webp",
        "http://img.zcool.cn/community/014df65a7faff6a8012045b378372f.jpg@1280w_1l_2o_100sh.webp",
        "http://img.zcool.cn/community/01d03d5a7fa9e8a8012045b387d00b.jpg@1280w_1l_2o_100sh.jpg",
        "http://img.zcool.cn/community/0163d05a7d4cc9a8012045b3d6a385.jpg@1280w_1l_2o_100sh.webp",
        "http://img.zcool.cn/community/0134695a7d4d19a8012045b31d530c.jpg@1280w_1l_2o_100sh.jpg",
        "http://img.zcool.cn/community/01dcae5a125bdda80121985c106fb0.jpg@1280w_1l_2o_100sh.webp",
        "http://img.zcool.cn/community/01c2c55a744cf6a8012134668dc927.jpg@1280w_1l_2o_100sh.webp",
        "http://img.zcool.cn/community/0188545a729c03a80121346664ce03.jpg@1280w_1l_2o_100sh.jpg",
        "http://img.zcool.cn/community/0194295a729c1fa801213466fd4285.jpg@1280w_1l_2o_100sh.jpg",
        "http://img.zcool.cn/community/01fbdf5a5dfc53a8012113c7ac2e69.png@1280w_1l_2o_100sh.webp"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiImageLoader.getInstance().setFactory(new GlideFactory());

        setContentView(R.layout.activity_grid_view);

        mGridView = findViewById(R.id.grid_view);
        mRefreshLayout = findViewById(R.id.refresh_layout);

        mGridView.setAdapter(mAdapter);

        initRefreshLayout();
    }

    private void initRefreshLayout() {

        mRefreshLayout.autoRefresh();

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mockData(true);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mockData(false);
            }
        });
    }

    private void mockData(boolean isRefresh) {
        if (isRefresh) {
            mItems.clear();
        }

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                Seven seven = new Seven();
                seven.setImageUrl(urls[i]);
                mItems.add(seven);
            }
        }

        setItems(mItems);

        if (isRefresh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadmore();
        }
    }


    public void setItems(List<Seven> items) {
        mAdapter.setData(items);
    }
}
