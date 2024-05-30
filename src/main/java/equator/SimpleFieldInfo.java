package equator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>Description: 解下比较对象返回的结果</p>
 * <p>Copyright: Copyright (c) 2024</p>
 *
 * @author zjt
 * &#064;date  2024/05/26/14:58
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
