package Data;

import Model.Test;

public interface iTest {
    Test[] getAllNames();
    Test getName(int ID);
    void addName(Test item);
    boolean removeName(Test item);
}
