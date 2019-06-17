package com.arvato.cc.dao1;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 11/10/12
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public interface SequenceDao {
    Vector<Integer> generate(int capacity, String seqName);
    Integer generate(String seqName);
}
