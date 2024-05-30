package equator;


import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>Description: 比较公共方法</p>
 * <p>Copyright: Copyright (c) 2024</p>
 *
 * @author zjt
 * &#064;date  2024/05/26/14:58
 */
public abstract class AbstractEquator implements Equator {
    private static final List<Class<?>> WRAPPER = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class, String.class);

    /**
     * 最终值比较
     *
     * @param fieldInfo 属性结果封装类
     * @return 值比较结果
     */
    boolean isFieldEquals(EquatorFieldInfo fieldInfo) {
        return Objects.deepEquals(fieldInfo.getFirstVal(), fieldInfo.getSecondVal());
    }

    /**
     * 基础类型比较封装
     *
     * @param first  旧对象
     * @param second 新对象
     * @return 封装比较类
     */
    List<EquatorFieldInfo> compareSimpleField(Object first, Object second) {
        boolean eq = Objects.equals(first, second);
        if (eq) {
            return Collections.emptyList();
        } else {
            Object obj = first == null ? second : first;
            Class<?> clazz = obj.getClass();
            return Collections.singletonList(new EquatorFieldInfo()
                    .setFieldName(clazz.getSimpleName())
                    .setFirstFieldType(clazz)
                    .setFirstVal(first)
                    .setSecondFieldType(clazz)
                    .setSecondVal(second)
            );
        }
    }

    /**
     * 判断对象是否为基础类型
     *
     * @param first  旧对象
     * @param second 新对象
     * @return 比较是否基础类型或String
     */
    boolean isSimpleField(Object first, Object second) {
        Object obj = first == null ? second : first;
        Class<?> clazz = obj.getClass();
        return clazz.isPrimitive() || WRAPPER.contains(clazz);
    }

    /**
     * 判断属性是否基础类型
     *
     * @param clazz 对象Class
     * @return 比较是否基础类型或String
     */
    boolean isSimpleField(Class<?> clazz) {
        return clazz.isPrimitive() || WRAPPER.contains(clazz);
    }

    /**
     * 判断对象是否为Collection类型
     *
     * @param obj 判断对象
     * @return 判断对象是否属于collection
     */
    boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    /**
     * 判断对象是否为Map类型
     *
     * @param obj 判断对象
     * @return 判断对象是否属于map
     */
    boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName 属性名称
     * @param o         对象
     * @return 属性值
     */
    Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

}
