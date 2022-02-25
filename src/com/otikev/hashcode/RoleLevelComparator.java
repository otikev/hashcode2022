package com.otikev.hashcode;

import java.util.Comparator;

/**
 * Created by kevin on 25-Feb-2022
 */

public class RoleLevelComparator implements Comparator<Role> {
    @Override
    public int compare(Role roleA, Role roleB) {
        return  roleB.level - roleA.level;
    }
}