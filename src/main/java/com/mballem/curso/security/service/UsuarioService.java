package com.mballem.curso.security.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private Datatables datatables;

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

	
	@Transactional(readOnly = true )
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.USUARIOS);
		Page<Usuario> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
						: repository.findByEmailOrPerfil(datatables.getSearch(), datatables.getPageable());
		
		System.out.println("Foi lista");

		return datatables.getResponse(page);
	}
	

}
