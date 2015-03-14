package adt;

public class TestBinarySearchTree extends TestAdt {

    @Override
    public void setUp() {

        d = new BinarySearchTree<String,Integer>();
    }

    @Override
    public void tearDown() {
        d = null;
    }

}
