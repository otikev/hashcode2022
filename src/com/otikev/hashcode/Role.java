package com.otikev.hashcode;

/**
 * Created by kevin on 25-Feb-2022
 */

public class Role {
    int index;
    String name;
    Integer level;
    Contributor contributor;

    public Role(String name, Integer level, int index) {
        this.name = name;
        this.level = level;
        this.index = index;
    }

    public boolean filled(){
        return contributor != null;
    }

    @Override
    public boolean equals(Object obj) {
        Role role = (Role) obj;
        return this.name.equals(role.name) && this.level == role.level;
    }
}
