package com.gupaoedu.vip.netty.tomcat.servlet;

import ch.qos.logback.core.util.FileUtil;
import com.gupaoedu.vip.netty.tomcat.http.GPRequest;
import com.gupaoedu.vip.netty.tomcat.http.GPResponse;
import com.gupaoedu.vip.netty.tomcat.http.GPServlet;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class FirstServlet extends GPServlet {

	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(GPRequest request, GPResponse response) throws Exception {
		File file = new File( "D:\\workspace\\tomcat\\src\\main\\resources\\webroot\\chat.html" );
		String s = FileUtils.readFileToString( file );
		response.write(s);
	}
}
