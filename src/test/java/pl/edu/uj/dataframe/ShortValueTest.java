package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortValueTest {

    @Test
    void test_ShortValueBuilt_Equals_ShortValueCreated() {
        ShortValue longValue1 = ShortValue.builder().setValue((short) 123).build();
        ShortValue longValue2 = (ShortValue) ShortValue.create("123");
        assertEquals(longValue1, longValue2);
    }

    @Test
    void test_BuildShortValueNull_NullPointerException() {
        Short nullObject = null;
        ShortValue.Builder builder = ShortValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateShortValueNull_NumberFormatException(String invalidValue) {
        assertThrows(NumberFormatException.class, () -> ShortValue.create(invalidValue));
    }

    @Test
    void test_ShortValueAdd() {
        ShortValue shortValue1 = ShortValue.builder().setValue((short) 123).build();
        ShortValue shortValue2 = ShortValue.builder().setValue((short) 456).build();
        ShortValue shortValue1plus2 = (ShortValue) shortValue1.add(shortValue2);

        ShortValue shortValue3 = ShortValue.builder().setValue((short) 579).build();

        assertEquals(shortValue1plus2, shortValue3);
    }

    @Test
    void test_ShortValue_AddBadValueType_IllegalArgumentException() {
        Value shortValue1 = ShortValue.builder().setValue((short) 123).build();
        Value intValue1 = IntValue.builder().setValue(123).build();

        assertThrows(IllegalArgumentException.class, () -> shortValue1.add(intValue1));
    }
}
