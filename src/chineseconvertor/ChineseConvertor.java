package chineseconvertor;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ming
 */
public class ChineseConvertor {

    private static final String S2T = "s2t";
    private static final String T2S = "t2s";
    private static Charset charset = Charset.defaultCharset();
    private static String type = S2T;

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            echo("usage:java -jar ChineseConvertor.jar source dest");
            echo("you can add argument \"--charset\" like \"UTF-8\"");
            echo("you can add argument \"--type\" like \"s2t\" or \"t2s\"");
            return;
        }
        ArrayList<String> paths = new ArrayList<String>();
        int index = 0;
        while (index < args.length) {
            String arg = args[index];
            if (arg.toLowerCase().equals("--charset")) {
                index++;
                if (index < args.length) {
                    charset = Charset.forName(args[index]);
                }
                index++;
            } else if (arg.toLowerCase().equals("--type")) {
                index++;
                if (index < args.length) {
                    charset = Charset.forName(args[index]);
                }
                index++;
            } else {
                paths.add(arg);
                index++;
            }
        }
        if (paths.size() != 2) {
            echo("missing path param not right...");
            return;
        }
        if (!S2T.equals(type) && !T2S.equals(type)) {
            echo("unknow convert type...");
            return;
        }
        String source = paths.get(0);
        String dest = paths.get(1);
        boolean s2t = S2T.equals(type);
        if (s2t) {
            echo("convert simple chinese to traditional chinese...");
        } else {
            echo("convert traditional chinese to simple chinese...");
        }
        try {
            int count = 0;
            InputStreamReader reader = new InputStreamReader(new FileInputStream(source), charset);
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dest), charset);
                try {
                    int ch;
                    while ((ch = reader.read()) != -1) {
                        writer.append(s2t ? Chinese.s2t((char) ch) : Chinese.t2s((char) ch));
                        count++;
                    }
                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
            echo("convert " + count + " character(s)...");
        } catch (IOException ex) {
            Logger.getLogger(ChineseConvertor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }
}
