package org.example;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.example.UnsafeGetter.unsafe;

public class ClassDescriptor {
    public final String name;
    private final Klass raw;

    public static ClassDescriptor of(Object obj) {
        return new ClassDescriptor(Klass.of(obj));
    }

    public static ClassDescriptor of(Class<?> clazz) {
        return new ClassDescriptor(Klass.of(clazz));
    }

    public static ClassDescriptor atAddress(long address) {
        return new ClassDescriptor(Klass.atAddress(address));
    }

    public ClassDescriptor theSuper() {
        return atAddress(raw._super);
    }

    public List<ClassDescriptor> primarySupers() {
        return stream(raw.primarySupers)
                .filter(address -> address != 0)
                .mapToObj(ClassDescriptor::atAddress)
                .collect(toUnmodifiableList());
    }

    public List<ClassDescriptor> secondarySupers() {
        return stream(AddressUtils.arrayAt(raw.secondarySupers))
                .mapToObj(ClassDescriptor::atAddress)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<ClassDescriptor> nextSibling() {
        return raw.nextSibling == 0 ? Optional.empty() : Optional.of(atAddress(raw.nextSibling));
    }

    public Optional<ClassDescriptor> subclass() {
        return raw.subclass == 0 ? Optional.empty() : Optional.of(atAddress(raw.subclass));
    }

    private ClassDescriptor(Klass raw) {
        this.raw = raw;
        this.name = getName();
    }

    private String getName() {
        var unsafe = unsafe();
        var length = unsafe.getByte(raw.name);
        var offset = 6; // for whatever reason
        var string = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            var val = unsafe.getByte(raw.name + i);
            string.append((char) val);
        }
        return string.toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClassDescriptor.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
