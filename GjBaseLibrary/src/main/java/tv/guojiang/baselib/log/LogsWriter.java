package tv.guojiang.baselib.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.io.File;
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

    private static String DISK_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "guojiang/log/";
    private static String FILE_NAME = "_logs.txt";

    private LogsWriterHandler mHandler;

    public LogsWriter() {
        HandlerThread thread = new HandlerThread("LogsWriterHandler");
        thread.start();
        mHandler = new LogsWriterHandler(thread.getLooper());
        deleteFile();
    }

    public void logs(String tag, String msg) {

        mHandler.sendMessage(creatMsg(tag, msg));
    }

    private Message creatMsg(String tag, String msg) {
        return mHandler.obtainMessage(0, new Date() + " ------ " + tag + " ------------- " + msg + "\r");
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
                if (nowDate.getTime() - fdate.getTime() > 7 * 24 * 60 * 60 * 1000) {
                    f.delete();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    class LogsWriterHandler extends Handler {
        FileWriter fileWriter;

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
                fileWriter = new FileWriter(getFile(), true);
                fileWriter.append(msg);
                fileWriter.flush();
                fileWriter.close();
                fileWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                        fileWriter = null;
                    } catch (IOException e1) {
                    }
                }
            }
        }

        private File getFile() {
            String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String name = time + FILE_NAME;
            String path = DISK_PATH + name;
            File fileMkd = new File(DISK_PATH);
            if (!fileMkd.exists()) {
                fileMkd.mkdirs();
            }
            File file = new File(path);
            return file;
        }

    }


}
