package com.ixiamen.activity.util;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpRequestUtil 工具
 * @author luoyongbin
 * @version 1.0 
 */
public class HttpRequestUtil {

  private HttpRequestUtil() {}

  /**
   * 将request中的参数绑定到指定的pojo的bean实例中。<br>
   * 只支持一些基本的类型，对于复杂的类型如Date可能无法绑定。
   * @param request HTTP请求
   * @param pojo 对象
   * @throws InvocationTargetException 无法读取对象
   * @throws IllegalAccessException 无法读取对象
   * @see #bind(HttpServletRequest, Object)
   */
  public static void bind(HttpServletRequest request, Object pojo)
  			throws IllegalAccessException, InvocationTargetException {
	  Map<String,Object> map = bindMap(request);
	  Map<?,?> props = BeanUtil.describeClass(pojo.getClass());
	  for (Object value : props.keySet()) {
		  String pName = (String) value;
		  PropertyDescriptor desc = (PropertyDescriptor) props.get(pName);
		  if (map.get(pName) == null) continue;
		  if (!desc.getPropertyType().isInstance(map.get(pName))) {
			  String v = (String) map.get(pName);
			  Object o = BeanUtil.convert(desc.getPropertyType(), v);
			  map.put(pName, o);
		  }
	  }
	  BeanUtils.populate(pojo, map);
  }
  
  /**
   * 将request中的参数获取到一个Map中并返回。<br>
   * @param request HTTP请求
   * @see #bindMap(HttpServletRequest)
   */
  public static Map<String,Object> bindMap(HttpServletRequest request) {
	  HashMap<String,Object> map = new HashMap<>();
	  Enumeration<?> names = request.getParameterNames();
	  while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameter(name));
	  }
	  return map;
  }

}
