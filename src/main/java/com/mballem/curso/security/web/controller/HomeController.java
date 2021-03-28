package com.mballem.curso.security.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	
	@GetMapping({"/", "home"})
	public String home() {
		return "home";
	}	
	
	@GetMapping({"login"})
	public String login() {
		return "login";
	}
	
	@GetMapping({"login-error"})
	public String loginError(ModelMap model) {
		
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Credenciais invalidas");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente");
		model.addAttribute("subtexto", "acesso permitido so para usuarios atividos");
		
		return "login";
		
	}
	
	@GetMapping({"acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse response) {
		
		model.addAttribute("status", response.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área");
		
		
		return "error";
		
	}
	
	
	
}
