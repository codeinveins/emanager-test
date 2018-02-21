package com.supra.sso.utiities;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.supra.sso.model.Modules;
import com.supra.sso.model.Role;
import com.supra.sso.repository.ModuleRepository;
import com.supra.sso.repository.RoleRepository;

@Component
public class CreateMasterDataInDatabase {

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	RoleRepository roleRepository;

	@PostConstruct
	public void createMasterData() {
		createRoles();
		createModules();

		System.out.println("MASTER DATA CREATED");
	}

	private void createModules() {
		List<Modules> modulesList = moduleRepository.findAll();
		if(modulesList.isEmpty()) {
			for(String moduleName : ApplicationConstants.MODULES_LIST) {
				Modules module = new Modules();
				module.setName(moduleName);
				moduleRepository.save(module);
			}
		}
	}

	private void createRoles() {
		List<Role> rolesList = roleRepository.findAll();
		if(rolesList.isEmpty()) {
			for(String roleName : ApplicationConstants.ROLES_LIST) {
				Role role = new Role();
				role.setAuthority(roleName);
				roleRepository.save(role);
			}
		}
	}

}
