package equator;


import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 基础属性比较器
 *
 * @author 123先生
 */
@Slf4j
public class BaseFieldEquator extends AbstractEquator {

    /**
     * 返回被修改的属性值
     * return the modified attribute value
     *
     * @param first  old object
     * @param second new object
     * @return new diff fields
     */
    @Override
    public List<EquatorFieldInfo> getDifferentFields(Object first, Object second) {
        if (first == second || Objects.equals(first, second)) {
            return Collections.emptyList();
        }
        if (super.isSimpleField(first, second)) {
            //基础类型比较
            return super.compareSimpleField(first, second);
        }
        //对象比较
        //获取对象属性集合
        List<SimpleFieldInfo> firstInfoList = this.doHandler(first);
        List<SimpleFieldInfo> secondInfoList = this.doHandler(second);
        List<EquatorFieldInfo> val = new LinkedList<>();
        Iterator<SimpleFieldInfo> firstIterator = firstInfoList.iterator();
        while (firstIterator.hasNext()) {
            SimpleFieldInfo firstNext = firstIterator.next();
            Iterator<SimpleFieldInfo> secondIterator = secondInfoList.iterator();
            while (secondIterator.hasNext()) {
                SimpleFieldInfo secondNext = secondIterator.next();
                //字段属性名称、字段中文名称相同的
                if (firstNext.getFieldName().equals(secondNext.getFieldName()) &&
                        firstNext.getFieldNote().equals(secondNext.getFieldNote())) {
                    EquatorFieldInfo f1 = new EquatorFieldInfo()
                            .setFieldName(firstNext.getFieldName())
                            .setFieldNote(firstNext.getFieldNote())
                            .setFieldDescribe(firstNext.getFieldDescribe())
                            .setFirstFieldType(firstNext.getFieldType())
                            .setFirstVal(firstNext.getFieldVal())
                            .setSecondFieldType(secondNext.getFieldType())
                            .setSecondVal(secondNext.getFieldVal());

                    val.add(f1);
                    //secondInfoList匹配上了，移除，以secondInfoList为主数据
                    secondIterator.remove();
                    //比较第一个匹配的
                    break;
                }
            }
        }
        //secondInfoList有剩余没匹配，说明是新增的
        if (!secondInfoList.isEmpty()) {
            secondInfoList.forEach(sec -> {
                EquatorFieldInfo f1 = new EquatorFieldInfo()
                        .setFieldName(sec.getFieldName())
                        .setFieldNote(sec.getFieldNote())
                        .setFieldDescribe(sec.getFieldDescribe())
                        .setFirstFieldType(sec.getFieldType())
                        .setFirstVal(null)
                        .setSecondFieldType(sec.getFieldType())
                        .setSecondVal(sec.getFieldVal());
                val.add(f1);
            });
        }
        List<EquatorFieldInfo> diffFields = new LinkedList<>();
        val.forEach(info -> {
            if (!super.isFieldEquals(info)) {
                diffFields.add(info);
            }
        });
        return diffFields;

    }

    /**
     * 处理解析对象，获取需要比对的基础属性
     *
     * @param obj
     * @return
     */
    private List<SimpleFieldInfo> doHandler(Object obj) {
        List<SimpleFieldInfo> infoList = new LinkedList<>();
        if (super.isCollection(obj)) {
            //Collection类型比较
            Collection<?> list = (Collection) obj;
            infoList.addAll(this.parseCollectionFieldInfo(list));
            return infoList;
        } else if (super.isMap(obj)) {
            //Map类型比较
            Map map = (Map) obj;
            infoList.addAll(this.parseMapFieldInfo(map));
            return infoList;
        } else {
            //解析对象
            infoList.addAll(this.parseObject(obj));
        }
        return infoList;
    }

    /**
     * 解析集合对象
     *
     * @param obj
     * @return
     */
    private List<SimpleFieldInfo> parseObject(Object obj) {
        List<SimpleFieldInfo> infoList = new LinkedList<>();
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                //判断字段上是否有注解@EqualsAnnotation
                EqualsAnnotation equalsAnnotation = field.getAnnotation(EqualsAnnotation.class);
                //判断属性类型，基础类型
                if (super.isSimpleField(type)) {
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
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        Collection list = (Collection) super.getFieldValueByName(field.getName(), obj);
                        infoList.addAll(this.parseCollectionFieldInfo(list));
                    }
                } else if (Map.class.equals(type)) {
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        Map map = (Map) super.getFieldValueByName(field.getName(), obj);
                        infoList.addAll(this.parseMapFieldInfo(map));
                    }
                } else {
                    if (field.isAnnotationPresent(EqualsAnnotation.class)) {
                        PropertyDescriptor descriptor = null;
                        try {
                            descriptor = new PropertyDescriptor(field.getName(), cls);
                            Method method = descriptor.getReadMethod();
                            Object obj1 = null;
                            obj1 = method.invoke(obj);
                            Optional.ofNullable(obj1).ifPresent(obj2 -> infoList.addAll(this.doHandler(obj2)));
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
     * 解析集合对象
     *
     * @param list
     * @return
     */
    private List<SimpleFieldInfo> parseCollectionFieldInfo(Collection<?> list) {
        List<SimpleFieldInfo> infoList = new LinkedList<>();
        Optional.ofNullable(list).ifPresent(info -> {
            for (Object next : list) {
                List<SimpleFieldInfo> simpleFieldInfo = this.doHandler(next);
                Optional.of(simpleFieldInfo).ifPresent(infoList::addAll);
            }
        });
        return infoList;
    }

    /**
     * 解析Map对象
     *
     * @param map
     * @return
     */
    private List<SimpleFieldInfo> parseMapFieldInfo(Map map) {
        List<SimpleFieldInfo> infoList = new LinkedList<>();
        Optional.ofNullable(map).ifPresent(info -> {
            map.forEach((k, v) -> {
                infoList.addAll(this.doHandler(v));
            });
        });
        return infoList;
    }


}
