/**
 * 
 */
package ro.tatacalu.zookeeperui.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;

/**
 * @author Matei
 *
 */
@Configuration
public class ZookeeperUIConfig {

	private static final Logger LOGGER  = LoggerFactory.getLogger(ZookeeperUIConfig.class);
	
	private static final String ZOOKEEPER_CONNECTION_URL = "127.0.0.1:2181";
	
	public ZookeeperUIConfig() {
		super();
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CuratorFramework curatorFramework() {
		LOGGER.debug("ZookeeperUIConfig.curatorFramework() called");
		
		// connect to a ZooKeeper cluster
		// CuratorFramework instances are fully thread-safe
		// You should share one CuratorFramework per ZooKeeper cluster in your application
		CuratorFramework framework = 
				CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECTION_URL, new ExponentialBackoffRetry(1000, 3));
		framework.start();
		
		return framework;
	}
	
}
