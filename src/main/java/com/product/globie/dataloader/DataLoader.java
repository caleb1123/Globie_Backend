package com.product.globie.dataloader;

import com.product.globie.entity.Enum.ERole;
import com.product.globie.entity.Role;
import com.product.globie.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner{
    @Autowired
    RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Arrays.asList(ERole.values()).forEach(roleName -> {
                Role role = Role.builder().roleName(roleName).build();
                roleRepository.save(role);
            });
        }
    }
}
