//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package equator;


import java.util.*;

public abstract class AbstractEquator implements Equator {
    private static final List<Class<?>> WRAPPER = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class, String.class);

    public AbstractEquator() {
    }

    protected boolean isFieldEquals(FieldInfo fieldInfo) {
        return this.nullableEquals(fieldInfo.getFirstVal(), fieldInfo.getSecondVal());
    }

    List<FieldInfo> compareSimpleField(Object first, Object second) {
        boolean eq = Objects.equals(first, second);
        if (eq) {
            return Collections.emptyList();
        } else {
            Object obj = first == null ? second : first;
            Class<?> clazz = obj.getClass();
            return Collections.singletonList(new FieldInfo(clazz.getSimpleName(), clazz, first, second));
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

    boolean isSimpleField(Class<?> clazz) {
        boolean primitive = clazz.isPrimitive();
        boolean contains = WRAPPER.contains(clazz);
        return primitive || contains;
    }

    private boolean nullableEquals(Object first, Object second) {
        return first instanceof Collection && second instanceof Collection ? Objects.deepEquals(((Collection) first).toArray(), ((Collection) second).toArray()) : Objects.deepEquals(first, second);
    }
}
