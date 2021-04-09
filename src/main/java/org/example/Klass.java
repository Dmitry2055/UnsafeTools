package org.example;

import java.util.Arrays;
import java.util.StringJoiner;

import static java.lang.System.arraycopy;
import static org.example.UnsafeGetter.unsafe;

/**
 * A raw representation of Klass structure
 *
 * @see <a href="http://hg.openjdk.java.net/jdk/jdk11/file/1ddf9a99e4ad/src/hotspot/share/oops/klass.hpp">here</>
 */
public class Klass {

    public static Klass of(Object obj) {
        return new Klass(readRaw(AddressUtils.classAddress(obj)));
    }

    public static Klass of(Class<?> clazz) {
        return new Klass(readRaw(AddressUtils.classAddress(clazz)));
    }

    public static Klass atAddress(long address) {
        return new Klass(readRaw(address));
    }

    private Klass(long[] raw) {
        this.vtblPtr = raw[0];
        this.layoutHelper = raw[1];
        this.superCheckOffset = raw[2];
        this.name = raw[3];
        this.secondarySuperCache = raw[4];
        this.secondarySupers = raw[5];
        arraycopy(raw, 6, primarySupers, 0, primarySupers.length);
        this.javaMirror = raw[14];
        this._super = raw[15];
        this.subclass = raw[16];
        this.nextSibling = raw[17];
        this.nextLink = raw[18];
        this.classLoaderData = raw[19];
        this.modifiedClass = raw[20];
        this.accessFlags = raw[21];
        this.lastBiasedLockBulkRevocationTime = raw[22];
        this.prototypeHeader = raw[23];
        this.biasedLockRevocationCount = raw[24];
        this.modifiedOops = raw[25];
        this.accumulatedModifiedOops = raw[26];
        this.traceId = raw[27];
    }

    private final int primarySupersCount = 8;

    public final long vtblPtr;
    public final long layoutHelper;
    public final long superCheckOffset;
    public final long name;
    public final long secondarySuperCache;
    public final long secondarySupers;
    public final long[] primarySupers = new long[primarySupersCount];
    public final long javaMirror;
    public final long _super;
    public final long subclass;
    public final long nextSibling;
    public final long nextLink;
    public final long classLoaderData;
    public final long modifiedClass;
    public final long accessFlags;
    public final long lastBiasedLockBulkRevocationTime;
    public final long prototypeHeader;
    public final long biasedLockRevocationCount;
    public final long modifiedOops;
    public final long accumulatedModifiedOops;
    public final long traceId;

    public static long[] readRaw(long address) {
        var size = 8;
        var unsafe = unsafe();
        var arr = new long[28];
        for (int i = 0; i < 27; i++) {
            arr[i] = unsafe.getLong(address + i * size);
        }
        return arr;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Klass.class.getSimpleName() + "[", "]")
                .add("vtblPtr=" + vtblPtr)
                .add("layoutHelper=" + layoutHelper)
                .add("superCheckOffset=" + superCheckOffset)
                .add("name=" + name)
                .add("secondarySuperCache=" + secondarySuperCache)
                .add("secondarySupers=" + secondarySupers)
                .add("primarySupers=" + Arrays.toString(primarySupers))
                .add("javaMirror=" + javaMirror)
                .add("_super=" + _super)
                .add("subclass=" + subclass)
                .add("nextSibling=" + nextSibling)
                .add("nextLink=" + nextLink)
                .add("classLoaderData=" + classLoaderData)
                .add("modifiedClass=" + modifiedClass)
                .add("accessFlags=" + accessFlags)
                .add("lastBiasedLockBulkRevocationTime=" + lastBiasedLockBulkRevocationTime)
                .add("prototypeHeader=" + prototypeHeader)
                .add("biasedLockRevocationCount=" + biasedLockRevocationCount)
                .add("modifiedOops=" + modifiedOops)
                .add("accumulatedModifiedOops=" + accumulatedModifiedOops)
                .add("traceId=" + traceId)
                .toString();
    }
}
