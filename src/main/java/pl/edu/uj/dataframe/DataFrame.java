package pl.edu.uj.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataFrame {

    private ArrayList<String> colnames;
    private ArrayList<String> coltypes;
    private ArrayList<Series> data;

    public DataFrame(String[] colnames, String[] coltypes) {
        this.colnames = new ArrayList(Arrays.asList(colnames));
        this.coltypes = new ArrayList(Arrays.asList(coltypes));
        assert this.colnames.size() == this.coltypes.size();

        data = new ArrayList<>(this.colnames.size());

        Iterator<String> itColnames = this.colnames.iterator();
        Iterator<String> itColtypes = this.coltypes.iterator();
        while (itColnames.hasNext() && itColtypes.hasNext()) {
            data.add(new Series(itColnames.next(), itColtypes.next()));
        }
    }

    private DataFrame(List<String> colnames, List<String> coltypes,
                      List<Series> data) {
        this.colnames = new ArrayList(colnames);
        this.coltypes = new ArrayList(coltypes);
        this.data = new ArrayList(data);
    }

    public int size() {
        if (data.isEmpty()) {
            return 0;
        }
        final int size = data.get(0).size();
        for (Series s : data) {
            assert size == s.size();
        }
        return data.get(0).size();
    }

    public Series get(String colname) {
        int index = -1;
        for (String name : colnames) {
            index++;
            if (name.equals(colname)) {
                return data.get(index);
            }
        }
        // no column
        return null;
    }

    public void ilocSet(int i, String c, String value) {
        int index = -1;
        for (String colname : colnames) {
            index++;
            if (colname.equals(c)) {
                final Series series = data.get(index);
                series.setValue(i, value, coltypes.get(index));
                return;
            }
        }
        throw new IllegalArgumentException("Column '" + c + "' not in DataFrame");
    }

    public DataFrame iloc(int i) {
        return iloc(i, i);
    }

    public DataFrame iloc(int from, int to) {
        return get(colnames.toArray(new String[0]), true, from, to);
    }

    public DataFrame get(String[] cols, boolean copy) {
        return get(cols, copy, -1, -1);
    }

    public DataFrame get(String[] cols, boolean copy, int from, int to) {
        List<String> newColnames = new ArrayList<>();
        List<String> newColtypes = new ArrayList<>();
        List<Series> newData = new ArrayList<>();

        int index;
        for (String columnsToAddNames : cols) {
            index = -1;
            for (String colname : colnames) {
                index++;
                if (columnsToAddNames.equals(colname)) {
                    newColnames.add(columnsToAddNames);
                    newColtypes.add(this.coltypes.get(index));
                    final Series series = this.data.get(index);
                    if (from >= 0) {
                        // TODO use SonarLint inspection and correct
                        assert to >= from;
                        newData.add(series.getSliceCopy(from, to));
                    } else {
                        newData.add(series.copy(copy));
                    }
                }
            }
        }
        return new DataFrame(newColnames, newColtypes, newData);
    }

    public DataFrame addColumn(String colname, String dtype, boolean inplace) {
        DataFrame df;
        if (inplace) {
            df = this;
        } else {
            df = this.copy();
        }
        df.colnames.add(colname);
        df.coltypes.add(dtype);

        Series emptySeries = new Series(colname, dtype);
        for (int i = 0; i < df.size(); i++) {
            emptySeries.addValue("0", dtype);
        }
        df.data.add(emptySeries);

        return df;
    }

    protected DataFrame copy() {
        //https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
        return get(colnames.toArray(new String[0]), true);
    }

    public DataFrame addRow(String[] datarow, boolean inplace) {
        // TODO use SonarLint inspection and correct
        assert datarow.length == this.coltypes.size();

        DataFrame df;
        if (inplace) {
            df = this;
        } else {
            df = copy();
        }

        int i = -1;
        for (String type : df.coltypes) {
            i++;
            df.data.get(i).addValue(datarow[i], type);
        }

        return df;
    }

}
