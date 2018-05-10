
package tv.guojiang.helper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/**
 * 选择照片或调用相机拍照 <b>target:24未做适配</b>
 * <p>
 * Manifest文件中要求
 * <pre>
 * <manifest ... >
 *      <uses-feature android:name="android.hardware.camera"
 *      android:required="true" />
 *
 *      <application>
 *           ...
 *           <provider
 *           android:name="android.support.v4.content.FileProvider"
 *           android:authorities="com.yidiandao.business.fileprovider"
 *           android:exported="false"
 *           android:grantUriPermissions="true">
 *           <meta-data
 *           android:name="android.support.FILE_PROVIDER_PATHS"
 *           android:resource="@xml/file_paths"></meta-data>
 *           </provider>
 *           ...
 *      </application>
 *
 *      ...
 * </manifest>
 *
 * 创建文件 res/xml/file_paths.xml
 * <code>
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <paths xmlns:android="http://schemas.android.com/apk/res/android">
 *      <external-files-path
 *      name="Pictures"
 *      path="Pictures/"/>
 * </paths>
 *
 * </pre>
 * <p>
 * Permission
 * <p>
 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
 * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
 * <p>
 * Created by ChenTao(chentao7v@gmail.com) on 2017-06-29 10:07
 */
public class TakePhotoHelper {

    public static final String TAG = "TakePhoto";

    private static final int REQUEST_TAKE_CAMERA = 1;

    private static final int REQUEST_TAKE_GALLERY = 2;

    private static final int REQUEST_TAKE_CROP = 3;

    private Activity mActivity;

    private File mTakePhotoFile;

    private Uri mPhotoURI;

    private boolean mCropImage;

    public TakePhotoHelper(Activity activity) {
        mActivity = activity;
    }

    public void openCamera() {
        openCamera(false);
    }

    public void openGallery() {
        openGallery(false);
    }

    /**
     * 打开相机
     *
     * @param cropImage 是否裁剪图片
     */
    public void openCamera(boolean cropImage) {
        mCropImage = cropImage;
        dispatchOpenCameraIntent();
    }

    private void dispatchOpenCameraIntent() {
        try {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mTakePhotoFile = createImageFile();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mPhotoURI = FileProvider
                    .getUriForFile(mActivity, "com.yidiandao.business.fileprovider",
                        mTakePhotoFile);
            } else {
                mPhotoURI = Uri.fromFile(mTakePhotoFile);
            }
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
            mActivity.startActivityForResult(openCameraIntent, REQUEST_TAKE_CAMERA);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 打开相册
     *
     * @param cropImage 是否裁剪图片
     */
    public void openGallery(boolean cropImage) {
        mCropImage = cropImage;
        dispatchOpenGalleryIntent();
    }

    private void dispatchOpenGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, REQUEST_TAKE_GALLERY);
    }

    /**
     * 处理onActivityResult
     * <p>
     * 注意：裁剪图片时会回掉两次
     */
    public File onActivityResult(int requestCode, int resultCode, Intent data) {
        File file = null;
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_TAKE_GALLERY:
                        if (mCropImage) {
                            mTakePhotoFile = getPhotoUri(data);
/*                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mPhotoURI = FileProvider.getUriForFile(mActivity, "com.yidiandao.business.fileprovider", mTakePhotoFile);
                            } else {*/
                            mPhotoURI = Uri.fromFile(mTakePhotoFile);
                            //                            }
                            cropPhoto();
                        } else {
                            file = getPhotoUri(data);
                        }
                        break;
                    case REQUEST_TAKE_CAMERA:
                        if (mCropImage) {
                            cropPhoto();
                        } else {
                            file = mTakePhotoFile;
                        }
                        break;
                    case REQUEST_TAKE_CROP:
                        file = mTakePhotoFile;
                        break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        Log.d(TAG, "inner --> " + ((file == null || file.length() == 0) ? "null"
            : file.getAbsolutePath()));

        return file;
    }

    private void cropPhoto() {
        try {
            mTakePhotoFile = createImageFile();
            //缩略图保存地址
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(mPhotoURI, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTakePhotoFile));
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            mActivity.startActivityForResult(intent, REQUEST_TAKE_CROP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = DateFormat.format("yyMMdd_HHmmss", System.currentTimeMillis())
            .toString();
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File imagePath = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        return File.createTempFile(imageFileName, ".jpg", imagePath);
    }

    private File getPhotoUri(Intent data) {
        Cursor cursor = null;
        String picturePath = null;
        try {
            Uri imageUri = data.getData();
            String imageStr = imageUri.toString();
            if (imageStr.startsWith("file")) {
                picturePath = imageStr.substring(7, imageStr.length());
            } else {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                cursor = mActivity.getContentResolver()
                    .query(imageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                picturePath = cursor.getString(columnIndex);
            }
            return new File(picturePath);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
