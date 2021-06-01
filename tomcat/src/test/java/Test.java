import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IOException {

        URL resource = Class.forName( "com.gupaoedu.vip.netty.tomcat.servlet.FirstServlet" ).getClassLoader()
                .getResource( "" );
        System.out.println(resource);

        File file = new File( "D:\\workspace\\tomcat\\src\\main\\resources\\webroot\\chat.html" );
        String s = FileUtils.readFileToString( file );
        System.out.println(s);


    }
}
