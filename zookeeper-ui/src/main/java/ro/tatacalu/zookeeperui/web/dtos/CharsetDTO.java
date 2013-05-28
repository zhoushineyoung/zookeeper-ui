/**
 * 
 */
package ro.tatacalu.zookeeperui.web.dtos;

import ro.tatacalu.zookeeperui.web.dtos.common.BaseDTO;

/**
 * @author Matei
 *
 */
public class CharsetDTO extends BaseDTO {
	
	private static final long serialVersionUID = -8396952485727414394L;
	
	private String name;
	private String displayName;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private CharsetDTO() {}
	
	/**
	 * @param name
	 * @param charset
	 */
	public CharsetDTO(String name, String displayName) {
		super();
		this.name = name;
		this.setDisplayName(displayName);
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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
