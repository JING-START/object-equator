//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package equator;


import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractEquator implements Equator {
    private static final List<Class<?>> WRAPPER = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class, String.class);

    public AbstractEquator() {
    }

    /**
     * 最终值比较
     *
     * @param fieldInfo
     * @return
     */
    boolean isFieldEquals(EquatorFieldInfo fieldInfo) {
        return Objects.deepEquals(fieldInfo.getFirstVal(), fieldInfo.getSecondVal());
    }

    /**
     * 基础类型比较封装
     *
     * @param first
     * @param second
     * @return
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
     * @param first
     * @param second
     * @return
     */
    boolean isSimpleField(Object first, Object second) {
        Object obj = first == null ? second : first;
        Class<?> clazz = obj.getClass();
        return clazz.isPrimitive() || WRAPPER.contains(clazz);
    }

    /**
     * 判断属性是否基础类型
     *
     * @param clazz
     * @return
     */
    boolean isSimpleField(Class<?> clazz) {
        boolean primitive = clazz.isPrimitive();
        boolean contains = WRAPPER.contains(clazz);
        return primitive || contains;
    }

    /**
     * 判断对象是否为Collection类型
     *
     * @param obj
     * @return
     */
    boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    /**
     * 判断对象是否为Map类型
     *
     * @param obj
     * @return
     */
    boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    /**
     * 根据属性名获取属性值
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
