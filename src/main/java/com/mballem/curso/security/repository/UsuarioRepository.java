package com.mballem.curso.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mballem.curso.security.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	@Query("SELECT u from Usuario u WHERE u.email like :email")
	public  Usuario findByEmail(@Param("email") String email);

	@Query("SELECT u from Usuario u " 
				+ "join u.perfis p " 
					+ "WHERE u.email like :search% OR p.desc like :search%")
	public Page<Usuario> findByEmailOrPerfil(String search, Pageable pageable);

}
