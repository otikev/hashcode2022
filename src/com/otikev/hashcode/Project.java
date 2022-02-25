package com.otikev.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kevin on 24-Feb-2022
 */

public class Project {
    String name = "";
    Integer durationDays = 0;
    Integer completionScore = 0;
    Integer bestBeforeDay = 0;
    List<Role> roles = new ArrayList<>();
    List<Contributor> contributors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Integer getCompletionScore() {
        return completionScore;
    }

    public void setCompletionScore(Integer completionScore) {
        this.completionScore = completionScore;
    }

    public Integer getBestBeforeDay() {
        return bestBeforeDay;
    }

    public void setBestBeforeDay(Integer bestBeforeDay) {
        this.bestBeforeDay = bestBeforeDay;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRole(Role role) {
        roles.add(role);

        //sort
        Collections.sort(roles, new RoleLevelComparator());
    }

    public boolean addContributor(Contributor contributor, Role role) {
        if (!isAssignedThisContributor(contributor)) {
            contributors.add(contributor);
            int index = roles.indexOf(role);
            roles.get(index).contributor = contributor;
            return true;
        }

        System.out.println("Contributor already assigned to this project");
        return false;
    }

    public boolean allRolesFilled() {
        for (Role role : roles) {
            if (!role.filled()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAssignedThisContributor(Contributor contributor) {
        return contributors.contains(contributor);
    }

    //assuming that this skill level is just 1 below the expected
    public boolean hasMentorForSkill(String skill, int expectedLevel) {

        //check if the project has filled a role with similar skill and at a higher level
        for (Role role : roles) {

            if (role.name.equals(skill) && role.level >= expectedLevel && role.filled()) {
                return true;
            }
        }

        //find any contributor in the project with this skill and at a higher level
        for (Contributor contributor : contributors) {
            if (contributor.getSkillLevel(skill) >= expectedLevel) {
                return true;
            }
        }

        return false;
    }

    class RoleLevelComparator implements Comparator<Role> {
        @Override
        public int compare(Role roleA, Role roleB) {
            return roleB.level - roleA.level;
        }
    }
}
