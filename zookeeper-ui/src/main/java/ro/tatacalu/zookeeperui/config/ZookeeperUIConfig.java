/**
 * 
 */
package ro.tatacalu.zookeeperui.config;

import java.io.IOException;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ro.tatacalu.zookeeperui.zookeeper.ZookeeperUIWatcher;

/**
 * @author Matei
 * 
 */
@Configuration
public class ZookeeperUIConfig {

    private static final Logger LOGGER                    = LoggerFactory.getLogger(ZookeeperUIConfig.class);

    private static final String ZOOKEEPER_CONNECTION_URL  = "127.0.0.1:2181";
    private static final int    ZOOKEEPER_SESSION_TIMEOUT = 30_000;

    public ZookeeperUIConfig() {
        super();
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ZooKeeper createZooKeeperInstance() {

        ZooKeeper ret = null;
                
        try {
            LOGGER.debug("createZooKeeperInstance() called");
            
            Watcher watcher = new ZookeeperUIWatcher();
            ret = new ZooKeeper(ZOOKEEPER_CONNECTION_URL, ZOOKEEPER_SESSION_TIMEOUT, watcher);
        } catch (IOException e) {
            LOGGER.error("Could not create a ZooKeeper instance", e);
        }
        
        return ret;
    }
}
