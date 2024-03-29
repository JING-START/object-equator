package demo;

import equator.EqualsAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExampleEntityTwo {

    @EqualsAnnotation(value = "Byte中文名称2", describe = "Byte属性描述")
    private Byte byteExample;

    @EqualsAnnotation(value = "Short中文名称2", describe = "Short属性描述")
    private Short shortExample;

    @EqualsAnnotation(value = "Integer中文名称2", describe = "Integer属性描述")
    private Integer integerExample;

    @EqualsAnnotation(value = "Long中文名称2", describe = "Long属性描述")
    private Long longExample;

    @EqualsAnnotation(value = "Float中文名称2", describe = "Float属性描述")
    private Float floatExample;

    @EqualsAnnotation(value = "Double中文名称2", describe = "Double属性描述")
    private Double doubleExample;

    @EqualsAnnotation(value = "Character中文名称2", describe = "Character属性描述")
    private Character characterExample;

    @EqualsAnnotation(value = "Boolean中文名称2", describe = "Boolean属性描述")
    private Boolean booleanExample;

    @EqualsAnnotation(value = "String中文名称2", describe = "String属性描述")
    private String stringExample;

}
