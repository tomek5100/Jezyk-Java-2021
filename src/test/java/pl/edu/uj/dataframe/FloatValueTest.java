package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FloatValueTest {

    @Test
    void test_FloatValueBuilt_Equals_FloatValueCreated() {
        FloatValue floatValue1 = FloatValue.builder().setValue(12.3f).build();
        FloatValue floatValue2 = (FloatValue) FloatValue.create("12.3f");
        assertEquals(floatValue1, floatValue2);
    }

    @Test
    void test_BuildFloatValueNull_NullPointerException() {
        Float nullObject = null;
        FloatValue.Builder builder = FloatValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateFloatValueNull_NumberFormatException(String invalidValue) {
        FloatValue floatValue1 = FloatValue.builder().setValue(12.3f).build();
        assertThrows(Exception.class, () -> FloatValue.create(invalidValue));
    }

    @Test
    void test_FloatValueAdd() {
        FloatValue floatValue1 = FloatValue.builder().setValue(12.3f).build();
        FloatValue floatValue2 = FloatValue.builder().setValue(45.6f).build();
        FloatValue floatValue1plus2 = (FloatValue) floatValue1.add(floatValue2);

        FloatValue floatValue3 = FloatValue.builder().setValue(57.9f).build();

        assertEquals(floatValue1plus2, floatValue3);
    }

    @Test
    void test_FloatValue_AddBadValueType_IllegalArgumentException() {
        Value floatValue1 = FloatValue.builder().setValue(12.3f).build();
        Value intValue1 = IntValue.builder().setValue(123).build();

        assertThrows(IllegalArgumentException.class, () -> floatValue1.add(intValue1));
    }
}
