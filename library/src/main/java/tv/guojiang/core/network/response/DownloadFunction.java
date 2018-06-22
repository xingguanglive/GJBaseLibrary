package tv.guojiang.core.network.response;

import io.reactivex.functions.Function;
import java.io.File;
import okhttp3.ResponseBody;
import tv.guojiang.core.util.FileUtils;

/**
 * 文件下载的Function。将{@link ResponseBody}转换成{@link File}
 *
 * @author leo
 */
public class DownloadFunction implements Function<ResponseBody, File> {

    private File mFile;

    public DownloadFunction(File file) {

        if (file == null) {
            throw new NullPointerException("download file can not be null !!!");
        }

        mFile = file;
    }

    @Override
    public File apply(ResponseBody responseBody) {
        boolean result = FileUtils.writeFile(responseBody.byteStream(), mFile.getAbsolutePath(), true);
        if (!result) {
            // 文件保存失败
            throw new IllegalArgumentException(
                "Save file to `" + mFile.getAbsolutePath() + "`failed");
        }
        return mFile;
    }
}
