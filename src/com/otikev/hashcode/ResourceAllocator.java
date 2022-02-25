package com.otikev.hashcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by kevin on 24-Feb-2022
 */

public class ResourceAllocator {
    Parser parser = new Parser();
    StringBuilder mainBuilder = new StringBuilder();

    public void execute(String input, String outputFile) {
        parser.parse(input);

        StringBuilder allocationsBuilder = new StringBuilder();

        int projectsCount=0;
        for (Project project : parser.projects) {
            if(project.name.equals("ShoppingProv8")){
                System.out.println("Debugging...");
            }

            for (Role role : project.roles) {
                Contributor contributor = getBestAvailableContributorForSkill(role, project);
                if (contributor != null) {
                    project.addContributor(contributor,role);
                }
            }

            if(project.allRolesFilled()){
                projectsCount++;
                allocationsBuilder.append("\n").append(project.name).append("\n");

                Collections.sort(project.roles, new RoleIndexComparator());
                for (Role role : project.roles) {
                    allocationsBuilder.append(role.contributor.name).append(" ");
                }
            }
        }

        mainBuilder.append(projectsCount);
        mainBuilder.append(allocationsBuilder);
        printOutput(outputFile);
    }

    private Contributor getBestAvailableContributorForSkill(Role role, Project project) {
        if(role.filled()){
            System.out.println("This role has been filled");
            return null;
        }

        for (Contributor contributor : parser.getContributorsForSkill(role.name)) {
            if(!project.isAssignedThisContributor(contributor)) {
                if (contributor.skills.containsKey(role.name) && contributor.skills.get(role.name) >= role.level) {//is current level or higher
                    return contributor;
                } else if (contributor.skills.containsKey(role.name) && contributor.skills.get(role.name) == role.level-1) {//is 1 level below
                    if(project.hasMentorForSkill(role.name,role.level)){
                        return contributor;
                    }
                }
            }
        }

        //Could not find anyone. Is there a mentor for this skill so that we assign a mentee?
        boolean hasMentor = project.hasMentorForSkill(role.name,role.level);
        if(!hasMentor){
            return null;
        }

        //Let's pick someone from a skill with multiple contributors
        for(SkillGroup group : parser.skillsSortedOnPopularity){
            for(int i = group.contributors.size()-1;i>0;i--){
                //get least experienced person from here (so they can grow other skills through mentoring :-))
                Contributor mentee = group.contributors.get(i);
                if(!project.isAssignedThisContributor(mentee) && mentee.getSkillLevel(group.skill) == role.level-1){
                    mentee.isMentee = true;
                    return mentee;
                }
            }
        }

        //Should not reach here
        return null;
    }

    private void printOutput(String outputFile) {
        System.out.println("======================");
        System.out.println("Printing " + outputFile);
        try {
            Files.write(Paths.get(outputFile), mainBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished " + outputFile);
    }

    class RoleIndexComparator implements Comparator<Role> {
        @Override
        public int compare(Role roleA, Role roleB) {
            return  roleA.index - roleB.index;
        }
    };
}
