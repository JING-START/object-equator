package equator;

import java.util.Objects;

/**
 * @author zjt
 * @date 2021/8/13 20:43
 */
public class FieldInfo {
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 属性中文名
     */
    private String fieldNote;
    /**
     * 属性代表描述
     */
    private String fieldDescribe;
    private Class<?> firstFieldType;
    private Class<?> secondFieldType;
    private Object firstVal;
    private Object secondVal;

    public FieldInfo() {
    }

    public FieldInfo(String fieldName, Class<?> firstFieldType, Class<?> secondFieldType) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.secondFieldType = secondFieldType;
    }

    public FieldInfo(String fieldName, Class<?> firstFieldType, Object firstVal,Class<?> secondFieldType,Object secondVal,String fieldNote,String fieldDescribe) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.firstVal = firstVal;
        this.secondFieldType = secondFieldType;
        this.secondVal = secondVal;
        this.fieldNote = fieldNote;
        this.fieldDescribe = fieldDescribe;
    }


    public FieldInfo(String fieldName, Class<?> firstFieldType, Object firstVal) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.firstVal = firstVal;
    }

    public FieldInfo(String fieldName, Class<?> fieldType, Object firstVal, Object secondVal) {
        this.fieldName = fieldName;
        this.firstFieldType = fieldType;
        this.secondFieldType = fieldType;
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    public FieldInfo(String fieldName, Class<?> firstFieldType, Class<?> secondFieldType, Object firstVal, Object secondVal) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.secondFieldType = secondFieldType;
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFirstFieldType() {
        return this.firstFieldType;
    }

    public void setFirstFieldType(Class<?> firstFieldType) {
        this.firstFieldType = firstFieldType;
    }

    public Object getFirstVal() {
        return this.firstVal;
    }

    public void setFirstVal(Object firstVal) {
        this.firstVal = firstVal;
    }

    public void setSecondFieldType(Class<?> secondFieldType) {
        this.secondFieldType = secondFieldType;
    }

    public Class<?> getSecondFieldType() {
        return this.secondFieldType;
    }

    public Object getSecondVal() {
        return this.secondVal;
    }

    public void setSecondVal(Object secondVal) {
        this.secondVal = secondVal;
    }

    public String getFieldNote() {
        return fieldNote;
    }

    public void setFieldNote(String fieldNote) {
        this.fieldNote = fieldNote;
    }

    public String getFieldDescribe() {
        return fieldDescribe;
    }

    public void setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            FieldInfo fieldInfo = (FieldInfo) o;
            return Objects.equals(this.fieldName, fieldInfo.fieldName) &&
                    Objects.equals(this.fieldNote, fieldInfo.fieldNote) &&
                    Objects.equals(this.fieldDescribe, fieldInfo.fieldDescribe) &&
                    Objects.equals(this.firstFieldType, fieldInfo.firstFieldType) &&
                    Objects.equals(this.secondFieldType, fieldInfo.secondFieldType) &&
                    Objects.equals(this.firstVal, fieldInfo.firstVal) &&
                    Objects.equals(this.secondVal, fieldInfo.secondVal);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fieldName,
                this.fieldNote,
                this.fieldDescribe,
                this.firstFieldType,
                this.secondFieldType, this.firstVal, this.secondVal);
    }

    @Override
    public String toString() {
        String val = "{" + fieldNote + ":" + this.secondVal + "}";
        if (fieldDescribe != null && !"".equals(fieldDescribe)) {
            val += ",{属性描述:" + fieldDescribe + "}";
        }
        return val;
    }
}
