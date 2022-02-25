package com.otikev.hashcode;

import java.util.Comparator;

/**
 * Created by kevin on 25-Feb-2022
 */

public class SkillGroupComparator implements Comparator<SkillGroup> {

    @Override
    public int compare(SkillGroup skillGroupA, SkillGroup skillGroupB) {
        return skillGroupB.contributors.size() - skillGroupA.contributors.size();
    }
}