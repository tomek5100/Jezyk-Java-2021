package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoolValueTest {

    @Test
    void test_BoolValueBuilt_Equals_BoolValueCreated() {
        BoolValue boolValue1 = BoolValue.builder().setValue(true).build();
        BoolValue boolValue2 = (BoolValue) BoolValue.create("true");
        BoolValue boolValue3 = (BoolValue) BoolValue.create("1");
        assertEquals(boolValue1, boolValue2);
        assertEquals(boolValue1, boolValue3);

        BoolValue boolValue4 = BoolValue.builder().setValue(false).build();
        BoolValue boolValue5 = (BoolValue) BoolValue.create("false");
        BoolValue boolValue6 = (BoolValue) BoolValue.create("0");
        assertEquals(boolValue4, boolValue5);
        assertEquals(boolValue4, boolValue6);
    }

    @Test
    void test_BuildBoolValueNull_NullPointerException() {
        Boolean nullObject = null;
        BoolValue.Builder builder = BoolValue.builder();
        assertThrows(NullPointerException.class, () -> builder.setValue(nullObject));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"null", "abcdef123"})
    void test_CreateBoolValueNull_NumberFormatException(String invalidValue) {
        assertThrows(IllegalArgumentException.class, () -> BoolValue.create(invalidValue));
    }

    @Test
    void test_BoolValueAdd() {
        BoolValue boolValue1 = BoolValue.builder().setValue(true).build();
        BoolValue boolValue2 = BoolValue.builder().setValue(false).build();
        BoolValue boolValue1plus2 = (BoolValue) boolValue1.add(boolValue2);

        BoolValue boolValue3 = BoolValue.builder().setValue(true).build();

        assertEquals(boolValue1plus2, boolValue3);
    }

    @Test
    void test_BoolValue_AddBadValueType_IllegalArgumentException() {
        Value booleanValue1 = BoolValue.builder().setValue(true).build();
        Value intValue1 = IntValue.builder().setValue(123).build();

        assertThrows(IllegalArgumentException.class, () -> booleanValue1.add(intValue1));
    }
}
