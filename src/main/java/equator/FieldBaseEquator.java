//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package equator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 123先生
 */
@Slf4j
public class FieldBaseEquator extends AbstractEquator {

    /**
     * 返回被修改的属性值
     * return the modified attribute value
     *
     * @param first  old object
     * @param second new object
     * @return new diff fields
     */
    @Override
    public List<FieldInfo> getDifferentFields(Object first, Object second) {
        if (super.isSimpleField(first, second)) {
            //基础类型比较
            return super.compareSimpleField(first, second);
        } else if (this.isCollection(first, second)) {
            //Collection类型比较 TODO
            return Collections.emptyList();
        } else if (this.isMap(first, second)) {
            //Map类型比较  TODO
            return Collections.emptyList();
        } else {
            //对象比较
            //获取对象属性集合
            List<SimpleFieldInfo> firstInfoList = this.getSimpleFieldInfo(first);
            List<SimpleFieldInfo> secondInfoList = this.getSimpleFieldInfo(second);
            List<FieldInfo> val = new LinkedList<>();
            Iterator<SimpleFieldInfo> firstIterator = firstInfoList.iterator();
            while (firstIterator.hasNext()) {
                SimpleFieldInfo firstNext = firstIterator.next();
                Iterator<SimpleFieldInfo> secondIterator = secondInfoList.iterator();
                while (secondIterator.hasNext()) {
                    SimpleFieldInfo secondNext = secondIterator.next();
                    //字段属性名称、字段中文名称相同的
                    if (firstNext.getFieldName().equals(secondNext.getFieldName()) &&
                            firstNext.getFieldNote().equals(secondNext.getFieldNote())) {
                        FieldInfo f1 = new FieldInfo(
                                firstNext.getFieldName(),
                                firstNext.getFieldType(),
                                firstNext.getFieldVal(),
                                secondNext.getFieldType(),
                                secondNext.getFieldVal(),
                                firstNext.getFieldNote(),
                                firstNext.getFieldDescribe());
                        val.add(f1);
                        //secondInfoList匹配上了，移除，以secondInfoList为主数据
                        secondIterator.remove();
                    }
                }
            }
            //secondInfoList有剩余没匹配，说明是新增的
            if (!secondInfoList.isEmpty()) {
                secondInfoList.forEach(sec -> {
                    FieldInfo f1 = new FieldInfo();
                    f1.setFieldName(sec.getFieldName());
                    f1.setFirstFieldType(sec.getFieldType());
                    f1.setFirstVal(null);
                    f1.setSecondFieldType(sec.getFieldType());
                    f1.setSecondVal(sec.getFieldVal());
                    f1.setFieldNote(sec.getFieldNote());
                    f1.setFieldDescribe(sec.getFieldDescribe());
                    val.add(f1);
                });
            }

            List<FieldInfo> diffFields = new LinkedList<>();
            val.forEach(info -> {
                if (!this.isFieldEquals(info)) {
                    diffFields.add(info);
                }
            });
            return diffFields;
        }
    }


    private List<SimpleFieldInfo> getSimpleFieldInfo(Object obj) {
        List<SimpleFieldInfo> infoList = new LinkedList<>();
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                //判断字段上是否有注解@EqualsAnnotation
                EqualsAnnotation equalsAnnotation = field.getAnnotation(EqualsAnnotation.class);
                //判断属性类型，基础类型
                if (isSimpleField(type)) {
                    //判断是否合成字段,由编译器生成的，在源代码中没有出现的，都会被标记为 synthetic
                    if (!field.isSynthetic()) {
                        //判断字段是否需要比较
                        if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                            //属性中文别名
                            String value = equalsAnnotation.value();
                            if ("".equals(value) || value == null) {
                                value = field.getName();
                            }
                            //属性描述
                            String describe = equalsAnnotation.describe();
                            //属性名称
                            String fieldName = field.getName();
                            //属性值
                            Object firstVal = null;
                            Class<?> firstType;
                            field.setAccessible(true);
                            try {
                                firstVal = field.get(obj);
                            } catch (IllegalAccessException e) {
                                log.error("获取属性值失败", e);
                            }
                            firstType = field.getType();
                            SimpleFieldInfo fieldInfo = new SimpleFieldInfo(fieldName, value, describe, firstType, firstVal);
                            infoList.add(fieldInfo);
                        }
                    }
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    //TODO
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        Collection list = (Collection) getFieldValueByName(field.getName(), obj);
                        Optional.ofNullable(list).ifPresent(info -> {
                            for (Object next : list) {
                                List<SimpleFieldInfo> simpleFieldInfo = this.getSimpleFieldInfo(next);
                                Optional.of(simpleFieldInfo).ifPresent(infoList::addAll);
                            }
                        });
                    }
                } else if (Map.class.equals(type)) {
                    //TODO
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        Map map = (Map) getFieldValueByName(field.getName(), obj);
                    }
                } else {
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        PropertyDescriptor descriptor = null;
                        try {
                            descriptor = new PropertyDescriptor(field.getName(), cls);
                            Method method = descriptor.getReadMethod();
                            Object obj1 = null;
                            obj1 = method.invoke(obj);
                            Optional.ofNullable(obj1).ifPresent(obj2 -> infoList.addAll(this.getSimpleFieldInfo(obj2)));
                        } catch (IntrospectionException e) {
                            log.error("IntrospectionException", e);
                        } catch (IllegalAccessException e) {
                            log.error("IllegalAccessException", e);
                        } catch (InvocationTargetException e) {
                            log.error("InvocationTargetException", e);
                        }
                    }
                }
            }
        }
        return infoList;
    }

    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            return method.invoke(o, new Object[]{});
        } catch (Exception e) {
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    static class SimpleFieldInfo {
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
}
