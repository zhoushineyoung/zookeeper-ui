/**
 * 
 */
package ro.tatacalu.zookeeperui.web.dtos;

import java.util.Date;

import org.apache.zookeeper.data.Stat;

import ro.tatacalu.zookeeperui.web.dtos.common.BaseDTO;

/**
 * @author Matei
 *
 */
public class ZNodeStatDTO extends BaseDTO {

	private static final long serialVersionUID = -3726315199461573767L;
	
	private Long czxid;
	private Long mzxid;
	private Date ctime;
	private Date mtime;
	private Integer version;
	private Integer cversion;
	private Integer aversion;
	private Long ephemeralOwner;
	private Integer dataLength;
	private Integer numChildren;
	private Long pzxid;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private ZNodeStatDTO() {}
	
	/**
	 * 
	 * @param stat
	 */
	public ZNodeStatDTO(Stat stat) {
		super();
		this.czxid = stat.getCzxid();
		this.mzxid = stat.getMzxid();
		this.ctime = new Date(stat.getCtime());
		this.mtime = new Date(stat.getMtime());
		this.version = stat.getVersion();
		this.cversion = stat.getCversion();
		this.aversion = stat.getAversion();
		this.ephemeralOwner = stat.getEphemeralOwner();
		this.dataLength = stat.getDataLength();
		this.numChildren = stat.getNumChildren();
		this.pzxid = stat.getPzxid();
	}

	/**
	 * @return the czxid
	 */
	public Long getCzxid() {
		return czxid;
	}

	/**
	 * @return the mzxid
	 */
	public Long getMzxid() {
		return mzxid;
	}

	/**
	 * @return the ctime
	 */
	public Date getCtime() {
		return ctime;
	}

	/**
	 * @return the mtime
	 */
	public Date getMtime() {
		return mtime;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @return the cversion
	 */
	public Integer getCversion() {
		return cversion;
	}

	/**
	 * @return the aversion
	 */
	public Integer getAversion() {
		return aversion;
	}

	/**
	 * @return the ephemeralOwner
	 */
	public Long getEphemeralOwner() {
		return ephemeralOwner;
	}

	/**
	 * @return the dataLength
	 */
	public Integer getDataLength() {
		return dataLength;
	}

	/**
	 * @return the numChildren
	 */
	public Integer getNumChildren() {
		return numChildren;
	}

	/**
	 * @return the pzxid
	 */
	public Long getPzxid() {
		return pzxid;
	}
}
