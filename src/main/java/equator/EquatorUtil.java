package equator;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EquatorUtil {

    /**
     * 属性比较对象
     */
    private static final BaseFieldEquator BASE_FIELD_EQUATOR = new BaseFieldEquator();

    public static List<EquatorFieldInfo> getDifferentFields(Object first, Object second) {
        return BASE_FIELD_EQUATOR.getDifferentFields(first, second);
    }

}
