package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.repository.RoleRepository;
import ru.kolomych.polyq.exception.NotFoundException;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String name) throws NotFoundException {
        return roleRepository.getRoleByName(name).orElseThrow(() ->
                new NotFoundException(String.format("Role with name %s does not exist", name)));
    }
}
