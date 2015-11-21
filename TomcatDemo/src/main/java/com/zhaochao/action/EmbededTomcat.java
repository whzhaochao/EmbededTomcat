package com.zhaochao.action;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class EmbededTomcat {
	private final Log log = LogFactory.getLog(getClass());
	private static String PROJECT_NAME = "TomcatDemo";
	private static String CONTEXT_PATH = "/";
	private static String PROJECT_PATH = System.getProperty("user.dir");
	private static String WEB_APP_PATH = PROJECT_PATH + File.separatorChar + "target" + File.separatorChar
			+ PROJECT_NAME;
	private static String CATALINA_HOME = PROJECT_PATH + File.separatorChar + "target" + File.separatorChar
			+ PROJECT_NAME + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar
			+ "tomcat-home";
	private static String ENCODING = "utf-8";
	private static Tomcat tomcat = new Tomcat();
	private int port;

	public EmbededTomcat(int port) {
		this.port = port;
		log.info(WEB_APP_PATH);
		log.info(CATALINA_HOME);
	}

	public void start() throws Exception {
		tomcat.setPort(port);
		tomcat.setBaseDir(CATALINA_HOME);
		tomcat.getHost().setAppBase(WEB_APP_PATH);
		// 支持自动部署，默认就是true
		tomcat.getHost().setAutoDeploy(true);
		// tomcat.enableNaming();//执行这句才能支持JDNI查找
		tomcat.getConnector().setURIEncoding(ENCODING);
		tomcat.getConnector().setProtocolHandlerClassName("rg.apache.coyote.http11.Http11NioProtocol");
		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);
		Context ctx = tomcat.addContext(CONTEXT_PATH, WEB_APP_PATH);
		ContextConfig ctxCfg = new ContextConfig();
		ctx.addLifecycleListener(ctxCfg);
		ctxCfg.setDefaultWebXml(null);
		tomcat.start();
		tomcat.getServer().await();
		log.info("Tomcat started.");
	}

	public void stop() throws Exception {
		tomcat.stop();
		log.info("Tomcat stoped");
	}

	public static void main(String[] args) throws Exception {

		if (args.length == 2) {
			PROJECT_PATH = args[0];
			int port = Integer.parseInt(args[1]);
			WEB_APP_PATH = PROJECT_PATH  + File.separatorChar + PROJECT_NAME;
			CATALINA_HOME = PROJECT_PATH  + File.separatorChar + PROJECT_NAME
					+ File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar
					+ "tomcat-home";
			System.out.println("PROJECT_PATH:" + PROJECT_PATH + "\nport=" + port + "\nWEB_APP_PATH=" + WEB_APP_PATH
					+ "\nCATALINA_HOME=" + CATALINA_HOME);
			EmbededTomcat embededTomcat = new EmbededTomcat(port);
			embededTomcat.start();
		} else {
			EmbededTomcat embededTomcat = new EmbededTomcat(80);
			embededTomcat.start();
		}
	}

}
