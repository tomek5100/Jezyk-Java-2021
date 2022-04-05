package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LongValueTest {

    @Test
    void test_LongValueBuilt_Equals_LongValueCreated() {
        LongValue longValue1 = LongValue.builder().setValue(123L).build();
        LongValue longValue2 = (LongValue) LongValue.create("123");
        assertEquals(longValue1, longValue2);
    }

    @Test
    void test_BuildLongValueNull_NullPointerException() {
        Long nullObject = null;
        LongValue.Builder builder = LongValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateLongValueNull_NumberFormatException(String invalidValue) {
        assertThrows(NumberFormatException.class, () -> LongValue.create(invalidValue));
    }

    @Test
    void test_LongValueAdd() {
        LongValue longValue1 = LongValue.builder().setValue(123L).build();
        LongValue longValue2 = LongValue.builder().setValue(456L).build();
        LongValue longValue1plus2 = (LongValue) longValue1.add(longValue2);

        LongValue longValue3 = LongValue.builder().setValue(579L).build();

        assertEquals(longValue1plus2, longValue3);
    }

    @Test
    void test_LongValue_AddBadValueType_IllegalArgumentException() {
        Value longValue1 = LongValue.builder().setValue(123L).build();
        Value intValue1 = IntValue.builder().setValue(123).build();

        assertThrows(IllegalArgumentException.class, () -> longValue1.add(intValue1));
    }
}
