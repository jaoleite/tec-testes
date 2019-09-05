package br.com.tecconcursos.tomcat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class StartTomcat {

	public StartTomcat() {
	}

	public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

	public void metodoUm() throws RuntimeException {
		try {
			String webappDirLocation = "src/main/webapp/";
			Tomcat tomcat = new Tomcat();
	
			// The port that we should run on can be set into an environment
			// variable
			// Look for that variable and default to 8080 if it isn't there.
			String webPort = System.getenv("PORT");
			if (webPort == null || webPort.isEmpty()) {
				webPort = "80";
			}
	
			tomcat.setPort(Integer.valueOf(webPort));
			StandardContext ctx = (StandardContext) tomcat.addWebapp("/tec-site", new File(webappDirLocation).getAbsolutePath());
			
			//System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
	
			// Declare an alternative location for your "WEB-INF/classes" dir
			// Servlet 3.0 annotation will work
			File additionWebInfClasses = new File("target/tec-site/WEB-INF/classes");
			//File additionWebInfClasses = new File("src/main/webapp/WEB-INF/classes");
			WebResourceRoot resources = new StandardRoot(ctx);
			
			
			DirResourceSet dirResourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/");
			System.out.println();
			resources.addPreResources(dirResourceSet);
			ctx.setResources(resources);
			
			tomcat.getHost().setAppBase("webapps");
			tomcat.getHost().setAutoDeploy(true);
			tomcat.getHost().setName("localhost");
			System.out.println(tomcat.getHost().getConfigClass());
			
			//tomcat.addWebapp("/tec-site", ctx.getDocBase());
			/*try {
				tomcat.start();
				
				//tomcat.getServer().start();
			} catch (LifecycleException e) {
				throw new RuntimeException(e);
			}
			 //tomcat.addWebapp(ctx.get, new File("./" +webappDirLocation).getAbsolutePath());
			 //System.out.println(tomcat.getService().);
			tomcat.getServer().await();*/
		
		} catch (ServletException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final static Logger LOGGER = Logger.getLogger(StartTomcat.class.getName());
	private final static String mWorkingDir = System.getProperty("java.io.tmpdir");
	private static Tomcat tomcat = null;

	public void metodoDois() throws RuntimeException {
		/*
		 * String contextPath = "/"; String appBase = "."; Tomcat tomcat = new
		 * Tomcat(); tomcat.setPort(Integer.valueOf(port.orElse("8080") ));
		 * tomcat.getHost().setAppBase(appBase); System.out.println(appBase);
		 * tomcat.addWebapp(contextPath, appBase); tomcat.start();
		 * tomcat.getServer().await();
		 */
		
		tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.setBaseDir(mWorkingDir);
		tomcat.getHost().setAppBase(mWorkingDir);
		tomcat.getHost().setAutoDeploy(true);
		tomcat.getHost().setDeployOnStartup(true);
		 
		 try {
			 tomcat.start();
		} catch (LifecycleException e) {
			LOGGER.severe("Tomcat could not be started.");
			e.printStackTrace();
		}
		LOGGER.info("Tomcat started on " + tomcat.getHost());
		 
		// Alternatively, you can specify a WAR file as last parameter in the following call e.g. "C:\\Users\\admin\\Desktop\\app.war"
		Context appContext = StartTomcat.getTomcat().addWebapp(StartTomcat.getTomcat().getHost(), "/tec-site", "D:\\Desenvolvimento\\git\\tec2.0\\tec-site\\src\\main\\webapp\\");
		LOGGER.info("Deployed " + appContext.getBaseName() + " as " + appContext.getBaseName());
		 
		tomcat.getServer().await();
	}
	
	public static Tomcat getTomcat() {
		return tomcat;
	}

	public void metodoTres() throws RuntimeException {
		try {
			String docBase = "src/main/webapp/WEB-INF/classes/";
	
			Tomcat tomcat = new Tomcat();
			String webPort = System.getenv("PORT");
			if (webPort == null || webPort.isEmpty()) {
				webPort = "8080";
			}
			tomcat.setPort(Integer.valueOf(webPort));
	
			tomcat.addWebapp("/", new File(docBase).getAbsolutePath());
			System.out.println("configuring app with basedir: " + new File("./" + docBase).getAbsolutePath());
		
			tomcat.getServer().init();
			tomcat.start();
		} catch (LifecycleException | ServletException e) {
			throw new RuntimeException(e);
		}
		tomcat.getServer().await();
	}
	
	public void metodoQuatro() throws RuntimeException {
		Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
         
        String contextPath = "/go";
        String docBase = new File(".").getAbsolutePath();
         
        Context context = tomcat.addContext(contextPath, docBase);
         
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();
                 
                writer.println("<html><title>Welcome</title><body>");
                writer.println("<h1>Have a Great Day!</h1>");
                writer.println("</body></html>");
            }
        };
         
        String servletName = "Servlet1";
        String urlPattern = "/go";
        
        tomcat.addServlet(contextPath, servletName, servlet);      
        context.addServletMappingDecoded(urlPattern, servletName);
        
        System.out.println(context.getPath());
        
        try {
			tomcat.start();
		} catch (LifecycleException e) {
			throw new RuntimeException();
		}
        tomcat.getServer().await();
	}

	public static void main(String[] args) throws Exception {
		Consumer<StartTomcat> consumer = Metodo.METODO_1.getConsumer();
		consumer.accept(new StartTomcat());
	}

	public enum Metodo {
		METODO_1(StartTomcat::metodoUm),
		METODO_2(StartTomcat::metodoDois),
		METODO_3(StartTomcat::metodoTres),
		METODO_4(StartTomcat::metodoQuatro),
		;

		private Consumer<StartTomcat> consumer;

		private Metodo(Consumer<StartTomcat> consumer) {
			this.consumer = consumer;
		}

		public Consumer<StartTomcat> getConsumer() {
			return consumer;
		}

	}

}
