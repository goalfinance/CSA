package cistern.dao.hibernate.simplequery;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: Cistern</p>
 * <p>Description: Bean简单访问控件类。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intensoft Corp</p>
 * @author seabao
 * @version 1.01
 */
public class SimpleBeanAccess {
	@SuppressWarnings("unchecked")
    private static Class primitiveClasses[] = { String.class, Integer.class, Integer.TYPE, Long.class, Long.TYPE,
            Double.class, Double.TYPE, Float.class, Float.TYPE, BigDecimal.class, Byte.class, Byte.TYPE, Short.class,
            Short.TYPE, Character.class, Character.TYPE, Boolean.class, Boolean.TYPE, java.util.Date.class };

    private SimpleBeanAccess() {
    }

    @SuppressWarnings("unchecked")
    private static Object getAttribute(Class c, Object obj, String attrName) throws NoSuchFieldException,
            IllegalAccessException {
        Field f = c.getField(attrName);
        return f.get(obj);
    }

    /**
     * 获得给定对象的属性值
     * @param obj 对象
     * @param attrName 属性名称
     * @return 属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getAttribute(Object obj, String attrName) throws NoSuchFieldException, IllegalAccessException {
        return getAttribute(obj.getClass(), obj, attrName);
    }

    /**
     * 获得给定类的静态属性值
     * @param c 类对象
     * @param attrName 静态属性名称
     * @return 属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static Object getAttribute(Class c, String attrName) throws NoSuchFieldException, IllegalAccessException {
        return getAttribute(c, null, attrName);
    }

    /**
     * 获得指定属性的描述对象。
     */
    @SuppressWarnings("unchecked")
    public static PropertyDescriptor getPropertyDescriptor(Class c, String name) throws Exception {
        BeanInfo info = java.beans.Introspector.getBeanInfo(c);
        PropertyDescriptor pd[] = info.getPropertyDescriptors();

        for (int i = 0; i < pd.length; i++) {
            if (pd[i].getName().equals(name)) {
                return pd[i];
            }
        }

        throw new Exception("The class[" + c.getName() + "] hasn't such property: " + name + ".");
    }

    /**
     * 设置Bean指定属性值。
     * @param obj - Bean对象。
     * @param name - 属性名称。
     * @param value - 属性String类型表达值。
     * @param valueOfTextMethod - 属性String类型初始化方法。
     * @throws Exception
     */
    public static void setProperty(Object obj, String name, Object value) throws Exception {
        PropertyDescriptor pd = getPropertyDescriptor(obj.getClass(), name);
        setProperty(obj, pd, value);
    }

    public static void setProperty(Object obj, PropertyDescriptor pd, Object value) throws Exception {
        Method m = pd.getWriteMethod();

        if (m == null) {
            throw new Exception("The property " + pd.getName() + " is read only.");
        }

        if (pd.getPropertyType().isPrimitive() == true && value == null) {
            return;
        }

        try {
            m.invoke(obj, new Object[] { value });
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable e1 = e.getTargetException();
            if (e1 instanceof Exception) {
                throw (Exception) e1;
            } else {
                throw (Error) e1;
            }
        }
    }

    /**
     * 获得Bean指定属性值。
     * @param obj - Bean对象。
     * @param name - 属性名称。
     * @return Object
     */
    public static Object getProperty(Object obj, String name) throws Exception {
        PropertyDescriptor pd = getPropertyDescriptor(obj.getClass(), name);
        return getProperty(obj, pd);
    }

    public static Object getProperty(Object obj, PropertyDescriptor pd) throws Exception {
        Method m = pd.getReadMethod();

        if (m == null) {
            throw new Exception("The property " + pd.getName() + " is write only.");
        }

        try {
            return m.invoke(obj);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable e1 = e.getTargetException();
            if (e1 instanceof Exception) {
                throw (Exception) e1;
            } else {
                throw (Error) e1;
            }
        }
    }

    public static Object cloneBean(Object bean) {
        return cloneBean(bean, null);
    }

    private static boolean isPrimitiveObject(Object obj) {
        for (int i = 0; i < primitiveClasses.length; i++) {
            if (primitiveClasses[i].isInstance(obj)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private static Object cloneObject(Class targetType, Object obj, Set<String> ignoreProperties, IdentityHashMap objMap) throws Exception {
        if (obj == null) {
            return null;
        }

        if (isPrimitiveObject(obj)) {
            return obj;
        }

        Object alreadyClonedObj = objMap.get(obj);
        if (alreadyClonedObj != null) {
            return alreadyClonedObj;
        }

        Object cloneObj;

        if (obj instanceof Collection) {
            if (targetType != null) {
                if (targetType.equals(java.util.SortedSet.class)) {
                    cloneObj = new TreeSet();
                } else if (targetType.equals(java.util.Set.class)) {
                    cloneObj = new HashSet();
                } else if (targetType.equals(java.util.List.class)) {
                    cloneObj = new ArrayList();
                } else if (targetType.equals(java.util.Vector.class)) {
                    cloneObj = new Vector();
                } else if (targetType.equals(java.util.LinkedList.class)) {
                    cloneObj = new LinkedList();
                } else {
                    cloneObj = obj.getClass().newInstance();
                }
            } else {
                cloneObj = obj.getClass().newInstance();
            }

            objMap.put(obj, cloneObj);
            for (Iterator it = ((Collection) obj).iterator(); it.hasNext();) {
                Object v = it.next();
                Object cloneItem = cloneObject(null, v, ignoreProperties, objMap);
                ((Collection) cloneObj).add(cloneItem);
            }

            return cloneObj;
        }

        if (obj instanceof Map) {
            if (targetType != null) {
                if (targetType.equals(java.util.SortedMap.class)) {
                    cloneObj = new TreeMap();
                } else if (targetType.equals(java.util.Map.class)) {
                    cloneObj = new HashMap();
                } else if (targetType.equals(java.util.Hashtable.class)) {
                    cloneObj = new Hashtable();
                } else {
                    cloneObj = obj.getClass().newInstance();
                }
            } else {
                cloneObj = obj.getClass().newInstance();
            }

            objMap.put(obj, cloneObj);
            for (Iterator it = ((Map) obj).keySet().iterator(); it.hasNext();) {
                Object key = it.next();
                Object value = ((Map) obj).get(key);
                Object cloneKey = cloneObject(null, key, ignoreProperties, objMap);
                Object cloneValue = cloneObject(null, value, ignoreProperties, objMap);
                ((Map) cloneObj).put(cloneKey, cloneValue);
            }

            return cloneObj;
        }

        if (obj instanceof Cloneable) {
            try {
                Method cloneMethod = obj.getClass().getDeclaredMethod("clone");
                cloneObj = cloneMethod.invoke(obj);
                objMap.put(obj, cloneObj);

                return cloneObj;

            } catch (Exception e) {
            }
        }

        cloneObj = obj.getClass().newInstance();
        objMap.put(obj, cloneObj);

        BeanInfo info = java.beans.Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            PropertyDescriptor pd = pds[i];

            if (pd.getWriteMethod() == null) {
                continue;
            }

            if (ignoreProperties != null) {
                boolean ignoreFlag = false;
                for (Iterator it = ignoreProperties.iterator(); it.hasNext();) {
                    Object ignoreObj = it.next();
                    if (ignoreObj instanceof Pattern) {
                        Matcher m = ((Pattern) ignoreObj).matcher(pd.getName());
                        if (m.matches()) {
                            ignoreFlag = true;
                            break;
                        }
                    } else if (ignoreObj.equals(pd.getName())) {
                        ignoreFlag = true;
                        break;
                    }
                }

                if (ignoreFlag) {
                    continue;
                }
            }

            Object attr = getProperty(obj, pd);
            setProperty(cloneObj, pd, cloneObject(pd.getPropertyType(), attr, ignoreProperties, objMap));
        }

        return cloneObj;
    }

    @SuppressWarnings("unchecked")
    public static Object cloneBean(Object bean, Set<String> ignoreProperties) {
        try {
            return cloneObject(null, bean, ignoreProperties, new IdentityHashMap());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}