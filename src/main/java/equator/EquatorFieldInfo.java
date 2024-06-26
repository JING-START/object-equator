package equator;

import lombok.Getter;


/**
 * <p>Description: 解下比较对象返回的结果</p>
 * <p>Copyright: Copyright (c) 2024</p>
 *
 * @author zjt
 * &#064;date  2024/05/26/14:58
 */
@Getter
public class EquatorFieldInfo {
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性中文名
     */
    private String fieldNote;
    /**
     * 属性描述
     */
    private String fieldDescribe;
    /**
     * 第一属性类型
     */
    private Class<?> firstFieldType;
    /**
     * 第二属性类型
     */
    private Class<?> secondFieldType;
    /**
     * 第一属性值，变更前数据
     */
    private Object firstVal;
    /**
     * 第二属性值，变更后数据
     */
    private Object secondVal;

    public EquatorFieldInfo() {

    }

    public EquatorFieldInfo setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public EquatorFieldInfo setFieldNote(String fieldNote) {
        this.fieldNote = fieldNote;
        return this;
    }

    public EquatorFieldInfo setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
        return this;
    }

    public EquatorFieldInfo setFirstFieldType(Class<?> firstFieldType) {
        this.firstFieldType = firstFieldType;
        return this;
    }

    public EquatorFieldInfo setSecondFieldType(Class<?> secondFieldType) {
        this.secondFieldType = secondFieldType;
        return this;
    }

    public EquatorFieldInfo setFirstVal(Object firstVal) {
        this.firstVal = firstVal;
        return this;
    }

    public EquatorFieldInfo setSecondVal(Object secondVal) {
        this.secondVal = secondVal;
        return this;
    }

    @Override
    public String toString() {
        String val = "{" + fieldNote + ":" + this.secondVal + "}";
        if (fieldDescribe != null && !fieldDescribe.isEmpty()) {
            val += ",{属性描述:" + fieldDescribe + "}";
        }
        return val;
    }
}
