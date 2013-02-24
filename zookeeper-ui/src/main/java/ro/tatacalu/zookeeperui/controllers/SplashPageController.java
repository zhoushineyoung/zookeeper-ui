/**
 * 
 */
package ro.tatacalu.zookeeperui.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Matei
 *
 */
@Controller
@RequestMapping(value = "/")
public class SplashPageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SplashPageController.class);
	
	/**
	 * 
	 */
	public SplashPageController() {
		super();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public void doGet() {
		
	}
	
}
