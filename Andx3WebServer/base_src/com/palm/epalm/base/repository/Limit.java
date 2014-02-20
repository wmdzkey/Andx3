package com.palm.epalm.base.repository;

/**
 * 查询条数限制
 *
 * @author desire
 * @since 2013-08-07 17:30
 */
public class Limit {
    int offset = 0;
    int size = 10;

    public Limit(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public Limit(int size) {
        this(0, size);
    }

    public Limit() {
        this(0, 10);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
