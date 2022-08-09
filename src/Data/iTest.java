package Data;

import Model.Test;

public interface iTest {
    Test[] getAllNames();
    Test getName(int ID);
    Test getName(String name);
    void addName(Test item);
    boolean removeName(Test item);
}
