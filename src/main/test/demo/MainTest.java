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
        example1.setByteExample("1".getBytes(StandardCharsets.UTF_8)[0]);
        short s = 123;
        example1.setShortExample(s);
        example1.setIntegerExample(1);
        example1.setLongExample(2L);
        example1.setFloatExample(3.0F);
        example1.setDoubleExample(4.0);
        example1.setCharacterExample('5');
        example1.setBooleanExample(true);
        example1.setStringExample("6");
        ExampleEntityOne example2 = new ExampleEntityOne();
        example2.setByteExample("1".getBytes(StandardCharsets.UTF_8)[0]);
        short s2 = 1234;
        example2.setShortExample(s2);
        example2.setIntegerExample(1);
        example2.setLongExample(2L);
        example2.setFloatExample(3.0F);
        example2.setDoubleExample(4.0);
        example2.setCharacterExample('5');
        example2.setBooleanExample(false);
        example2.setStringExample("7");
        List<EquatorFieldInfo> differentFields = EquatorUtil.BASE_FIELD_EQUATOR.getDifferentFields(example1, example2);
        differentFields.forEach(System.out::println);
        //example2 short、boolean、String值不一致
        //{{Short中文名称1:1234},{属性描述:Short属性描述}
        //{Boolean中文名称1:false},{属性描述:Boolean属性描述}
        //{String中文名称1:7},{属性描述:String属性描述}
    }

    /**
     * 级联对象基础比较
     */
    @Test
    public void testCascadingObject() {
        ExampleEntityThree three = new ExampleEntityThree();
        three.setStringExample("string three");

        ExampleEntityOne example1 = new ExampleEntityOne();
        short s = 123;
        example1.setShortExample(s);
        ExampleEntityTwo two1 = new ExampleEntityTwo();
        two1.setShortExample(s);
//        two1.setThree(three);
//        example1.setTwo(two1);

        ExampleEntityOne example2 = new ExampleEntityOne();
        short s2 = 1234;
        example2.setShortExample(s2);
        ExampleEntityTwo two2 = new ExampleEntityTwo();
        two2.setShortExample(s2);
        example2.setTwo(two2);

        two2.setThree(three);
        example2.setTwo(two2);

        List<EquatorFieldInfo> differentFields = EquatorUtil.BASE_FIELD_EQUATOR.getDifferentFields(example1, example2);
        differentFields.forEach(System.out::println);
        //example2 short、对象two.short值不一致
        //{Short中文名称1:1234},{属性描述:Short属性描述}
        //{Short中文名称2:1234},{属性描述:Short2属性描述}
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
        differentFields.forEach(System.out::println);
        //example2 short、对象two.short值不一致
        //{Short中文名称1:1234},{属性描述:Short属性描述}
        //{Short中文名称2:1234},{属性描述:Short2属性描述}
    }
}
