package pl.edu.uj.dataframe;

import java.util.Objects;

public class BoolValue extends Value {

    private BoolValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        BoolValue v = new BoolValue();
        if ("true".equalsIgnoreCase(s) || "1".equalsIgnoreCase(s)) {
            v.val = true;
        } else if ("false".equalsIgnoreCase(s) || "0".equalsIgnoreCase(s)) {
            v.val = false;
        } else {
            throw new IllegalArgumentException("Only 'true', 'false', '1', '0' are supported but got '" + s + "'");
        }
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof BoolValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotNull((BoolValue) val);

        return BoolValue.builder()
                .setValue((boolean) this.val || (boolean) val.val)
                .build();
    }

    private void assertNotNull(BoolValue val) {
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
        BoolValue other1 = (BoolValue) other;
        return other1.val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(boolean value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = create(s).val;
            return this;
        }

        public BoolValue build() {
            BoolValue booleanValue = new BoolValue();
            booleanValue.val = valueToSet;
            return booleanValue;
        }
    }
}
