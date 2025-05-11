package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import java.util.HashSet;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Role;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.User;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.RoleRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findByUsername("admin").isEmpty()) {
            Role adminRole = roleRepo.findByName("ADMIN").orElse(null);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepo.save(adminRole);
            }

            User admin = new User();
            admin.setUsername("admin");

            admin.setPassword(passwordEncoder.encode("Andre2001!"));

            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepo.save(admin);

            System.out.println("Utente admin creato");
        }
    }

}
