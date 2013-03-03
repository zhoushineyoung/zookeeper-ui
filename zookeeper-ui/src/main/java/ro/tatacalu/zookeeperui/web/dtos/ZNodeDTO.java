/**
 * 
 */
package ro.tatacalu.zookeeperui.web.dtos;

import ro.tatacalu.zookeeperui.web.dtos.common.BaseDTO;

/**
 * @author Matei
 *
 */
public class ZNodeDTO extends BaseDTO {
	
	private static final long serialVersionUID = -1696050724030681844L;
	
	private String name;
	private String path;
	
	/**
	 * 
	 */
	public ZNodeDTO() {
		super();
	}
	
	/**
	 * @param name
	 * @param path
	 */
	public ZNodeDTO(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
