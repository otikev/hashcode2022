package com.otikev;

import java.util.*;

/**
 * Created by kevin on 24-Feb-2022
 */

public class Parser {
    int contributorCount;
    int projectCount;
    List<Contributor> contributors = new ArrayList<>();
    List<Project> projects = new ArrayList<>();
    int contributorsOffset = 1;
    int projectsOffset;
    List<String> skills = new ArrayList<>();

    List<SkillGroup> skillsSortedOnPopularity = new ArrayList<>();

    public void parse(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());

        parseTeamWorkload(lines);
        getContributors(lines);
        getProjects(lines);
        sortContributorsOnSkillLevel();
    }

    public List<Contributor> getContributorsForSkill(String skill){
        for(SkillGroup group: skillsSortedOnPopularity){
            if(group.skill.equals(skill)){
                return group.contributors;
            }
        }

        //Should never reach here
        return new ArrayList<>();
    }

    private void sortContributorsOnSkillLevel() {
        List<SkillGroup> sortedSkillGroup = new ArrayList<>();

        for(String skill : skills){
            SkillGroup group = new SkillGroup();
            group.skill = skill;

            for(Contributor contributor : contributors){
                if(contributor.hasSKill(skill)){
                    group.contributors.add(contributor);
                }
            }

            //sort
            Collections.sort(group.contributors, new SkillLevelComparator(skill));
            sortedSkillGroup.add(group);
        }

        Collections.sort(sortedSkillGroup, new SkillGroupComparator());
        skillsSortedOnPopularity = sortedSkillGroup;
        System.out.println("Grouped and sorted contributors based on skill level and skill popularity");
    }

    private void parseTeamWorkload(String[] teamWorkload) {
        String[] team = teamWorkload[0].split("\\s+");
        contributorCount = Integer.parseInt(team[0]);
        projectCount = Integer.parseInt(team[1]);
    }

    private void updateSkillsList(String skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    private void getContributors(String[] input) {
        int contributorsCovered = 0;
        for (int i = contributorsOffset; i < Integer.MAX_VALUE; i++) {
            contributors.add(parseContributor(input, i + 1));
            String[] sections = input[i].split("\\s+");
            i += Integer.parseInt(sections[1]);
            contributorsCovered++;
            if (contributorsCovered >= contributorCount) {
                projectsOffset = i + 1;
                break;
            }
        }
    }

    private void getProjects(String[] input) {
        int projectsCovered = 0;
        for (int i = projectsOffset; i < Integer.MAX_VALUE; i++) {
            projects.add(parseProject(input, i + 1));
            String[] sections = input[i].split("\\s+");
            i += Integer.parseInt(sections[4]);
            ;
            projectsCovered++;
            if (projectsCovered >= projectCount) {
                break;
            }
        }
    }

    private Contributor parseContributor(String[] input, int offset) {
        String[] sections = input[offset - 1].split("\\s+");
        Contributor contributor = new Contributor();
        contributor.setName(sections[0]);

        int numberOfSkills = Integer.parseInt(sections[1]);
        for (int i = offset; i < numberOfSkills + offset; i++) {
            String[] skill = input[i].split("\\s+");
            String name = skill[0];
            updateSkillsList(name);
            int level = Integer.parseInt(skill[1]);
            contributor.setSkill(name, level);
        }
        return contributor;
    }

    private Project parseProject(String[] input, int offset) {
        String[] sections = input[offset - 1].split("\\s+");
        Project project = new Project();
        project.setName(sections[0]);
        project.setDurationDays(Integer.valueOf(sections[1]));
        project.setCompletionScore(Integer.valueOf(sections[2]));
        project.setBestBeforeDay(Integer.valueOf(sections[3]));

        int numberOfRoles = Integer.parseInt(sections[4]);

        int index = 0;
        for (int i = offset; i < numberOfRoles + offset; i++) {
            String[] skill = input[i].split("\\s+");
            String name = skill[0];
            int level = Integer.parseInt(skill[1]);

            project.setRole(new Role(name,level,index));
            index++;
        }
        return project;
    }
}