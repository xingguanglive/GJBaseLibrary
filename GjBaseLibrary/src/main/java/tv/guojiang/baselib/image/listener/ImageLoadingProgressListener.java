package tv.guojiang.baselib.image.listener;

import android.view.View;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface ImageLoadingProgressListener {
	void onProgressUpdate(String imageUri, View view, int current, int total);
}
