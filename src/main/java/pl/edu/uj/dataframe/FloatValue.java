package pl.edu.uj.dataframe;

import java.util.Objects;

public class FloatValue extends Value {

    static final double THRESHOLD = .00001f;

    private FloatValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        FloatValue v = new FloatValue();
        v.val = Float.valueOf(s);
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof FloatValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotNull((FloatValue) val);

        return FloatValue.builder()
                .setValue((float) this.val + (float) val.val)
                .build();
    }

    private void assertNotNull(FloatValue val) {
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
        FloatValue other1 = (FloatValue) other;
        /*
         * https://howtodoinjava.com/java-examples/correctly-compare-float-double/
         */
        return Math.abs((float) other1.val - (float) this.val) < THRESHOLD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "FloatValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(float value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = FloatValue.create(s).val;
            return this;
        }

        public FloatValue build() {
            FloatValue floatValue = new FloatValue();
            floatValue.val = valueToSet;
            return floatValue;
        }
    }
}
