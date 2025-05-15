package movtery;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;

public class JarExceptionCatcher {
    private static final String TAG = "[JarExceptionCatcher] ";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(TAG + "Error: No main class provided.");
            System.exit(1);
        }

        String mainClassName = args[0];
        String[] appArgs = Arrays.copyOfRange(args, 1, args.length);

        try {
            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(mainClassName);
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) appArgs);
        } catch (Throwable e) {
            System.err.println(TAG + "Exception caught during execution:\n" + getStackTraceInfo(e));
            System.exit(1);
        }
    }

    private static String getStackTraceInfo(Throwable e) {
        try (
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter)
        ) {
            e.printStackTrace(printWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            return "Error: Can't print exception, " + ex;
        }
    }
}