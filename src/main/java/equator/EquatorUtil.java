package equator;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class EquatorUtil {

    /**
     * 属性比较对象
     */
    private static final FieldBaseEquator fieldBaseEquator = new FieldBaseEquator();

    /**
     * 返回被修改的属性值
     * return the modified attribute value
     *
     * @param first  old object
     * @param second new object
     * @return new diff fields
     */
    public static List<FieldInfo> getDifferentFields(Object first, Object second) {
        if (first.getClass() != second.getClass()) {
            log.warn("比较对象类型不一致");
            return Collections.emptyList();
        } else if (first == second || Objects.equals(first, second)) {
            return Collections.emptyList();
        }
        return fieldBaseEquator.getDifferentFields(first, second);
    }
}
