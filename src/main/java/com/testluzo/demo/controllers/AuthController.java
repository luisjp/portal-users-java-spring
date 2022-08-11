package com.testluzo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testluzo.demo.dao.UsuarioDao;
import com.testluzo.demo.models.Usuario;
import com.testluzo.demo.utils.JWTUtil;

@RestController
public class AuthController {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public String login(@RequestBody Usuario usuario) {
		Usuario usuarioLogueado = usuarioDao.obtenerUsuariosPorCredenciales(usuario);
		if(usuarioLogueado != null) {
			String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
			
			return tokenJwt;
		}
		
		return "NOOK";
	}
	
	
}
