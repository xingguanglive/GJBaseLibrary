package tv.guojiang.core.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author KK
 * date 2017/12/22
 */
public class LogsWriter {

    private static String DISK_BASE =
        Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    private static String DISK_PATH =
        Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "/guojiang/log/";

    private static String FILE_NAME = "_logs.txt";
    private static String DIRECTORY_NAME = "guojiang";

    private LogsWriterHandler mHandler;
    private static final long DELETE_TIME = 7 * 24 * 60 * 60 * 1000;
    private StringBuffer mStringBuffer = new StringBuffer(1024);

    private static String FILE_FIRST_NAME;
    private static long FILE_LENGH = 1024 * 1024;

    public LogsWriter() {
        HandlerThread thread = new HandlerThread("LogsWriterHandler");
        thread.start();
        mHandler = new LogsWriterHandler(thread.getLooper());
        deleteFile();
        FILE_FIRST_NAME = System.currentTimeMillis() + "";
    }

    public LogsWriter(String path) {
        this();
        DISK_PATH = DISK_BASE + "/" + path + "/log/";
    }

    public void logs(String tag, String msg) {

        mHandler.sendMessage(creatMsg(tag, msg));
    }

    private Message creatMsg(String tag, String msg) {
        return mHandler.obtainMessage(0, new Date() + " ------ " + tag + " ------ " + msg + "\r");
    }

    /**
     * 删除7天日志
     */
    private void deleteFile() {
        File fileMK = new File(DISK_PATH);
        File[] files = fileMK.listFiles();
        if (null == files) {
            return;
        }
        for (File f : files) {
            if (f.getName().indexOf("_") < 0) {
                break;
            }
            String fileTime = f.getName().substring(0, f.getName().indexOf("_"));
            try {
                Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fileTime);
                Date nowDate = new Date();
                if (nowDate.getTime() - fdate.getTime() > DELETE_TIME) {
                    f.delete();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    class LogsWriterHandler extends Handler {

        FileWriter fw;
        BufferedWriter bw;

        public LogsWriterHandler(Looper looper) {
            super(looper);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            write(msg.obj.toString());

        }

        private void write(String msg) {
            try {
                fw = new FileWriter(getFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(msg);
                bw.close();
                fw.close();
                fw = null;
            } catch (IOException e) {
                e.printStackTrace();
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        private File getFile() {
            String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String name = time + "_" + FILE_FIRST_NAME + FILE_NAME;
            String path = DISK_PATH + name;
            File fileMkd = new File(DISK_PATH);
            if (!fileMkd.exists()) {
                fileMkd.mkdirs();
            }
            File file = new File(path);
            if (getFileSize(file) > FILE_LENGH) {
                FILE_FIRST_NAME = System.currentTimeMillis() + "";
            }
            return file;
        }

        public long getFileSize(File file) {
            long size = 0;
            if (file.exists()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    size = fis.available();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return size;
        }

    }


}
