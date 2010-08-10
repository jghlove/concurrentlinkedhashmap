package org.cachebench.cachewrappers;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap.Builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cachebench.CacheWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Zell
 */
@SuppressWarnings("unchecked")
public class CLHMCacheWrapper implements CacheWrapper {
  private final Log logger = LogFactory.getLog("org.cachebench.cachewrappers.CLHMCacheWrapper");
  private ConcurrentLinkedHashMap<Object, Object> cache;
  private int capacity;
  private int level;

  public void init(Map parameters) throws Exception {
//    InputStream stream =
//        getClass().getClassLoader().getResourceAsStream((String) parameters.get("config"));
//    Properties props = new Properties();
//
//    props.load(stream);
//    stream.close();
//
//    level = Integer.parseInt(props.getProperty("clhm.concurrencyLevel"));
//    capacity = Integer.parseInt(props.getProperty("clhm.maximumCapacity"));
    level = 16;
    capacity = 5000;
  }

  public void setUp() throws Exception {
    cache = new Builder<Object, Object>()
        .maximumWeightedCapacity(capacity)
        .concurrencyLevel(level)
        .build();
  }

  public void tearDown() throws Exception {
  }

  public void put(List<String> path, Object key, Object value) throws Exception {
    cache.put(key, value);
  }

  public Object get(List<String> path, Object key) throws Exception {
    return cache.get(key);
  }

  public void empty() throws Exception {
    cache.clear();
  }

  public int getNumMembers() {
    return 0;
  }

  public String getInfo() {
    return "size/capacity: " + cache.size() + "/" + cache.capacity();
  }

  public Object getReplicatedData(List<String> path, String key) throws Exception {
    return get(path, key);
  }

  public Object startTransaction() {
    throw new UnsupportedOperationException("Does not support JTA!");
  }

  public void endTransaction(boolean successful) {
    throw new UnsupportedOperationException("Does not support JTA!");
  }
}
