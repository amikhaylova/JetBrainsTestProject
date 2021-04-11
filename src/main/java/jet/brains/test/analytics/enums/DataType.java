package jet.brains.test.analytics.enums;

import java.util.HashMap;
import java.util.function.Function;

public enum DataType {
    INTEGER, STRING, DOUBLE;
    public static HashMap<DataType, Function<String, Object>> dataTypeMap = new HashMap<>();

    static {
        for (DataType type : DataType.values()) {
            switch (type) {
                case INTEGER:
                    dataTypeMap.put(INTEGER, Integer::parseInt);
                    break;
                case STRING:
                    dataTypeMap.put(STRING, (x) -> x);
                    break;
                case DOUBLE:
                    dataTypeMap.put(DOUBLE, Double::parseDouble);
                    break;
            }
        }

    }
}
