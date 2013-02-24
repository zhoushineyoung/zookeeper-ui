/**
 * 
 */
package ro.tatacalu.zookeeperui.web.dtos;

import java.io.Serializable;

/**
 * @author Matei
 *
 */
public class ZooKeeperInstanceDTO implements Serializable {

	private static final long serialVersionUID = 4477855667687213314L;
	
	private Long id;
	private String name;
	private String description;
	private String host;
	private Integer port;
	private String rootMountPoint;
	// TODO retrypolicy ??
	private Integer connectionTimeoutMs;
	private Integer sessionTimeoutMs;
	
	public ZooKeeperInstanceDTO() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the rootMountPoint
	 */
	public String getRootMountPoint() {
		return rootMountPoint;
	}

	/**
	 * @param rootMountPoint the rootMountPoint to set
	 */
	public void setRootMountPoint(String rootMountPoint) {
		this.rootMountPoint = rootMountPoint;
	}

	/**
	 * @return the connectionTimeoutMs
	 */
	public Integer getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	/**
	 * @param connectionTimeoutMs the connectionTimeoutMs to set
	 */
	public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	/**
	 * @return the sessionTimeoutMs
	 */
	public Integer getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	/**
	 * @param sessionTimeoutMs the sessionTimeoutMs to set
	 */
	public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}
}
