package pl.edu.uj.dataframe;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Value {

    private static final Logger logger = LoggerFactory.getLogger(Value.class);
    protected Object val;

    /*
     * See: https://stackoverflow.com/questions/370962/why-cant-static-methods-be-abstract-in-java
     */
    public static Value create(String s) {
        throw new NotImplementedException();
    }

    public abstract Value add(Value val);

    public Object getVal() {
        return val;
    }


/*
    TODO implement:

    public abstract Value sub(Value val);

    public abstract Value mul(Value val);

    public abstract Value div(Value val);

    public abstract Value pow(Value val);

    public abstract boolean eq(Value val);

    public abstract boolean lte(Value val);

    public abstract boolean gte(Value val);

    public abstract boolean neq(Value val); */

    public abstract boolean equals(Object other);

    public abstract int hashCode();

    public void print() {
        logger.info(val.toString());
    }

}
