package com.mballem.curso.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	public Usuario buscarPorEmail(String email) {

		return repository.findByEmail(email);

	}

	// convertendo List de perfils em String para utilizar no create Authority
	@Transactional(readOnly = true )
	private String[] converterPerfisEmString(List<Perfil> perfis) {

		String[] perfisConvertidos = new String[perfis.size()];

		for (int i = 0; i < perfis.size(); i++) {

			perfisConvertidos[i] = perfis.get(i).getDesc();
		}

		return perfisConvertidos;
	}

	
	@Override @Transactional(readOnly = true )
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = buscarPorEmail(username);
		return new User(
				usuario.getEmail(), 
				usuario.getSenha(), 
				AuthorityUtils.createAuthorityList(converterPerfisEmString(usuario.getPerfis()))
				);
	}
	

}
