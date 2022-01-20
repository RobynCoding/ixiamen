package com.ixiamen.activity.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公用处理类：<br>
 * 动态设置或获取Bean的属性；转换或实例化对象；转换List与Map；比较两个值或Bean对象；
 * @author luoyongbin
 * @version 1.0
 */
public class BeanUtil {
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);
	
    /**
     * 给HashMap里面的查询条件加上%%,以使用LIKE通配符进行SQL查询
     *
     * @param hs   查询条件的Map
     * @param name 需要增加%的属性,以逗号分割
     */
    public static void addHsLike(Map<String,Object> hs, String name) {
    	addHsLike(hs, name, false);
    }
    
    /**
     * 给HashMap里面的查询条件加上%%,以使用LIKE通配符进行SQL查询。
     *
     * @param hs   查询条件的Map
     * @param name 需要增加%的属性,以逗号分割
     * @param uppercase 是否将值转换成大写
     */
    public static void addHsLike(Map<String,Object> hs, String name, boolean uppercase) {
        String[] arrName = name.split(",");
        int len = arrName.length;
		for (String item : arrName) {
			if ((hs.get(item) == null) || (hs.get(item).toString().equals(""))) {
				continue;
			}
			Object value = hs.get(item);
			if (value instanceof String) {
				String s = ((String) value).trim();
				if (!"".equals(s)) {
					s = uppercase ? s.toUpperCase() : s;
					hs.put(item, "%" + s + "%");
				}
			}
		}
    }

    /**
     * 给Object实例里面的查询条件加上%%,以使用LIKE通配符进行SQL查询
     *
     * @param obj  查询条件的对象
     * @param name 需要增加%的属性,以逗号分割
     */
    public static void addObjLike(Object obj, String name) {
    	addObjLike(obj, name, false);
    }
    
    /**
     * 给Object实例里面的查询条件加上%%,以使用LIKE通配符进行SQL查询
     *
     * @param obj  查询条件的对象
     * @param name 需要增加%的属性,以逗号分割
     * @param uppercase 是否将值转换成大写
     */
    public static void addObjLike(Object obj, String name, boolean uppercase) {
        String[] arrName = name.split(",");
        int len = arrName.length;
		for (String s : arrName) {
			try {
				String value = BeanUtils.getSimpleProperty(obj, s);
				if ((value == null) || (value.equals(""))) {
					continue;
				}
				value = uppercase ? value.toUpperCase() : value;
				BeanUtils.setProperty(obj, s, "%" + value + "%");
			} catch (Exception e) {
				log.error("add Like to [" + name + "]", e);
			}
		}
    }

    /**
     * Map是否为空
     * @param hs 被检查的HashMap
     * @return true如果HashMap为null或者空，false如果HashMap不空
     */
    public static boolean isEmpty(Map<?,?> hs) {
        if ((hs == null) || (hs.size() == 0)) return true;
        Set<?> set = hs.entrySet();
		for (Object o : set) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				return false;
			}
		}
        return true;
    }

    /**
     * 对Object的属性中的字符串值进行trim处理
     * @param obj 被处理的对象
     * @return 值已被处理过的Object
     */
    public static Object trim(Object obj) throws BeanUtilException {
    	try {
	        if (obj != null) {
	        	Map<?,?> hs = PropertyUtils.describe(obj);
	            Set<?> set = hs.entrySet();
				for (Object o : set) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
					if ((entry.getValue() != null) && (entry.getValue() instanceof String))
						((Map.Entry<?, Object>) entry).setValue(entry.getValue().toString().trim());
				}
	            PropertyUtils.copyProperties(obj, hs);
	        }
    	} catch (Exception e) {
			throw new BeanUtilException(e);
		}
        return obj;
    }
    
    /**
     * 把对象转换为Map形式的数据
     * @param obj 被转换对象
     * @return Map 结果结果为map
     */
	public static Map<String,Object> describe(Object obj) throws BeanUtilException {
		Map<String,Object> map = new HashMap<>();
		Map<?,?> original;
		try {
			if (obj instanceof Map<?,?>) {
				original = (Map<?,?>)obj;
			} else {
				original = PropertyUtils.describe(obj);
				original.remove("class");
			}
		} catch (Exception e) {
			throw new BeanUtilException(e);
		}

		for (Object o : original.keySet()) {
			String key = (String) o;
			map.put(key, original.get(key));
		}
		return map;
    }
    
    /**
     * 将类中的属性描述以Map形式返回
     * @param clazz 类
     * @return 属性描述
     */
    public static Map<String,PropertyDescriptor> describeClass(Class<?> clazz) {
    	Map<String,PropertyDescriptor> map = new HashMap<>();
    	PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor propertyDescriptor : pd) {
			map.put(propertyDescriptor.getName(), propertyDescriptor);
		}
    	return map;
    }

    /**
     * 将List里面所有的对象都trim处理
     * @param list 被处理列表
     * @return 值已被trim处理过的Object的List
     */
    public static List<Object> trimList(List<Object> list) throws BeanUtilException {
        if (list == null) return null;
        for (int i=0; i<list.size(); i++) {
			list.set(i, trim(list.get(i)));
		}
		return list;
    }

    /**
     * 把Object的属性的字符串的编码由ISO8859-1转换为GBK编码
     * @param obj 被转码对象
     * @param fromEncode 转码前编码
     * @param toEncode 转码后编码
     * @return 转码后的对象
	 */
    public static Object decode(Object obj, String fromEncode, String toEncode)
    		throws BeanUtilException {
    	try {
	    	if (obj != null) {
		    	Map<?,?> hs = describe(obj);
		        Set<?> set = hs.entrySet();
				for (Object o : set) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
					if ((entry.getValue() != null) && (entry.getValue() instanceof String)) {
						String temp = entry.getValue().toString().trim();
						temp = new String(temp.getBytes(fromEncode), toEncode);
						((Map.Entry<?, Object>) entry).setValue(temp);
					}
				}
		        PropertyUtils.copyProperties(obj, hs);
	    	}
    	} catch (Exception e) {
			throw new BeanUtilException(e);
		}
        return obj;
    }
    
    /**
     * 将对象从ISO8859-1编码转换为GBK编码
     * @see #decode(Object, String, String)
     */
	public static Object decode(Object obj) throws BeanUtilException {
    	return decode(obj, "ISO8859-1", "GBK");
    }
    
	/**
	 * 克隆一个对象
	 * @param source 源对象
	 * @return 克隆所得的对象
	 * @throws BeanUtilException 克隆失败
	 */
	public static Object cloneBean(Object source) throws BeanUtilException {
		try {
    		return BeanUtils.cloneBean(source);
    	} catch (Exception e) {
			throw new BeanUtilException(e);
		}
	}
	
    /**
     * 将source的属性复制到target里面
     * @param target 目标对象
     * @param source 来源对象
     */
    public static void copy(Object target, Object source) throws BeanUtilException {
    	try {
    		PropertyUtils.copyProperties(target, source);
    	} catch (Exception e) {
			throw new BeanUtilException(e);
		}
    }
    
    /**
     * 将source的属性复制到target里面，若属性名在exclusion中则不复制该属性
     * @param target 目标对象
     * @param source 来源对象
     * @param exclusion 例外的属性（不复制）
     */
    public static void copy(Object target, Object source, String[] exclusion) throws BeanUtilException {
    	Set<String> exclude = null;
    	if (exclusion != null && exclusion.length > 0) {
    		exclude = new HashSet<>();
			Collections.addAll(exclude, exclusion);
    	}
    	
		Map<?,?> tarMap = describe(target);
		Map<String,Object> temp = new HashMap<>();
		
		Map<?,?> srcMap = describe(source);
		for (Object o : tarMap.keySet()) {
			String key = (String) o;
			if (exclude != null && exclude.contains(key)) {
				continue;
			}
			temp.put(key, srcMap.get(key));
		}
        copy(target, temp);
    }
    
    /**
     * 依据KeyMapping中指定的映射，将Map中的数据复制到目标Bean中
     * @param target 目标Bean
     * @param source 源map
     * @param keyMapping 键映射，如:["firstName=FIRST-NAME", "lastName=LAST-NAME"]
     * @return target-目标Bean
     */
    public static Object copyKey(Object target, Map<?,?> source, String[] keyMapping) throws BeanUtilException {
    	try {
			for (String s : keyMapping) {
				String[] key2key = s.split("=");
				String key = key2key[0].trim();
				Object value = source.get(key2key[1].trim());
				Class<?> clazz = PropertyUtils.getPropertyType(target, key);
				PropertyUtils.setSimpleProperty(target, key, convert(clazz, value));
			}
    	} catch (Exception e) {
			throw new BeanUtilException(e);
		}
		return target;
	}
    
    /**
     * 将SQL查询的结果行赋值成pojo对象
     * @param pojo 对象
     * @param row 数据行，KEY为ColumnLabel的大写
     * @return pojo 对象，已赋值
     */
    public static Object copyColumn(Object pojo, Map<?,?> row) throws BeanUtilException {
    	Map<String,Object> prop = new HashMap<>();
		for (Object o : row.keySet()) {
			String column = (String) o;
			String key = FormatUtil.toProperty(column);
			try {
				Class<?> clazz = PropertyUtils.getPropertyType(pojo, key);
				if (clazz != null) {
					prop.put(key, convert(clazz, row.get(column)));
				}
			} catch (Exception e) {
				log.error("[column=" + column + "][key=" + key + "][class="
						+ pojo.getClass().getName() + "]", e);
			}
		}
    	copy(pojo, prop);
    	return pojo;
    }
    
    /**
     * 将两个Bean对象进行比较，若属性名在exclusion中则不比较该属性
     * @param obj1 比较对象1
     * @param obj2 比较对象2
     * @param exclusion 例外的属性（不比较）
     * @return 0-一致；非0-不一致
     */
    public static int compares(Object obj1, Object obj2, String[] exclusion) throws BeanUtilException {
    	Set<String> exclude = null;
    	if (exclusion != null && exclusion.length > 0) {
    		exclude = new HashSet<>();
			Collections.addAll(exclude, exclusion);
    	}
    	
    	Map<?,?> map1 = describe(obj1);
    	Map<?,?> map2 = describe(obj2);
		for (Object o : map1.keySet()) {
			String key = (String) o;
			if (exclude != null && exclude.contains(key)) {
				continue;
			}
			Object prop1 = map1.get(key);
			Object prop2 = map2.get(key);
			int compare = compare(prop1, prop2);
			if (compare != 0) {
				return compare;
			}
		}
    	return 0;
    }
    
    /**
     * 将两个Bean对象进行比较，若属性名在exclusion中则不比较该属性
     * @param obj1 比较对象1
     * @param obj2 比较对象2
     * @see #compares(Object, Object, String[])
     */
    public static int compares(Object obj1, Object obj2) throws BeanUtilException {
    	return compares(obj1, obj2, null);
    }
    
    /**
     * 将两个String或者Number或者日期或者布儿值进行比较
     * @param value 被比较值
     * @param compareValue 比较值
     * @return 0-相同,非0-不同: 大于0为前者大，小于0为后者大
     */
    public static int compare(Object value, Object compareValue) {
    	int result = 0;
    	if ((value == null || "".equals(value)) && (compareValue == null || "".equals(compareValue))) {
    		return 0;
    	}
    	
    	if (value != null && compareValue == null) {
    		return 1;
    	} else if (value == null) {
    		return -1;
    	}
    	if (value instanceof String) {
			result = ((String)value).compareTo((String)compareValue);
		} else if (value instanceof Integer) {
			result = ((Integer)value).compareTo((Integer)compareValue);
		} else if (value instanceof Long) {
			result = ((Long)value).compareTo((Long)compareValue);
		} else if (value instanceof Float) {
			result = ((Float)value).compareTo((Float)compareValue);
		} else if (value instanceof Double) {
			result = ((Double)value).compareTo((Double)compareValue);
		} else if (value instanceof Short) {
			result = ((Short)value).compareTo((Short)compareValue);
		} else if (value instanceof BigInteger) {
			result = ((BigInteger)value).compareTo((BigInteger)compareValue);
		} else if (value instanceof BigDecimal) {
			result = ((BigDecimal)value).compareTo((BigDecimal)compareValue);
		} else if (value instanceof java.util.Date) {
			result = ((java.util.Date)value).compareTo((java.util.Date)compareValue);
		} else if (value instanceof Boolean) {
			int intBln = (Boolean) value ? 1 : 0;
			int intCompareBln = (Boolean) compareValue ? 1 : 0;
			result = intBln - intCompareBln;
		}
    	return result;
    }
    
    /**
     * 将两个Bean对象进行比较，获取不同值的属性，若属性名在exclusion中则不比较该属性
     * @param obj1 比较对象1
     * @param obj2 比较对象2
     * @param exclusion 例外的属性（不比较）
     * @return 比较结果
     */
    public static Map<String,Object> differProperties(Object obj1, Object obj2, String[] exclusion)
    		 throws BeanUtilException {
    	Set<String> exclude = null;
    	if (exclusion != null && exclusion.length > 0) {
    		exclude = new HashSet<>();
			Collections.addAll(exclude, exclusion);
    	}
    	
    	Map<String,Object> map = new HashMap<>();
    	Map<?,?> map1 = describe(obj1);
    	Map<?,?> map2 = describe(obj2);
		for (Object o : map1.keySet()) {
			String key = (String) o;
			if (exclude != null && exclude.contains(key)) {
				continue;
			}
			Object prop1 = map1.get(key);
			Object prop2 = map2.get(key);
			int compare = compare(prop1, prop2);
			if (compare != 0) {
				List<Object> diff = new ArrayList<>();
				diff.add(prop1);
				diff.add(prop2);
				map.put(key, diff);
			}
		}
        return map;
    }
    
    /**
     * 将两个Bean对象进行比较，获取不同值的属性
     * @param obj1 比较对象1
     * @param obj2 比较对象2
     * @return 比较结果
     * @see #differProperties(Object, Object, String[])
     */
    public static Map<String,Object> differProperties(Object obj1, Object obj2)
    		 throws BeanUtilException {
    	return differProperties(obj1, obj2, null);
    }

    /**
     * 将List里的对象放入Map中，Map的key使用对象的属性，这些属性通过keys指定。<br>
     * 若存在多个属性作为key，则各个属性间使用symbol连接起来，若symbol==null，则symbol取"-"。
     * @param list 对象列表，list中的对象最好为简单的对象
     * @param keys 对象的属性，将作为map的key。如：new String[]{"name","id.code"}
     * @param symbol 连接符，默认为"-"
     * @return map 转换后的结果
     */
    public static Map<String,Object> rotateListToMap(List<?> list, String[] keys, String symbol)
    		 throws BeanUtilException {
    	Map<String,Object> map = new HashMap<>();
    	if (symbol == null) {
    		symbol = "-";
    	}
    	if (keys == null || keys.length == 0) {
    		throw new IllegalArgumentException("keys should be appointed.");
    	}
    	try {
			for (Object obj : list) {
				StringBuilder key = new StringBuilder();
				for (int j = 0; j < keys.length; j++) {
					Object prop = PropertyUtils.getProperty(obj, keys[j]);
					key.append(prop).append(j > 0 ? symbol : "");
				}
				map.put(key.toString(), obj);
			}
    	} catch (Exception e) {
    		throw new BeanUtilException(e);
		}
    	return map;
    }
    
    /**
     * 将List转换成Map
     * @see #rotateListToMap(List, String[], String)
     */
    public static Map<String,Object> rotateListToMap(List<?> list, String[] keys) throws BeanUtilException {
    	return rotateListToMap(list, keys, null);
    }
    
    /**
     * 将List转换成Map。用List中的对象的keyProperty属性作为map的键值，而对象本身作为map的值。
     * @param list List 被转换的列表，其中包含要被转换的bean
     * @param keyProperty 属性，被转换类要提供getter方法
     * @return Map 转换后的结果
     * @see #rotateListToMap(List, String[], String)
     */
    public static Map<String,Object> convertList2Map(List<?> list, String keyProperty)
    		throws BeanUtilException {
    	Map<String,Object> map = new HashMap<>();
		for (Object bean : list) {
			if (bean == null) {
				continue;
			}
			Object key;
			if (bean instanceof Map<?, ?>) {
				key = ((Map<?, ?>) bean).get(keyProperty);
			} else {
				key = BeanUtil.get(bean, keyProperty);
			}
			String keyStr = key != null ? key.toString() : null;
			map.put(keyStr, bean);
		}
    	return map;
    }
    
    /**
     * 对一些基类与数字、日期类型进行实例转换。
     * @param clazz 类
     * @param value 值
     * @return 转换后的值
     */
    public static Object convert(Class<?> clazz, String value) {
    	if (clazz.equals(String.class)) {
    		return value;
    	}
    	if (value == null || "".equals(value.trim())) {
    		return null;
    	}
    	if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
    		return Integer.valueOf(value);
    	}
        if (clazz.equals(short.class) || clazz.equals(Short.class)) {
        	return Short.valueOf(value);
        }
        if (clazz.equals(long.class) || clazz.equals(Long.class)) {
        	return Long.valueOf(value);
        }
        if (clazz.equals(float.class) || clazz.equals(Float.class)) {
        	return Float.valueOf(value);
        }
        if (clazz.equals(double.class) || clazz.equals(Double.class)) {
        	return Double.valueOf(value);
        }
        if (clazz.equals(BigDecimal.class)) {
        	return new BigDecimal(value);
        }
        if (clazz.equals(BigInteger.class)) {
        	return new BigInteger(value);
        }
        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
        	return Boolean.valueOf(value);
        }
        if (clazz.equals(java.util.Date.class)) {
			String[] patterns = new String[] {"yyyy-MM-dd", "yyyyMMdd",
					"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "HH:mm:ss"};
			for (String pattern : patterns) {
				try {
					return new SimpleDateFormat(pattern).parse(value);
				} catch (ParseException e) {
					//do nothing
				}
			}
		}
        throw new IllegalStateException("cannot instance:"+clazz+":"+value);
    }
    
    static Map<String,Class<?>> MAP_CLASS = new HashMap<>();
    static {
    	MAP_CLASS.put("String", String.class);
    	MAP_CLASS.put("Integer", Integer.class);
    	MAP_CLASS.put("Short", Short.class);
    	MAP_CLASS.put("Long", Long.class);
    	MAP_CLASS.put("Float", Float.class);
    	MAP_CLASS.put("Double", Double.class);
    	MAP_CLASS.put("BigDecimal", BigDecimal.class);
    	MAP_CLASS.put("BigInteger", BigInteger.class);
    	MAP_CLASS.put("Boolean", Boolean.class);
    	MAP_CLASS.put("Date", java.util.Date.class);
    }
    
    /**
     * 对一些基类与数字、日期类型进行实例转换。
     * @param clazz 类，字符缩写
     * @param value 值
     * @return 转换后的值
     */
    public static Object convert(String clazz, String value) {
    	return convert(MAP_CLASS.get(clazz), value);
    }
    
    /**
     * 对一些基类与数字、日期类型进行实例转换。
     * @param clazz 类
     * @param value 值
     * @return 转换后的值
     */
    public static Object convert(Class<?> clazz, Object value) {
    	if (value == null) {
    		return null;
    	}
    	if (value instanceof String) {
    		return convert(clazz, (String)value);
    	}
    	if (value instanceof Character) {
    		return convert(clazz, ((Character)value).toString());
    	}
		if (value instanceof Number) {
			if (clazz.equals(String.class)) {
				return value.toString();
			}
			if (clazz.equals(long.class) || clazz.equals(Long.class)) {
				return ((Number) value).longValue();
			}
			if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
				return ((Number) value).intValue();
			}
			if (clazz.equals(double.class) || clazz.equals(Double.class)) {
				return ((Number) value).doubleValue();
			}
			if (clazz.equals(float.class) || clazz.equals(Float.class)) {
	        	return ((Number) value).floatValue();
	        }
			if (clazz.equals(short.class) || clazz.equals(Short.class)) {
	        	return ((Number) value).shortValue();
	        }
		}
		throw new IllegalStateException("cannot instance:"+clazz+":"+value);
    }
    
    /**
     * 动态调用POJO的getter
     * 
     * @param property property 属性名
     * @return Object 属性值
     */
    public static Object get(Object instance, String property) throws BeanUtilException {
		String getter = ReflectHelper.buildGetter(property);
		try {
			return ReflectHelper.invokeMethodOnClass(instance, getter, null);
		} catch (Exception e) {
			throw new BeanUtilException("获取BEAN属性值时出错:property=" + property, e);
		}
	}
    
    /**
     * 动态调用POJO的setter
     * 
     * @param property String 方法名
     * @param value Object 值。注意：调用者自己保证值的类型，否则抛错误。
     */
    public static void set(Object instance, String property, Object value) throws BeanUtilException {
    	String setter = ReflectHelper.buildSetter(property);
    	try {
			Method ms = ReflectHelper.getMethodByName(instance, setter);
			Class<?> clazz = ms.getParameterTypes()[0];
			Object[] values = { convert(clazz, value) };
			ReflectHelper.invokeMethodOnClass(instance, setter, values);
		} catch (Exception e) {
			throw new BeanUtilException("给BEAN属性赋值时出错:property=" + 
					property + ",setter=" + setter + ",value=" + value + 
					",value.class=" + (value==null?null:value.getClass()), e);
		}
	}

}
