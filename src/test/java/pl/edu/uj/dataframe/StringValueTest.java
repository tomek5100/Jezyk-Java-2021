package pl.edu.uj.dataframe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringValueTest {

    @Test
    void test_StringValueBuilt_Equals_StringValueCreated() {
        StringValue StringValue1 = StringValue.builder().setValue("abc").build();
        StringValue StringValue2 = (StringValue) StringValue.create("abc");
        assertEquals(StringValue1, StringValue2);
    }

    @Test
    void test_BuildStringValueNull_IllegalArgumentException() {
        String nullObj = null;
        StringValue.Builder builder = StringValue.builder();
        assertThrows(IllegalArgumentException.class, () -> builder.setValue(nullObj));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void test_CreateStringValueNull_IllegalArgumentException(String invalidString) {
        assertThrows(IllegalArgumentException.class, () -> StringValue.create(invalidString));
    }

    @Test
    void test_StringValueAdd() {
        StringValue stringValue1 = StringValue.builder().setValue("abc").build();
        StringValue stringValue2 = StringValue.builder().setValue("def").build();
        StringValue stringValue1plus2 = (StringValue) stringValue1.add(stringValue2);

        StringValue stringValue3 = StringValue.builder().setValue("abcdef").build();

        assertEquals(stringValue1plus2, stringValue3);
    }
}
