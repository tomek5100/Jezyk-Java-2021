package pl.edu.uj.dataframe;

import java.util.Objects;

public class IntValue extends Value {

    private IntValue() {
    }

    /*
     * See: https://stackoverflow.com/questions/2475259/can-i-override-and-overload-static-methods-in-java/5436790
     */
    public static Value create(String s) {
        IntValue v = new IntValue();
        v.val = Integer.valueOf(s);
        return v;
    }

    @Override
    public Value add(Value val) {
        /*
         * TODO how to improve? HINT SonarLint?
         **/
        assert val instanceof IntValue;
        assert val.val != null;

        return IntValue.builder()
                .setValue((int) this.val + (int) val.val)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        IntValue other1 = (IntValue) other;
        return other1.val.equals(this.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "value=" + val +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Object valueToSet;

        public Builder setValue(int value) {
            valueToSet = value;
            return this;
        }

        public Builder setValue(String s) {
            valueToSet = IntValue.create(s).val;
            return this;
        }

        public IntValue build() {
            IntValue intValue = new IntValue();
            intValue.val = valueToSet;
            return intValue;
        }
    }
}
