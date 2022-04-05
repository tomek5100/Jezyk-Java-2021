package pl.edu.uj.dataframe;

import java.util.Objects;

public class ShortValue extends Value {

    private ShortValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        ShortValue v = new ShortValue();
        v.val = Short.valueOf(s);
        return v;
    }

    @Override
    public Value add(Value val) {
        if (!(val instanceof ShortValue)) {
            throw new IllegalArgumentException("Should be of type '" + this.getClass() + "', but got '" + val.getClass() + "'");
        }
        assertNotNull((ShortValue) val);

        return ShortValue.builder()
                .setValue((short) ((short) this.val + (short) val.val))
                .build();
    }

    private void assertNotNull(ShortValue val) {
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
        ShortValue other1 = (ShortValue) other;
        return other1.val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "ShortValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(short value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = ShortValue.create(s).val;
            return this;
        }

        public ShortValue build() {
            ShortValue shortValue = new ShortValue();
            shortValue.val = valueToSet;
            return shortValue;
        }
    }
}
