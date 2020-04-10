package pl.jee.klos.dao2;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jee.klos.domain2.PersonRole;

/**
* Created by klos
*/
@Transactional
@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Integer>
{
	PersonRole findById(int id);
	PersonRole findByRole(String role);
}
