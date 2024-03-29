package equator;


import java.util.List;

/**
 * @author zjt
 * @date 2021/8/13 20:42
 */
public interface Equator {

    /**
     * 获取值不一致的属性
     * @param var1
     * @param var2
     * @return
     */
    List<FieldInfo> getDifferentFields(Object var1, Object var2);
}