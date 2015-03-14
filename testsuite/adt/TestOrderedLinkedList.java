package adt;

public class TestOrderedLinkedList extends TestAdt {

    @Override
    public void setUp() {

        d = new OrderedLinkedList<String,Integer>();
    }

    @Override
    public void tearDown() {
        d = null;
    }

}
