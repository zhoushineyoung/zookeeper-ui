/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import ro.tatacalu.zookeeperui.web.dtos.ZNodeDTO;

import com.netflix.curator.framework.CuratorFramework;

/**
 * @author Matei
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class FirstStateBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstStateBean.class);
	
	@Resource
	private CuratorFramework curatorFramework;
	
	private TreeNode rootTreeNode;
	
	private TreeNode selectedTreeNode;
	
	private Map<String, TreeNode> pathToNodeMap;
	
	/**
	 * 
	 */
	public FirstStateBean() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		LOGGER.debug("PostConstruct START");
		
		try {
			this.pathToNodeMap = new HashMap<String, TreeNode>();
			
			this.rootTreeNode = this.readZookeeperRootNode();
			this.rootTreeNode = this.getTreeNodeChildren(this.rootTreeNode, "/");
			
			LOGGER.debug("this.rootTreeNode={}", this.rootTreeNode);
			
			List<TreeNode> rootChildren = this.rootTreeNode.getChildren(); 
			for (TreeNode child: rootChildren) {
				this.getTreeNodeChildren(child, ((ZNodeDTO) child.getData()).getPath());
			}
			
			LOGGER.debug("ROOT Children: {}", rootChildren);
		} catch (Exception e) {
			LOGGER.error("Get ROOT Children error", e);
		}
		
		LOGGER.debug("PostConstruct END");
	}
	
	public void onNodeExpand(NodeExpandEvent nodeExpandEvent) throws Exception {
		
		TreeNode originatingTreeNode = nodeExpandEvent.getTreeNode();
		
		ZNodeDTO originatingZNodeDTO = (ZNodeDTO) originatingTreeNode.getData();
		int childCount = originatingTreeNode.getChildCount();
		LOGGER.debug("onNodeExpand: ZNodeDTO={}, child count: {}", originatingZNodeDTO, childCount);
		
		if (childCount > 0) {
			
			List<TreeNode> children = originatingTreeNode.getChildren();
			for (TreeNode currentChild : children) {
				
				ZNodeDTO currentChildZNodeDTO = (ZNodeDTO) currentChild.getData();
				
				LOGGER.debug("onNodeExpand: originatingZNodeDTO={}, childDTO={}", originatingZNodeDTO, currentChildZNodeDTO);
				
				this.getTreeNodeChildren(currentChild, currentChildZNodeDTO.getPath());
			}
		}
	}
	
	public void onNodeCollapse(NodeCollapseEvent nodeCollapseEvent) throws Exception {
		LOGGER.debug("onNodeCollapse: ZNodeDTO={}", (ZNodeDTO)(nodeCollapseEvent.getTreeNode().getData()));
	}
	
	public void onNodeSelect(NodeSelectEvent nodeSelectEvent) throws Exception {
		LOGGER.debug("onNodeSelect: ZNodeDTO={}", (ZNodeDTO)(nodeSelectEvent.getTreeNode().getData()));
	}
	
	public void onNodeUnselect(NodeUnselectEvent nodeUnselectEvent) throws Exception {
		LOGGER.debug("onNodeUnselect: ZNodeDTO={}", (ZNodeDTO)(nodeUnselectEvent.getTreeNode().getData()));
	}
	
	public List<ZNodeDTO> getChildren(String path) throws Exception {
		
		List<ZNodeDTO> ret = new ArrayList<ZNodeDTO>();
		
		List<String> rootChildren = this.curatorFramework.getChildren().forPath(path);
		for (String currentChild: rootChildren) {
			StringBuilder sb = new StringBuilder(path);
			if (!(path.equals("/"))) {
				sb.append("/");
			}
			sb.append(currentChild);
			ZNodeDTO zNodeDTO = new ZNodeDTO(currentChild, sb.toString());
			ret.add(zNodeDTO);
		}
		
		return ret;
	}
	
	public TreeNode getTreeNodeChildren(TreeNode parent, String parentPath) throws Exception {
		
		LOGGER.debug("getTreeNodeChildren START: parent={}, parentPath={}", parent, parentPath);
		
		List<ZNodeDTO> children = this.getChildren(parentPath);
		
		// remove all the children
		if (parent.getChildCount() > 0) {
			List <TreeNode> treeNodeChildren = parent.getChildren();
			for (TreeNode treeNodeChild : treeNodeChildren) {
				treeNodeChildren.remove(treeNodeChild);
			}
		}
		
		LOGGER.debug("getTreeNodeChildren children.size()={}", children.size());
		
		if (children.size() > 0) {
			
			for (ZNodeDTO currentChildDTO : children) {
				new DefaultTreeNode("ZNode", currentChildDTO, parent);
			}
		}
		
		LOGGER.debug("getTreeNodeChildren END: parent={}, parentPath={}", parent, parentPath);
		
		return parent;
	}
	
	public TreeNode readZookeeperRootNode() {
		
		return new DefaultTreeNode("ZNode", new ZNodeDTO("/","/"), null);
	}
	
	public TreeNode getRootTreeNode() {
		return rootTreeNode;
	}

	/**
	 * @return the selectedTreeNode
	 */
	public TreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}

	/**
	 * @param selectedTreeNode the selectedTreeNode to set
	 */
	public void setSelectedTreeNode(TreeNode selectedTreeNode) {
		this.selectedTreeNode = selectedTreeNode;
	}
}
