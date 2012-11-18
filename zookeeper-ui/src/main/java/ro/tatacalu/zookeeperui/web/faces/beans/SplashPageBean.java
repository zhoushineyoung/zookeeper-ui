/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Tata Calu
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SplashPageBean implements Serializable {

	private static final long serialVersionUID = -9205351459549012011L;
	
	/**
	 * Default constructor.
	 */
	public SplashPageBean() {
		super();
	}
}
