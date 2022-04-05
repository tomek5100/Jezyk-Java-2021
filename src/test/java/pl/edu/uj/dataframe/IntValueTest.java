package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntValueTest {

    @Test
    void test_IntValueBuilt_Equals_IntValueCreated() {
        IntValue intValue1 = IntValue.builder().setValue(123).build();
        IntValue intValue2 = (IntValue) IntValue.create("123");
        assertEquals(intValue1, intValue2);
    }

    @Test
    void test_BuildIntValueNull_NullPointerException() {
        Integer nullObject = null;
        IntValue.Builder builder = IntValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateIntValueNull_NumberFormatException(String invalidValue) {
        IntValue intValue1 = IntValue.builder().setValue(123).build();
        assertThrows(NumberFormatException.class, () -> IntValue.create(invalidValue));
    }

    @Test
    void test_IntValueAdd() {
        IntValue intValue1 = IntValue.builder().setValue(123).build();
        IntValue intValue2 = IntValue.builder().setValue(456).build();
        IntValue intValue1plus2 = (IntValue) intValue1.add(intValue2);

        IntValue intValue3 = IntValue.builder().setValue(579).build();

        assertEquals(intValue1plus2, intValue3);
    }

    @Test
    void test_IntValue_AddBadValueType_IllegalArgumentException() {
        Value intValue1 = IntValue.builder().setValue(123).build();
        Value longValue1 = LongValue.builder().setValue(123L).build();

        assertThrows(AssertionError.class, () -> intValue1.add(longValue1));
    }
}
