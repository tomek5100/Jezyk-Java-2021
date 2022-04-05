package pl.edu.uj.dataframe;

import org.junit.platform.commons.util.StringUtils;

import java.util.Objects;

public class StringValue extends Value {

    private StringValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        assertNotEmptyOrBlank(s);
        StringValue v = new StringValue();
        v.val = s;
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof StringValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotEmptyOrBlank((String) val.val);

        return StringValue.builder()
                .setValue(this.val + (String) val.val)
                .build();
    }

    private static void assertNotEmptyOrBlank(String s) {
        if (StringUtils.isBlank(s)) {
            throw new IllegalArgumentException("Should not be blank, but got '" + s + "'");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        StringValue other1 = (StringValue) other;
        return other1.val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(String value) {
            assertNotEmptyOrBlank(value);
            valueToSet = value;
            return this;
        }

        public StringValue build() {
            StringValue stringValue = new StringValue();
            stringValue.val = valueToSet;
            return stringValue;
        }
    }
}
