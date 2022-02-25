package com.otikev.hashcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 24-Feb-2022
 */

public class Contributor {

    String name = "";
    Map<String, Integer> skills = new HashMap<>();
    boolean isMentee = false;

    public void setSkill(String skill, Integer level) {
        skills.put(skill, level);
    }

    public Integer getSkillLevel(String skill) {
        if (skills.containsKey(skill)) {
            return skills.get(skill);
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasSKill(String skill){
        return skills.containsKey(skill);
    }
}