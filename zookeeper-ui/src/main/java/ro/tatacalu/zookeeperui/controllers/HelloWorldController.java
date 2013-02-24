/**
 * 
 */
package ro.tatacalu.zookeeperui.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Matei
 *
 */
@Controller
public class HelloWorldController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);
	
	/**
	 * 
	 */
	public HelloWorldController() {
		super();
	}
	
	@RequestMapping(value="/helloWorld", method = RequestMethod.GET)
	public void helloWorld() {
		
		
		
		LOGGER.debug("TEH HELLO WORLDZ !!!1");
	}
	
}
