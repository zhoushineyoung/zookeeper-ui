/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netflix.curator.framework.CuratorFramework;

/**
 * @author Matei
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirstStateBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstStateBean.class);
	
	@Resource
	private CuratorFramework curatorFramework;
	
	private TreeNode rootTreeNode;
	
	/**
	 * 
	 */
	public FirstStateBean() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		LOGGER.debug("PostConstruct START 3");
		
		try {
			List<String> rootChildren = curatorFramework.getChildren().forPath("/");
			
			this.rootTreeNode = new DefaultTreeNode("/", null);
			
			for (String currentChild: rootChildren) {
				TreeNode currentNode = new DefaultTreeNode("ZNode", currentChild, this.rootTreeNode);
			}
			
			LOGGER.debug("ROOT Children: {}", rootChildren);
		} catch (Exception e) {
			LOGGER.error("Get ROOT Children error", e);
		}
		
		LOGGER.debug("PostConstruct END");
	}
	
	public TreeNode getRootTreeNode() {
		return rootTreeNode;
	}
}
