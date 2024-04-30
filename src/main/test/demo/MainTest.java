package demo;

import equator.EquatorUtil;
import equator.EquatorFieldInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


import java.nio.charset.StandardCharsets;

import java.util.List;


@Slf4j
public class MainTest {

    /**
     * 单个对象基础比较
     */
    @Test
    public void testSingleObject() {
        ExampleEntityOne example1 = new ExampleEntityOne();
        example1.setBooleanExample(true);
        example1.setStringExample("6");
        ExampleEntityOne example2 = new ExampleEntityOne();
        example2.setByteExample("1".getBytes(StandardCharsets.UTF_8)[0]);
        example2.setBooleanExample(false);
        example2.setStringExample("7");
        List<EquatorFieldInfo> differentFields = EquatorUtil.BASE_FIELD_EQUATOR.getDifferentFields(example1, example2);
        differentFields.forEach(info -> {
            System.out.println("属性名: " + info.getFieldName());
            System.out.println("属性中文名: " + info.getFieldNote());
            System.out.println("属性描述: " + info.getFieldDescribe());
            System.out.println("old属性类型: " + info.getFirstFieldType());
            System.out.println("old属性值: " + info.getFirstVal());
            System.out.println("new属性值: " + info.getSecondVal());
            System.out.println("====================================================");
        });
    }

    /**
     * 级联对象比较
     */
    @Test
    public void testCascadingObject() {
        ExampleEntityOne example1 = new ExampleEntityOne();
        example1.setShortExample((short) 123);

        ExampleEntityTwo two1 = new ExampleEntityTwo();
        two1.setLongExample(111L);

        ExampleEntityThree three = new ExampleEntityThree();
        three.setStringExample("ExampleEntityTwo two1 string three");
        two1.setThree(three);
        example1.setTwo(two1);

        ExampleEntityOne example2 = new ExampleEntityOne();
        example2.setShortExample((short) 1234);

        ExampleEntityTwo two2 = new ExampleEntityTwo();
        two2.setLongExample(2222L);

        example2.setTwo(two2);
        ExampleEntityThree three1 = new ExampleEntityThree();
        three1.setStringExample("ExampleEntityTwo two2 string three");
        two2.setThree(three1);
        example2.setTwo(two2);

        List<EquatorFieldInfo> differentFields = EquatorUtil.BASE_FIELD_EQUATOR.getDifferentFields(example1, example2);
        differentFields.forEach(info -> {
            System.out.println("属性名: " + info.getFieldName());
            System.out.println("属性中文名: " + info.getFieldNote());
            System.out.println("属性描述: " + info.getFieldDescribe());
            System.out.println("old属性类型: " + info.getFirstFieldType());
            System.out.println("old属性值: " + info.getFirstVal());
            System.out.println("new属性值: " + info.getSecondVal());
            System.out.println("====================================================");
        });
    }


    /**
     * 对象集合属性基础比较
     */
    @Test
    public void testCollectionObject() {
        ExampleEntityOne example1 = new ExampleEntityOne();
        example1.setStringExample("String属性值1");
        ExampleEntityOne example2 = new ExampleEntityOne();
        example2.setStringExample("String属性值2");


        List<EquatorFieldInfo> differentFields = EquatorUtil.BASE_FIELD_EQUATOR.getDifferentFields(example1, example2);
        differentFields.forEach(info -> {
            System.out.println("属性名: " + info.getFieldName());
            System.out.println("属性中文名: " + info.getFieldNote());
            System.out.println("属性描述: " + info.getFieldDescribe());
            System.out.println("old属性类型: " + info.getFirstFieldType());
            System.out.println("old属性值: " + info.getFirstVal());
            System.out.println("new属性值: " + info.getSecondVal());
            System.out.println("====================================================");
        });
    }
}
