package study.my_board.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import study.my_board.domain.Role;
import study.my_board.repository.RoleRepository;

@Service
public class RoleSetupService {
    private final RoleRepository roleRepository;

    public RoleSetupService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.findRoleByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (roleRepository.findRoleByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
    }
}
