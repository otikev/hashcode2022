package com.otikev.hashcode;

import java.util.Comparator;

/**
 * Created by kevin on 25-Feb-2022
 */

public class SkillLevelComparator implements Comparator<Contributor> {

    private String skill;

    public SkillLevelComparator(String skill) {
        this.skill = skill;
    }

    @Override
    public int compare(Contributor contributorA, Contributor contributorB) {
        return  contributorB.getSkillLevel(skill) - contributorA.getSkillLevel(skill);
    }
}