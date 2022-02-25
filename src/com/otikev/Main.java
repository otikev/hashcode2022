package com.otikev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        try {
            String input = Files.readString(Path.of("input_data/a_an_example.in.txt"));
            ResourceAllocator resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "a.out.txt");

            input = Files.readString(Path.of("input_data/b_better_start_small.in.txt"));
            resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "b.out.txt");

            input = Files.readString(Path.of("input_data/c_collaboration.in.txt"));
            resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "c.out.txt");

            input = Files.readString(Path.of("input_data/d_dense_schedule.in.txt"));
            resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "d.out.txt");

            input = Files.readString(Path.of("input_data/e_exceptional_skills.in.txt"));
            resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "e.out.txt");

            input = Files.readString(Path.of("input_data/f_find_great_mentors.in.txt"));
            resourceAllocator = new ResourceAllocator();
            resourceAllocator.execute(input, "f.out.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
