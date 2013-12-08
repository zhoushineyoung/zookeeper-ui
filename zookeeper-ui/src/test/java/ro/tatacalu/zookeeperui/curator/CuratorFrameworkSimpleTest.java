/**
 * 
 */
package ro.tatacalu.zookeeperui.curator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.imps.CuratorFrameworkState;
import com.netflix.curator.retry.ExponentialBackoffRetry;

/**
 * @author Matei
 *
 */
public class CuratorFrameworkSimpleTest {
	
	private Logger LOGGER = LoggerFactory.getLogger(CuratorFrameworkSimpleTest.class);
	
	private static final String ZOOKEEPER_CONNECTION_URL = "127.0.0.1:2181";
	
	private CuratorFramework curatorFramework;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.curatorFramework = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECTION_URL, new ExponentialBackoffRetry(1000, 3));
		this.curatorFramework.start();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.curatorFramework.close();
	}

	@Test
	@Ignore
	public final void connectTest() {
		LOGGER.debug("START connectTest");
		
		CuratorFrameworkState state =  this.curatorFramework.getState();
		String namespace = this.curatorFramework.getNamespace();
		
		LOGGER.debug("Curator Framework Client State: {}, Namespace: \"{}\"", state, namespace);
		
		LOGGER.debug("END connectTest");
	}
	
	@Test
	public final void getChildrenTest() throws Exception {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		LOGGER.info("START getChildrenTest()");
		
		int iterations = 1;
		
		for (int i = 0; i <= iterations; ++i) {
			
			LOGGER.debug("ZNode List {}/{}", i, iterations);
			
			List<String> znodes = this.getZNodeList();
			for (String znode : znodes) {
				LOGGER.debug(znode);
			}
			
			LOGGER.debug("--------------------------");
		}
		stopWatch.stop();
		
		LOGGER.info("END getChildrenTest() - Elapsed time: {} ms",  stopWatch.getNanoTime() / 1000000);
	}
	
	// TODO dummy data generator to insert shit inside ZK
	
	public List<String> getChildren(String path) throws Exception {
		return  this.curatorFramework.getChildren().forPath(path);
	}
	
	public void getChildren(Map<String, List<String>> childrenMap, String path) throws Exception {
		
		List<String> children = this.getChildren(path);
		
		// put the children inside the map
		childrenMap.put(path, children);
		
		// process the children
		for (String child : children) {
			
			String currentZNodePath = path;
			if (!(path.equals("/"))) {
				currentZNodePath += "/";
			}
			currentZNodePath += child;
			
			this.getChildren(childrenMap, currentZNodePath); 
		}
		
		// remove the children list from the map
		// we will only use keyset of the map for the children
		childrenMap.put(path, null);
	}
	
	public List<String> getZNodeList() throws Exception {
		Map<String, List<String>> childrenMap = new HashMap<>();
		
		
		this.getChildren(childrenMap, "/");
		
		for(String currentZNodePath : childrenMap.keySet()) {
			LOGGER.debug(currentZNodePath);
		}
		
		List<String> childrenList = new ArrayList<>(childrenMap.keySet());
		Collections.sort(childrenList);
		
		return childrenList;
	}
	
}
