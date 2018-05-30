package tv.guojiang.core.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 文件工具类.
 * <p>
 * <pre>
 * 使用时注意权限的添加：
 * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
 * </pre>
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    public static final String CACHE_DIR = "cache";

    public static final String ICON_DIR = "icon";

    public static final String API_CORE = "core";

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取缓存目录
     *
     * @return 若sdcard存在，返回 /sdcard/Android/data/application package/cache/ <p>
     * 若sdcard不存在，返回/data/data/application package/cache/cache/
     */
    public static String getCacheDir(Context context) {
        return getDir(context, CACHE_DIR);
    }

    public static String getApiCacheDir(Context context) {
        return getDir(context, API_CORE);
    }

    /**
     * 获取icon目录
     *
     * @return 若sdcard存在，返回 /sdcard/Android/data/application package/icon/
     * <p>若sdcard不存在，返回/data/data/application package/cache/icon/
     */
    public static String getIconDir(Context context) {
        return getDir(context, ICON_DIR);
    }

    /**
     * 获取应用目录
     *
     * @return 若sdcard存在，返回 /sdcard/Android/data/application package/ <p>若sdcard不存在，返回/data/data/application
     * package/cache/
     */
    public static String getDir(Context context, String name) {
        File dir;
        if (isSDCardAvailable()) {
            File externalStorageFile = getExternalStorageFile(context);
            dir = new File(externalStorageFile, name);
        } else {
            dir = new File(getInnerCacheDir(context), name);
        }
        dir.mkdirs();
        return dir.getAbsolutePath();
    }

    /**
     * @return /sdcard/Android/data/application package/
     */
    public static File getExternalStorageFile(Context context) {
        File externalCacheDir = context.getExternalCacheDir();

        if (externalCacheDir == null) {
            return null;
        }

        return externalCacheDir.getParentFile();
    }

    /**
     * 返回 /sdcard/DCIM/<app name>/
     * <p>一般用来存储图片等，此处存储的图片或者视频在相册中可以看到
     *
     * @return /sdcard/DCIM/<app name>/
     */
    public static String getAndroidOpenDir(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append("DCIM");
        sb.append(File.separator);
        sb.append(context.getApplicationInfo().name);
        sb.append(File.separator);

        File file = new File(sb.toString());

        if (!file.exists()) {
            file.mkdirs();
        }

        return sb.toString();
    }

    /**
     * @return /data/data/application package/cache/
     */
    public static File getInnerCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        return copyFile(srcFile, destFile, deleteSrc);
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) {
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = in.read(buffer)) > 0) {
                out.write(buffer, 0, i);
                out.flush();
            }
            if (deleteSrc) {
                srcFile.delete();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        } finally {
            IOUtils.close(out);
            IOUtils.close(in);
        }
        return true;
    }

    /**
     * 判断文件是否可写
     */
    public static boolean isWriteable(String path) {
        try {
            if (TextUtils.isEmpty(path)) {
                return false;
            }
            File f = new File(path);
            return f.exists() && f.canWrite();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 修改文件的权限,例如"777"等
     */
    public static void chmod(String path, String mode) {
        try {
            String command = "chmod " + mode + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 把数据写入文件
     *
     * @param is 数据流
     * @param path 文件路径
     * @param recreate 如果文件存在，是否需要删除重建
     * @return 是否写入成功
     */
    public static boolean writeFile(InputStream is, String path, boolean recreate) {
        boolean res = false;
        File f = new File(path);
        FileOutputStream fos = null;
        try {
            if (recreate && f.exists()) {
                f.delete();
            }
            if (!f.exists() && null != is) {
                File parentFile = new File(f.getParent());
                parentFile.mkdirs();
                int count = -1;
                byte[] buffer = new byte[1024];
                fos = new FileOutputStream(f);
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                res = true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(fos);
            IOUtils.close(is);
        }
        return res;
    }

    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path 文件路径名称
     * @param append 是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(byte[] content, String path, boolean append) {
        boolean res = false;
        File f = new File(path);
        RandomAccessFile raf = null;
        try {
            if (f.exists()) {
                if (!append) {
                    f.delete();
                    f.createNewFile();
                }
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(raf.length());
                raf.write(content);
                res = true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(raf);
        }
        return res;
    }

    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path 文件路径名称
     * @param append 是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(String content, String path, boolean append) {
        return writeFile(content.getBytes(), path, append);
    }

    /**
     * 把字符串数据写入文件
     * <p>
     * <p>主要用于缓存json数据
     */
    public static void writeFile(String content, String path) {
        File file = new File(path);
        File parentFile = file.getParentFile();

        BufferedWriter writer = null;
        FileWriter fileWriter = null;
        try {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(writer);
            IOUtils.close(fileWriter);
        }
    }

    /**
     * 把键值对写入文件
     *
     * @param filePath 文件路径
     * @param key 键
     * @param value 值
     * @param comment 该键值对的注释
     */
    public static void writeProperties(String filePath, String key, String value, String comment) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(filePath)) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis); // 先读取文件，再把键值对追加到后面
            p.setProperty(key, value);
            fos = new FileOutputStream(f);
            p.store(fos, comment);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(fis);
            IOUtils.close(fos);
        }
    }

    /**
     * 根据值读取
     */
    public static String readProperties(String filePath, String key, String defaultValue) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(fis);
        }
        return value;
    }

    /**
     * 把字符串键值对的map写入文件
     */
    public static void writeMap(
        String filePath, Map<String, String> map, boolean append, String comment) {
        if (map == null || map.size() == 0 || TextUtils.isEmpty(filePath)) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            Properties p = new Properties();
            if (append) {
                fis = new FileInputStream(f);
                p.load(fis); // 先读取文件，再把键值对追加到后面
            }
            p.putAll(map);
            fos = new FileOutputStream(f);
            p.store(fos, comment);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(fis);
            IOUtils.close(fos);
        }
    }

    /**
     * 把字符串键值对的文件读入map
     */
    public static Map<String, String> readMap(String filePath, String defaultValue) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        Map<String, String> map = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            map = new HashMap<>((Map) p); // 因为properties继承了map，所以直接通过p来构造一个map
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.close(fis);
        }
        return map;
    }

    /**
     * 改名
     */
    public static boolean copy(String src, String des, boolean delete) {
        File file = new File(src);
        if (!file.exists()) {
            return false;
        }
        File desFile = new File(des);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                out.flush();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        } finally {
            IOUtils.close(in);
            IOUtils.close(out);
        }
        if (delete) {
            file.delete();
        }
        return true;
    }

    /**
     * 读取文件为String.
     * <p>
     * <p>主要用于读取json缓存
     *
     * @param path 文件的地址
     */
    public static String readFile(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return "";
        }

        BufferedReader reader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            return reader.readLine();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        } finally {
            IOUtils.close(fileReader);
            IOUtils.close(reader);
        }
    }

    /**
     * 检查外部存储器是否可以被移除.
     *
     * @return true - 外部存储器可以被移除(例如SD卡)<br> false - 不可移除
     */
    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String fileAbsolutePath) {

        if (TextUtils.isEmpty(fileAbsolutePath)) {
            return;
        }

        File file = new File(fileAbsolutePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static String formatSize(Context context, long length) {
        return Formatter.formatFileSize(context, length);
    }

    //    /**
    //     * 将bitmap保存为图片
    //     */
    //    public static File saveBitmapFile(Bitmap bitmap, String fileName) {
    //        File file = new File(PictureUtils.getAlbumDir(), fileName);
    //        BufferedOutputStream bos = null;
    //        FileOutputStream outputStream = null;
    //        try {
    //            outputStream = new FileOutputStream(file);
    //            bos = new BufferedOutputStream(outputStream);
    //            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
    //            bos.flush();
    //        } catch (IOException e) {
    //            Logger.e(e.getMessage(), e);
    //        } finally {
    //            IOUtils.close(bos);
    //            IOUtils.close(outputStream);
    //        }
    //
    //        return file;
    //    }

    /**
     * 获取文件的MD5值
     */
    public static String fileMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(
                filePath); // Create an FileInputStream instance according to the
            // filepath
            byte[] buffer = new byte[1024]; // The buffer to read the file
            MessageDigest digest = MessageDigest.getInstance("MD5"); // Get a MD5 instance
            int numRead = 0; // Record how many bytes have been read
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0) {
                    digest.update(buffer, 0, numRead); // Update the digest
                }
            }
            byte[] md5Bytes = digest.digest(); // Complete the hash computing
            return convertHashToString(md5Bytes); // Call the function to convert to hex digits
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        } finally {
            IOUtils.close(inputStream);
        }
    }

    /**
     * Convert the hash bytes to hex digits string
     *
     * @return The converted hex digits string
     */
    private static String convertHashToString(byte[] hashBytes) {
        String returnVal = "";
        for (int i = 0; i < hashBytes.length; i++) {
            returnVal += Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal.toLowerCase();
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取文件的后缀名
     */
    public static String getFileSuffix(File file) {
        if (file == null) {
            return "";
        }
        String fileName = file.getName();
        int index = fileName.indexOf(".");
        if (index == -1 || index == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(index, fileName.length());
    }
}
