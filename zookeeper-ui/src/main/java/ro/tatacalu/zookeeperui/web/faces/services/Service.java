/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.services;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Tata Calu
 *
 */
@ManagedBean(name="demoService")
@ApplicationScoped
public class Service {

	public Service() {
		super();
	}
	
	public String reverse(String name) {
		return new StringBuilder(name).reverse().toString().toLowerCase();
	}
	
}
