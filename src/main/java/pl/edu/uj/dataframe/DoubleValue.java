package pl.edu.uj.dataframe;

import java.util.Objects;

public class DoubleValue extends Value {

    static final double THRESHOLD = .00001f;

    private DoubleValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        DoubleValue v = new DoubleValue();
        v.val = Double.valueOf(s);
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof DoubleValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotNull((DoubleValue) val);

        return DoubleValue.builder()
                .setValue((double) this.val + (double) val.val)
                .build();
    }

    private void assertNotNull(DoubleValue val) {
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
        DoubleValue other1 = (DoubleValue) other;
        /*
         * https://howtodoinjava.com/java-examples/correctly-compare-double-double/
         */
        return Math.abs((double) other1.val - (double) this.val) < THRESHOLD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "DoubleValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(double value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = create(s).val;
            return this;
        }

        public DoubleValue build() {
            DoubleValue doubleValue = new DoubleValue();
            doubleValue.val = valueToSet;
            return doubleValue;
        }
    }
}
