package tv.guojiang.network;

import java.io.File;
import java.util.List;
import tv.guojiang.baselib.network.annotation.Upload;
import tv.guojiang.baselib.network.request.BaseRequest;

/**
 * @author leo
 */
public class UploadRequest extends BaseRequest {

    public String username;

    public String password;

    @Upload("picture")
    public List<File> file;

}
