package com.otikev;

import java.util.Comparator;

/**
 * Created by kevin on 25-Feb-2022
 */

public class RoleIndexComparator implements Comparator<Role> {
    @Override
    public int compare(Role roleA, Role roleB) {
        return  roleA.index - roleB.index;
    }
}