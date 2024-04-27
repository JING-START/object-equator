# object-equator

对象属性比较器，旧对象和新对象比较，获取新对象被修改的属性

* 目前支持对象属性基础类型比较，包括String,支持对象嵌套
* 支持集合属性类型，Collection,Map
* 如果觉得有用，麻烦点个star

<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

本篇README.md面向开发者

## 目录

- [上手指南](#上手指南)
    - [技术架构](#技术架构)
    - [开发前的配置要求](#开发前的配置要求)
    - [安装步骤](#安装步骤)
- [文件目录说明](#文件目录说明)
- [部署](#部署)
- [版本控制](#版本控制)
- [作者](#作者)
- [鸣谢](#鸣谢)

### 上手指南

###### 技术架构

实现方式：使用java反射机制获取比较对象属性  
如果对象属性是基础类型或String并且添加了比较注解，例：@EqualsAnnotation(value = "String中文名称1", describe = "String属性描述")，那么就会比较该属性值  
如果是比较自定义对象bean或Collection、Map属性，如果添加了注解，则会递归进该引用对象，找到最低层属性基础类型或String

###### 开发前的配置要求

1. 作者jdk开发版本:jdk11

###### **安装步骤**

1. 复制[equator]目录即可使用

```
https://github.com/JING-START/object-equator.git
git clone https://github.com/JING-START/equator.git
```

### 文件目录说明

```
pom.xml
README.md
src
    └─main
        ├─java
        │  └─equator
        │          AbstractEquator.java
        │          BaseFieldEquator.java
        │          EqualsAnnotation.java
        │          Equator.java
        │          EquatorFieldInfo.java
        │          EquatorUtil.java
        │          SimpleFieldInfo.java
        │
        └─test
            └─demo
                    ExampleEntityOne.java
                    ExampleEntityThree.java
                    ExampleEntityTwo.java
                    MainTest.java
```

### 部署

* 被比较对象需要添加注解如：
  ``@EqualsAnnotation(value = "Byte中文名称1", describe = "Byte属性描述")``

* 调用工具类方法，传入比较对象example1,example2，返回example2被修改的属性值,具体可以运行测试类
```
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
```



### 版本控制

该项目使用Git进行版本管理。您可以在repository参看当前可用版本。

### 作者

* 912176434@qq.com
* zhoujingtong123@foxmail.com
* yezeyue@gamil.com

*您也可以在贡献者名单中参看所有参与该项目的开发者。*

### 版权说明

该项目签署了Apache License Version
2.0授权许可，详情请参阅 [LICENSE](https://github.com/JING-START/equator/blob/main/LICENSE)

### 鸣谢

- [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
- [Img Shields](https://shields.io)
- [Choose an Open Source License](https://choosealicense.com)
- [GitHub Pages](https://pages.github.com)
- [dadiyang](https://github.com/dadiyang/equator)

<!-- links -->

[your-project-path]:https://github.com/JING-START/equator

[contributors-shield]: https://img.shields.io/github/contributors/JING-START/equator?style=flat-square

[contributors-url]: https://github.com/JING-START/equator/pulse

[forks-shield]: https://img.shields.io/github/forks/JING-START/equator?style=flat-square

[forks-url]: https://github.com/JING-START/equator/forks

[stars-shield]: https://img.shields.io/github/stars/JING-START/equator?style=flat-square

[stars-url]: https://github.com/JING-START/equator/stargazers

[issues-shield]: https://img.shields.io/github/issues/JING-START/equator?style=flat-square

[issues-url]: https://github.com/JING-START/equator/issues

[license-shield]: https://img.shields.io/github/license/JING-START/equator?style=flat-square

[license-url]: https://github.com/JING-START/equator/blob/main/LICENSE




