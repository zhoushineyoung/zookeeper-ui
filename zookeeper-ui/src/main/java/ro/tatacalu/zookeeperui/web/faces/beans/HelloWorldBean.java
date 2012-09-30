/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ro.tatacalu.zookeeperui.web.faces.services.Service;

/**
 * @author Tata Calu
 *
 */
@ManagedBean
@SessionScoped
public class HelloWorldBean implements Serializable {

	private static final long serialVersionUID = -6405473751530670276L;
	
	private String name = "";
	
	@ManagedProperty(value = "#{demoService}")
	private Service service;
	
	/**
	 * 
	 */
	public HelloWorldBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public void setService(Service service) {
	    this.service = service;
	  }

	  public String getReverseName() {
	    return service.reverse(name);
	  }

}
