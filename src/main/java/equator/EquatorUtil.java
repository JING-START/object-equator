package equator;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EquatorUtil {

    /**
     * 属性比较对象
     */
    private static final BaseFieldEquator fieldBaseEquator = new BaseFieldEquator();

    /**
     * 返回被修改的属性值
     * return the modified attribute value
     *
     * @param first  old object
     * @param second new object
     * @return new diff fields
     */
    public static List<FieldInfo> getDifferentFields(Object first, Object second) {
        return fieldBaseEquator.getDifferentFields(first, second);
    }
}
