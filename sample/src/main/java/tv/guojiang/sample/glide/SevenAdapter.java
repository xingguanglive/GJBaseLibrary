package tv.guojiang.sample.glide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import tv.guojiang.base.R;
import tv.guojiang.core.image.ApiImageLoader;

/**
 * @author leo
 */
public class SevenAdapter extends BaseAdapter {

    private List<Seven> mUrls = new ArrayList<>();

    private Context mContext;

    public SevenAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return mUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<Seven> urls) {
        mUrls = urls;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_seven, null);

            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {
            convertView.setPadding(30, 0, 0, 0);
        } else {
            convertView.setPadding(0, 0, 30, 0);
        }

        String url = mUrls.get(position).getImageUrl();

        ApiImageLoader.getInstance().newBuilder().imageUrl(url)
            .errorImage(R.mipmap.ic_launcher).loadingImage(R.mipmap.ic_launcher)
            .into(mContext, holder.imageView);

        return convertView;
    }

    static class ViewHolder {

        ImageView imageView;
    }
}
