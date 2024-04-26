package equator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zjt
 * @date 2021/8/13 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleFieldInfo {
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性中文
     */
    private String fieldNote;
    /**
     * 属性描述
     */
    private String fieldDescribe;
    /**
     * 属性类型
     */
    private Class<?> fieldType;
    /**
     * 属性值
     */
    private Object fieldVal;
}
