package my.spring.el.add;

import java.util.ArrayList;
import java.util.List;

public class SimpleGeneric {

    private List<Integer> integerList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList( List<Integer> integerList ) {
        this.integerList = integerList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList( List<String> stringList ) {
        this.stringList = stringList;
    }
}
