package cc.javastudio.raw;

import java.util.HashMap;

public final class Index {
    private static Index index = new Index();

    private HashMap<Long, Long> rowIndex;

    private long totalRowNumber = 0;

    public int test = 0;

    private Index(){
        rowIndex = new HashMap<>();
    }

    public static Index getInstance(){

        return index;
    }

    public void add(long bytePosition){
        rowIndex.put(totalRowNumber, bytePosition);
        totalRowNumber++;

    }
    public void remove(Long row){
        rowIndex.remove(row);
        totalRowNumber--;
    }

    public long getTotalRowNumberOfRows(){
        return totalRowNumber;
    }

    public long getBytePosition(Long totalRowNumber){
        return rowIndex.getOrDefault(totalRowNumber, (long) -1);
    }
}
