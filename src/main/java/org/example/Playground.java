package org.example;

import org.example.dummies.Dummy;
import org.example.dummies.DummyWithParents;

public class Playground {
    public static void main(String[] args) {
        var dummy = new Dummy();
        var dummyWParents = new DummyWithParents();

        var dummyKlass = Klass.of(dummy);
        System.out.println("dummyKlass = " + dummyKlass);
        var dummyWParentsKlass = Klass.of(dummyWParents);
        System.out.println("dummyWParentsKlass = " + dummyWParentsKlass);

        var dummyDescriptor = ClassDescriptor.of(Dummy.class);
        System.out.println("dummyDescriptor = " + dummyDescriptor);
        System.out.println("dummyDescriptor.subclass() = " + dummyDescriptor.subclass());
        var dummyWParentsDescriptor = ClassDescriptor.of(dummyWParents);
        System.out.println("dummyWParentsDescriptor = " + dummyWParentsDescriptor);
        System.out.println("dummyWParentsDescriptor.theSuper() = " + dummyWParentsDescriptor.theSuper());
        System.out.println("dummyWParentsDescriptor.primarySupers() = " + dummyWParentsDescriptor.primarySupers());
        System.out.println("dummyWParentsDescriptor.secondarySupers() = " + dummyWParentsDescriptor.secondarySupers());
    }
}
