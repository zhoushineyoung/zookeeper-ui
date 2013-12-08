/**
 * 
 */
package ro.tatacalu.zookeeperui.web.faces.beans;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
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

import ro.tatacalu.zookeeperui.web.dtos.CharsetDTO;
import ro.tatacalu.zookeeperui.web.dtos.ZNodeDTO;
import ro.tatacalu.zookeeperui.web.dtos.ZNodeStatDTO;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.api.transaction.CuratorTransactionResult;

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
	
	private String currentZNodeData;
	private ZNodeStatDTO currentZNodeStat;
	private String newZNodeName;
	private String newZNodeDataString;
	
	private Map<String, TreeNode> pathToNodeMap;
	
	private List<CharsetDTO> availableCharsets;
	
	private String defaultCharsetString;
	
	private String selectedCharsetString;
	
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
			
			this.availableCharsets = new ArrayList<>();
			
			SortedMap<String, Charset> availableCharsetsMap = Charset.availableCharsets(); 
			for (Map.Entry<String, Charset> entry : availableCharsetsMap.entrySet()) {
				CharsetDTO newCharsetDTO = new CharsetDTO(entry.getValue().name(), entry.getValue().displayName());
				this.availableCharsets.add(newCharsetDTO);
			}
			
			this.setDefaultCharsetString(Charset.defaultCharset().name());
			this.setSelectedCharsetString(this.getDefaultCharsetString());
			
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
	
	/**
	 * Trigger executed when a TreeNode is expanded.
	 * 
	 * @param nodeExpandEvent
	 * @throws Exception
	 */
	public void onNodeExpand(NodeExpandEvent nodeExpandEvent) throws Exception {
		
		TreeNode originatingTreeNode = nodeExpandEvent.getTreeNode();
		if (!(originatingTreeNode.isExpanded())) {
			originatingTreeNode.setExpanded(Boolean.TRUE);
		}
		
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
	
	/**
	 * Trigger executed when a TreeNode is collapse.
	 * 
	 * @param nodeCollapseEvent
	 * @throws Exception
	 */
	public void onNodeCollapse(NodeCollapseEvent nodeCollapseEvent) throws Exception {
		LOGGER.debug("onNodeCollapse: ZNodeDTO={}", (ZNodeDTO)(nodeCollapseEvent.getTreeNode().getData()));
		
		TreeNode originatingTreeNode = nodeCollapseEvent.getTreeNode();
		if (originatingTreeNode.isExpanded()) {
			nodeCollapseEvent.getTreeNode().setExpanded(Boolean.FALSE);
		}
	}
	
	/**
	 * Trigger executed when a TreeNode is selected.
	 * 
	 * @param nodeSelectEvent
	 * @throws Exception
	 */
	public void onNodeSelect(NodeSelectEvent nodeSelectEvent) throws Exception {
		
		ZNodeDTO selectedZNodeDTO = (ZNodeDTO)(nodeSelectEvent.getTreeNode().getData());
		
		LOGGER.debug("onNodeSelect: ZNodeDTO={}", selectedZNodeDTO);
		this.currentZNodeData = this.getZNodeDataString(selectedZNodeDTO);
		
		// read the Stat
		Stat stat = this.curatorFramework.checkExists().forPath(selectedZNodeDTO.getPath());
		this.currentZNodeStat = new ZNodeStatDTO(stat);
		LOGGER.debug("Stat: {}", currentZNodeStat);
	}
	
	/**
	 * Trigger executed when a TreeNode is unselected.
	 * 
	 * @param nodeUnselectEvent
	 * @throws Exception
	 */
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
	
	public void loadSelectedZNodeData() {
		LOGGER.debug("loadSelectedZNodeData()");
		this.currentZNodeData = this.getZNodeDataString(this.getSelectedZNodeDTO());  
	}
	
	public TreeNode getTreeNodeChildren(TreeNode parent, String parentPath) throws Exception {
		
		LOGGER.debug("getTreeNodeChildren START: parent={}, parentPath={}", parent, parentPath);
		
		List<ZNodeDTO> children = this.getChildren(parentPath);
		
		// remove all the children
		parent .getChildren().clear();
		
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
	
	private ZNodeStatDTO updateZNodeData(ZNodeDTO zNodeDTO, String data) {
		LOGGER.debug("updateZNodeData: zNodeDTO={}, data={}", zNodeDTO, data);
		
		ZNodeStatDTO ret = null;
		
		try {
			Collection<CuratorTransactionResult> results = this.curatorFramework
				.inTransaction()
				.setData().forPath(zNodeDTO.getPath(), data.getBytes())
				.and()
				.commit();
			
			if (results.size() == NumberUtils.INTEGER_ONE) {
				CuratorTransactionResult curatorTransactionResult = results.iterator().next();
				
				ret = new ZNodeStatDTO(curatorTransactionResult.getResultStat());
				
				LOGGER.debug("Transaction result: forPath={}, resultPath={}, resultStat={}, resultType={}", 
						curatorTransactionResult.getForPath(),
						curatorTransactionResult.getResultPath(),
						ret,
						curatorTransactionResult.getType());
			} else {
				LOGGER.error("Multiple CuratorTransactionResult objects returned by the setData operation!");
			}
		} catch (Exception e) {
			LOGGER.error("Error trying to update the znode", e);
		}
		
		return ret;
	}
	
	public void saveSelectedNodeData() {
		ZNodeDTO zNodeDTO = this.getSelectedZNodeDTO();
		
		LOGGER.debug("saveSelectedNodeData: selectedDTO=", zNodeDTO);
		
		this.currentZNodeStat = this.updateZNodeData(zNodeDTO, this.currentZNodeData);
	}
	
	private String getZNodeDataString(ZNodeDTO zNodeDTO) {
		
		LOGGER.debug("getZNodeDataString: zNodeDTO={}", zNodeDTO);
		
		String ret = null;
		
		try {
			byte[] data = this.curatorFramework.getData().forPath(zNodeDTO.getPath());
			ret = new String(data, this.getSelectedCharset());
			
//			List<ACL> aclList = this.curatorFramework.getACL().forPath(zNodeDTO.getPath());
//			for (ACL acl : aclList) {
//				LOGGER.debug("ACL: {}", acl);
//			}
//			
//			LOGGER.debug("DATA: byte array: {}; String: {}", data, ret);
		} catch (Exception e) {
			LOGGER.error ("Error while retrieving data: {}", zNodeDTO, e);
		}
		
		return ret;
	}
	
	
	
	public void createNewNode() {
		
		ZNodeDTO selectedZNodeDTO = this.getSelectedZNodeDTO();
		
		LOGGER.debug("createNewNode: Parent={}, New ZNode name={}, Data={}", selectedZNodeDTO, this.newZNodeName, 
				this.newZNodeDataString);
		
		try {
			
			// TODO Refactoring
			String parentNodePath = this.getSelectedZNodeDTO().getPath();
			StringBuilder builder = new StringBuilder(parentNodePath);
			
			if (!(parentNodePath.equals("/"))) {
				builder.append("/");
			}
			
			builder.append(this.newZNodeName);
			
			String newZNodePath = builder.toString();
			Charset charset = this.getSelectedCharset();
			
			LOGGER.debug("New ZNode path: {}; Charset: {}", newZNodePath, charset);
			
			Collection<CuratorTransactionResult> results = this.curatorFramework.inTransaction().create()
				.forPath(newZNodePath, this.newZNodeDataString.getBytes(charset))
				.and()
				.commit();
			
			for (CuratorTransactionResult curatorTransactionResult : results) {
				LOGGER.debug("Transaction result: forPath={}, resultPath={}, resultStat={}, resultType={}", 
						curatorTransactionResult.getForPath(),
						curatorTransactionResult.getResultPath(),
						curatorTransactionResult.getResultStat(),
						curatorTransactionResult.getType());
			}
			
			// clean up
			this.newZNodeName = null;
			this.newZNodeDataString = null;
		} catch (Exception e) {
			LOGGER.error("An error has occured while trying to create a new node", e);
		}
		
	}
	
	private ZNodeDTO getSelectedZNodeDTO() {
		
		ZNodeDTO ret = null;
		if (this.selectedTreeNode != null) {
			Object data = this.selectedTreeNode.getData();
			if (data != null) {
				ret = (ZNodeDTO) data;
			}
		}
		
		return ret;
	}
	
	private Charset getSelectedCharset() {
		Charset ret = null;
		
		if (this.selectedCharsetString != null) {
			ret = Charset.forName(this.selectedCharsetString); 
		}
		
		return ret;
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

	/**
	 * @return the currentZNodeData
	 */
	public String getCurrentZNodeData() {
		return currentZNodeData;
	}

	/**
     * @param currentZNodeData the currentZNodeData to set
     */
    public void setCurrentZNodeData(String currentZNodeData) {
        this.currentZNodeData = currentZNodeData;
    }

    /**
	 * @return the currentZNodeStatDTO
	 */
	public ZNodeStatDTO getCurrentZNodeStat() {
		return currentZNodeStat;
	}

	/**
	 * @return the newZNodeName
	 */
	public String getNewZNodeName() {
		return newZNodeName;
	}

	/**
	 * @param newZNodeName the newZNodeName to set
	 */
	public void setNewZNodeName(String newZNodeName) {
		this.newZNodeName = newZNodeName;
	}

	/**
	 * @return the newZNodeDataString
	 */
	public String getNewZNodeDataString() {
		return newZNodeDataString;
	}

	/**
	 * @param newZNodeDataString the newZNodeDataString to set
	 */
	public void setNewZNodeDataString(String newZNodeDataString) {
		this.newZNodeDataString = newZNodeDataString;
	}

	/**
	 * @return the availableCharsets
	 */
	public List<CharsetDTO> getAvailableCharsets() {
		return availableCharsets;
	}

	/**
	 * @param availableCharsets the availableCharsets to set
	 */
	public void setAvailableCharsets(List<CharsetDTO> availableCharsets) {
		this.availableCharsets = availableCharsets;
	}

	/**
	 * @return the defaultCharsetString
	 */
	public String getDefaultCharsetString() {
		return defaultCharsetString;
	}

	/**
	 * @param defaultCharsetString the defaultCharsetString to set
	 */
	public void setDefaultCharsetString(String defaultCharsetString) {
		this.defaultCharsetString = defaultCharsetString;
	}

	/**
	 * @return the selectedCharsetString
	 */
	public String getSelectedCharsetString() {
		return selectedCharsetString;
	}

	/**
	 * @param selectedCharsetString the selectedCharsetString to set
	 */
	public void setSelectedCharsetString(String selectedCharsetString) {
		this.selectedCharsetString = selectedCharsetString;
	}
}
