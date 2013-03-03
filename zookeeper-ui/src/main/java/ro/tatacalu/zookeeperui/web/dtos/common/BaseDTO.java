/**
 * 
 */
package ro.tatacalu.zookeeperui.web.dtos.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Matei
 *
 */
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = -4269020783726404971L;

	/**
	 * Default constructor.
	 */
	public BaseDTO() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		EqualsBuilder.reflectionEquals(this, obj, Boolean.FALSE.booleanValue());
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, Boolean.FALSE.booleanValue());
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
