package equator;


import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @date 2021/8/13 20:42
 */
public interface Equator {

    /**
     * 获取值不一致的属性
     *
     * @param var1
     * @param var2
     * @return
     */
    List<?> getDifferentFields(Object var1, Object var2);

    /**
     * 主入口方法，会递归调用
     * @see Equator#parseObject(Object)
     * @see Equator#parseCollectionFieldInfo(Collection)
     * @see Equator#parseMapFieldInfo(Map)
     *
     * @param obj
     * @return
     *
     */
    List<?> doHandler(Object obj);

    /**
     * 解析单对象属性
     *
     * @param obj
     * @return
     */
    List<?> parseObject(Object obj);

    /**
     * 解析集合属性
     *
     * @param list
     * @return
     */
    List<?> parseCollectionFieldInfo(Collection<?> list);

    /**
     * 解析Map属性
     *
     * @param map
     * @return
     */
    List<?> parseMapFieldInfo(Map map);

}