/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import ro.tatacalu.zookeeperui.web.dtos.ZooKeeperInstanceDTO;

/**
 * @author Tata Calu
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SplashPageBean implements Serializable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SplashPageBean.class);	
	
	private static final long serialVersionUID = -9205351459549012011L;
	
	private static final String CONNECT_STRING_LOCALHOST = "127.0.0.1:2181";
	
	private List<ZooKeeperInstanceDTO> instances;
	
	
	/**
	 * Default constructor.
	 */
	public SplashPageBean() {
		super();
		System.out.println("---------------------------------------------------------------------------------------------------------");
		this.instances = new ArrayList<ZooKeeperInstanceDTO>();
		
		ZooKeeperInstanceDTO localZkInstance = new ZooKeeperInstanceDTO();
		
		localZkInstance.setId(1L);
		localZkInstance.setName("Local ZooKeeper");
		localZkInstance.setDescription("DOLAN");
		localZkInstance.setHost("127.0.0.1");
		localZkInstance.setPort(2181);
		
		this.instances.add(localZkInstance);
	}
//	@PostConstruct
//	public void init() {
//		Builder builder = CuratorFrameworkFactory.builder();
//		CuratorFramework curatorFramework = builder.connectString(CONNECT_STRING_LOCALHOST).build();
//		CuratorZookeeperClient curatorZookeeperClient = curatorFramework.getZookeeperClient();
//			LOGGER.debug("Children: {}", this.children);
//		} catch (Exception e1) {
//			LOGGER.error("Error trying to get the children list", e1);
//		}
//	}
//	public List<String> getChildren() {
//		return children;
//	}
//	}

	
	
}
