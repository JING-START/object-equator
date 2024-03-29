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
            List<SimpleFieldInfo> firstInfoList = getSimpleFieldInfo(first);
            List<SimpleFieldInfo> secondInfoList = getSimpleFieldInfo(second);

            List<FieldInfo> val = new LinkedList<>();
            int bigSize = Math.max(firstInfoList.size(), secondInfoList.size());

            boolean isLong = firstInfoList.size() <= secondInfoList.size();

            for (int i = 0; i < bigSize; i++) {
                SimpleFieldInfo simple1 = null;
                SimpleFieldInfo simple2 = null;
                boolean isEnd = true;
                //second长
                if (isLong) {
                    Iterator<SimpleFieldInfo> iterator = firstInfoList.iterator();
                    while (iterator.hasNext()) {
                        SimpleFieldInfo next = iterator.next();
                        if (isEnd) {
                            //字段相同的
                            if (secondInfoList.get(i).getFieldName().equals(next.getFieldName()) &&
                                    secondInfoList.get(i).getFieldNote().equals(next.getFieldNote())) {
                                simple1 = next;
                                simple2 = secondInfoList.get(i);
                                FieldInfo f1 = new FieldInfo(
                                        simple1.getFieldName(),
                                        simple1.getFieldType(),
                                        simple1.getFieldVal(),

                                        simple2.getFieldType(),
                                        simple2.getFieldVal(),
                                        simple1.getFieldNote(),
                                        simple1.getFieldDescribe());
                                val.add(f1);
                                isEnd = false;
                                //短的匹配上了，就踢出这个短的
                                iterator.remove();
                            }
                        }
                    }
                    //短的全部遍历结束也没有匹配上，说明 长的那个是多出来的
                    if (isEnd) {
                        //第二个长，第二个多出来，val1 就设为空
                        simple2 = secondInfoList.get(i);
                        FieldInfo f1 = new FieldInfo(
                                simple2.getFieldName(),
                                simple2.getFieldType(),
                                null,

                                simple2.getFieldType(),
                                simple2.getFieldVal(),
                                simple2.getFieldNote(),
                                simple2.getFieldDescribe());
                        val.add(f1);
                    }
                    //first长
                } else {
                    Iterator<SimpleFieldInfo> iterator = secondInfoList.iterator();
                    while (iterator.hasNext()) {
                        SimpleFieldInfo next = iterator.next();
                        if (isEnd) {
                            //字段相同的，second短
                            if (next.getFieldName().equals(firstInfoList.get(i).getFieldName()) &&
                                    next.getFieldNote().equals(firstInfoList.get(i).getFieldNote())) {
                                simple1 = firstInfoList.get(i);
                                simple2 = next;
                                FieldInfo f1 = new FieldInfo();
                                f1.setFieldName(simple1.getFieldName());
                                f1.setFirstFieldType(simple1.getFieldType());
                                f1.setFirstVal(simple1.getFieldVal());
                                f1.setSecondFieldType(simple2.getFieldType());
                                f1.setSecondVal(simple2.getFieldVal());
                                f1.setFieldNote(simple1.getFieldNote());
                                f1.setFieldDescribe(simple1.getFieldDescribe());
                                val.add(f1);
                                isEnd = false;
                                iterator.remove();
                            }
                        }
                    }
                    //短的全部遍历结束也没有匹配上，说明 长的那个是多出来的
                    if (isEnd) {
                        //第一个长，第一个就是多出来，val2 就设为空
                        simple1 = firstInfoList.get(i);
                        FieldInfo f1 = new FieldInfo();
                        f1.setFieldName(simple1.getFieldName());
                        f1.setFirstFieldType(simple1.getFieldType());
                        f1.setFirstVal(simple1.getFieldVal());
                        f1.setSecondFieldType(simple1.getFieldType());
                        f1.setSecondVal(null);
                        f1.setFieldNote(simple1.getFieldNote());
                        f1.setFieldDescribe(simple1.getFieldDescribe());
                        val.add(f1);
                    }
                }
            }

            //有没有可能短的里面的值不在长的里面
            //second长
            if (isLong) {
                //第一个短的里面的值不在长 的里面
                //短的值存在，就说明不在长的里面
                if (!firstInfoList.isEmpty()) {
                    firstInfoList.forEach(fir -> {
                        //第一个长，第一个就是多出来，val2 就设为空
                        SimpleFieldInfo var1 = fir;
                        FieldInfo f1 = new FieldInfo();
                        f1.setFieldName(var1.getFieldName());
                        f1.setFirstFieldType(var1.getFieldType());
                        f1.setFirstVal(var1.getFieldVal());
                        f1.setSecondFieldType(var1.getFieldType());
                        f1.setSecondVal(null);
                        f1.setFieldNote(var1.getFieldNote());
                        f1.setFieldDescribe(var1.getFieldDescribe());
                        val.add(f1);
                    });
                }
                //第一个长
            } else {
                //第二个短   短里面的值不在长的里面
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
        List<SimpleFieldInfo> infoList = new ArrayList<>();
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
                            List<SimpleFieldInfo> list = getSimpleFieldInfo(obj1);
                            infoList.addAll(list);
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
