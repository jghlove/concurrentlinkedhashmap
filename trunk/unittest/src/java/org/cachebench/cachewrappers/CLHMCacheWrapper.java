package org.cachebench.cachewrappers;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rc.util.concurrent.ConcurrentLinkedHashMap;
import com.rc.util.concurrent.ConcurrentLinkedHashMap.EvictionPolicy;

import org.cachebench.CacheWrapper;

@SuppressWarnings("unchecked")
public class CLHMCacheWrapper implements CacheWrapper
{
   private final Log logger = LogFactory.getLog(
      "org.cachebench.cachewrappers.CLHMCacheWrapper");

   private int level;
   private EvictionPolicy policy;
   private int capacity;

   private ConcurrentLinkedHashMap<Object, Object> cache;

   /**
    * {@inheritDoc}
    */
   public void init(Map parameters) throws Exception
   {
      InputStream stream = getClass().getClassLoader().getResourceAsStream(
         (String) parameters.get("config"));
      Properties props = new Properties();

      props.load(stream);
      stream.close();

      level = Integer.parseInt(props.getProperty("clhm.concurrencyLevel"));
      policy = EvictionPolicy.valueOf(
         EvictionPolicy.class, props.getProperty("clhm.evictionPolicy"));
      capacity = Integer.parseInt(props.getProperty("clhm.maximumCapacity"));
   }

   /**
    * {@inheritDoc}
    */
   public void setUp() throws Exception
   {
      cache = new ConcurrentLinkedHashMap<Object, Object>(
         policy, capacity, level);
   }

   /**
    * {@inheritDoc}
    */
   public void tearDown() throws Exception
   {
   }

   /**
    * {@inheritDoc}
    */
   public void put(List<String> path, Object key, Object value) throws Exception
   {
      cache.put(key, value);
   }

   /**
    * {@inheritDoc}
    */
   public Object get(List<String> path, Object key) throws Exception
   {
      return cache.get(key);
   }

   /**
    * {@inheritDoc}
    */
   public void empty() throws Exception
   {
      cache.clear();
   }

   /**
    * {@inheritDoc}
    */
   public int getNumMembers()
   {
      return 0;
   }

   /**
    * {@inheritDoc}
    */
   public String getInfo()
   {
      return "size/capacity: " + cache.size() + "/" + cache.capacity();
   }

   /**
    * {@inheritDoc}
    */
   public Object getReplicatedData(List<String> path, String key) throws Exception
   {
      return get(path, key);
   }
}