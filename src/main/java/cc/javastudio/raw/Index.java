package cc.javastudio.raw;

public final class Index {
    private static Index index;
    public int test;

    private Index(){ }

    public static Index getInstance(){
        if(index == null){
            index = new Index();
        }
        return index;
    }
}
