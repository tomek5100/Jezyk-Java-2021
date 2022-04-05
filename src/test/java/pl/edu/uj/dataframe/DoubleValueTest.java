package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoubleValueTest {

    @Test
    void test_DoubleValueBuilt_Equals_DoubleValueCreated() {
        DoubleValue doubleValue1 = DoubleValue.builder().setValue(12.3d).build();
        DoubleValue doubleValue2 = (DoubleValue) DoubleValue.create("12.3d");
        assertEquals(doubleValue1, doubleValue2);
    }

    @Test
    void test_BuildDoubleValueNull_NullPointerException() {
        Double nullObject = null;
        DoubleValue.Builder builder = DoubleValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateDoubleValueNull_NumberFormatException(String invalidValue) {
        assertThrows(Exception.class, () -> DoubleValue.create(invalidValue));
    }

    @Test
    void test_DoubleValueAdd() {
        DoubleValue doubleValue1 = DoubleValue.builder().setValue(12.3d).build();
        DoubleValue doubleValue2 = DoubleValue.builder().setValue(45.6d).build();
        DoubleValue doubleValue1plus2 = (DoubleValue) doubleValue1.add(doubleValue2);

        DoubleValue doubleValue3 = DoubleValue.builder().setValue(57.9d).build();

        assertEquals(doubleValue1plus2, doubleValue3);
    }

    @Test
    void test_DoubleValue_AddBadValueType_IllegalArgumentException() {
        Value doubleValue1 = DoubleValue.builder().setValue(12.3d).build();
        Value intValue1 = IntValue.builder().setValue(123).build();

        assertThrows(IllegalArgumentException.class, () -> doubleValue1.add(intValue1));
    }
}
