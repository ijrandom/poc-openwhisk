package poc.openwhisk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestApp {

    public static final int COUNT = 100;
    public static final int THREAD_COUNT = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        long ss = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {
            int n = i;
            executorService.submit(() -> {
                execute(n);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        long ee = System.currentTimeMillis();
        System.out.println("Total: " + (ee - ss) / COUNT);
    }

    private static void execute(int n) {
        try {
            Process process = Runtime.getRuntime().exec("./wsk -i action invoke -rb  poc-openwhisk");
            dumpStream(process.getInputStream(), System.out);
            dumpStream(process.getErrorStream(), System.err);
            process.waitFor();
            System.out.println(process.exitValue());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void dumpStream(InputStream is, OutputStream os) {
        byte[] buf = new byte[1024];
        int read;

        try {
            while ((read = is.read(buf)) > 0) {
                os.write(buf, 0, read);
            }
        } catch (IOException e) {
            //
        }
    }
}
