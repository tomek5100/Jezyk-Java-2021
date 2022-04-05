package pl.edu.uj.dataframe;

import java.util.ArrayList;
import java.util.List;

public class Series {

    private String name;
    private String type;
    private List<Value> data;

    protected Series(String name, String type, List data) {
        this.name = name;
        this.type = type;
        /*
         * Accept both list of Value's and list of "ordinary" types, eg. Integer, String, ...
         */
        if (data.isEmpty()) {
            this.data = (List<Value>) data;
        } else {
            this.data = new ArrayList<>(data.size());
            for (Object o : data) {
                if (o instanceof Value) {
                    this.data = (List<Value>) data;
                    break;
                } else {
                    addValue(o, type);
                }
            }
        }
    }

    protected Series(String name, String type) {
        this.name = name;
        this.type = type;
        if (!"int".equals(type) && !"String".equals(type) && !"long".equals(type) && !"short".equals(type) && !"float".equals(type) && !"double".equals(type) && !"boolean".equals(type)) {
            throw new IllegalArgumentException("Unknown column type= " + type);
        }
        this.data = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int size() {
        return data.size();
    }

    protected void addValue(Object value, String type) {
        assert this.type.equals(type);
        switch (type) {
            case "int":
                data.add(IntValue.builder().setValue((int) value).build());
                break;
            case "String":
                data.add(StringValue.builder().setValue((String) value).build());
                break;
            case "long":
                data.add(LongValue.builder().setValue((long) value).build());
                break;
            case "short":
                data.add(ShortValue.builder().setValue((short) value).build());
                break;
            case "float":
                data.add(FloatValue.builder().setValue((float) value).build());
                break;
            case "double":
                data.add(DoubleValue.builder().setValue((double) value).build());
                break;
            case "boolean":
                data.add(BoolValue.builder().setValue((boolean) value).build());
                break;
            default:
                throw new IllegalArgumentException("Unknown column type= " + type);
        }
    }

    protected void addValue(String value, String type) {
        assert this.type.equals(type);
        switch (type) {
            case "int":
                data.add(IntValue.builder().setValue(value).build());
                break;
            case "String":
                data.add(StringValue.builder().setValue(value).build());
                break;
            case "long":
                data.add(LongValue.builder().setValue(value).build());
                break;
            case "short":
                data.add(ShortValue.builder().setValue(value).build());
                break;
            case "float":
                data.add(FloatValue.builder().setValue(value).build());
                break;
            case "double":
                data.add(DoubleValue.builder().setValue(value).build());
                break;
            case "boolean":
                data.add(BoolValue.builder().setValue(value).build());
                break;
            default:
                throw new IllegalArgumentException("Unknown column type= " + type);
        }
    }

    public List<Value> getData() {
        return data;
    }

    Series copy(boolean deep) {
        if (deep) {
            // https://www.infoworld.com/article/2077343/java-s-primitive-wrappers-are-written-in-stone.html
            ArrayList<Value> newData = new ArrayList<>(data);
            return new Series(name, type, newData);
        } else {
            return new Series(name, type, data);
        }

    }

    Series getSliceCopy(int from, int to) {
        assert this.size() > to;
        // https://stackoverflow.com/questions/1480663/how-can-i-slice-an-arraylist-out-of-an-arraylist-in-java
        ArrayList<Value> newData = new ArrayList<>(data.subList(from, to + 1));
        return new Series(name, type, newData);
    }

    public void setValue(int i, String value, String type) {
        // TODO use SonarLint inspection and correct
        assert i < size();
        assert type.equals(this.type);

        switch (type) {
            case "int":
                data.set(i, IntValue.builder().setValue(value).build());
                break;
            case "String":
                data.set(i, StringValue.builder().setValue(value).build());
                break;
            case "long":
                data.set(i, LongValue.builder().setValue(value).build());
                break;
            case "short":
                data.set(i, ShortValue.builder().setValue(value).build());
                break;
            case "float":
                data.set(i, FloatValue.builder().setValue(value).build());
                break;
            case "double":
                data.set(i, DoubleValue.builder().setValue(value).build());
                break;
            case "boolean":
                data.set(i, BoolValue.builder().setValue(value).build());
                break;
            default:
                throw new IllegalArgumentException("Unknown column type= " + type);
        }
    }
}
