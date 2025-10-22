package com.example.user_service.service;

import com.example.user_service.entity.ERole;
import com.example.user_service.entity.Role;
import com.example.user_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(ERole.values()).forEach(roleEnum -> {
            if (roleRepository.findByName(roleEnum).isEmpty()) {
                roleRepository.save(new Role(roleEnum));
                System.out.println("Seeded " + roleEnum.name() + " into database.");
            }
        });
    }
}
