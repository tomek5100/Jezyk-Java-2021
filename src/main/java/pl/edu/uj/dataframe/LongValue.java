package pl.edu.uj.dataframe;

import java.util.Objects;

public class LongValue extends Value {

    private LongValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        LongValue v = new LongValue();
        v.val = Long.valueOf(s);
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof LongValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotNull((LongValue) val);

        return LongValue.builder()
                .setValue((long) this.val + (long) val.val)
                .build();
    }

    private void assertNotNull(LongValue val) {
        if (val == null || val.val == null) {
            throw new IllegalArgumentException("Should not be null, but got '" + val + "'");
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
        LongValue other1 = (LongValue) other;
        return other1.val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "LongValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(long value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = LongValue.create(s).val;
            return this;
        }

        public LongValue build() {
            LongValue longValue = new LongValue();
            longValue.val = valueToSet;
            return longValue;
        }
    }
}
