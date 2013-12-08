/**
 * 
 */
package ro.tatacalu.zookeeperui.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tatacalu
 *
 */
public class ZookeeperUIWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperUIWatcher.class);
    
    /* (non-Javadoc)
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    @Override
    public void process(WatchedEvent event) {
        LOGGER.debug("Received event: {}", event);
    }

}
