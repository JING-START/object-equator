package demo;

import equator.EquatorUtil;
import equator.FieldInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.List;


@Slf4j
public class MainTest {

    /**
     * 单个对象比较
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
        ExampleEntityTwo t1 = new ExampleEntityTwo(
                "1".getBytes(StandardCharsets.UTF_8)[0],
                s,
                3,
                4L,
                5F,
                6D,
                '7',
                false,
                "8");
        example1.setTwo(t1);

        ExampleEntityOne example2 = new ExampleEntityOne();
        example2.setByteExample("2".getBytes(StandardCharsets.UTF_8)[0]);
        short s2 = 123;
        example2.setShortExample(s2);
        example2.setIntegerExample(1);
        example2.setLongExample(2L);
        example2.setFloatExample(3.0F);
        example2.setDoubleExample(4.0);
        example2.setCharacterExample('5');
        example2.setBooleanExample(false);
        example2.setStringExample("7");
        ExampleEntityTwo t2 = new ExampleEntityTwo(
                "1".getBytes(StandardCharsets.UTF_8)[0],
                s2,
                3,
                4L,
                5F,
                6D,
                '7',
                true,
                "8");
        example2.setTwo(t2);
        List<FieldInfo> differentFields = EquatorUtil.getDifferentFields(example1, example2);
        differentFields.forEach(System.out::println);
        //输出
        //{Boolean中文名称:false},{属性描述:Boolean属性描述}
        //{String中文名称:7},{属性描述:String属性描述}
    }
}
