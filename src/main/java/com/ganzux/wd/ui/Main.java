package com.ganzux.wd.ui;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
/**
 * Main class
 * @author ganzux
 */
@PropertySource("classpath:config.properties")
@SpringBootApplication
public class Main {
	///////////////////////////////////////////////////////////////
	//                        Attributes                         //
	///////////////////////////////////////////////////////////////
	private static Log log = LogFactory.getLog(Main.class);
	///////////////////////////////////////////////////////////////
	//                        /Attributes                        //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////
	/**
	 * Main method to run the App
	 * @param args
	 */
    public static void main(String[] args) {
    	log.debug("Init Main...");
        SpringApplication.run(Main.class, args);
    }
    
    /**
     * Method to override basic error pages
     * @return containerCustomizer
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
     
        return new EmbeddedServletContainerCustomizer() {
        	@Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error404Page = 
                		new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.htm");
                ErrorPage error405Page = 
                		new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405.htm");
     
                container.addErrorPages(error404Page, error405Page);
            }
        };
    }
	///////////////////////////////////////////////////////////////
	//                      /Public Methods                      //
	///////////////////////////////////////////////////////////////
}
