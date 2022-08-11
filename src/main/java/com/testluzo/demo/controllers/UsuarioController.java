package com.testluzo.demo.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testluzo.demo.dao.UsuarioDao;
import com.testluzo.demo.models.Usuario;
import com.testluzo.demo.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioDao usuarioDao;

	private JWTUtil jwtUtil;
	
	@RequestMapping(value="usuario/{id}", method = RequestMethod.GET)
	public Usuario getUsuario(@PathVariable Long id) {
		Usuario user = new Usuario();
		user.setId(id);
		user.setNombre("Luis");
		user.setApellido("Arambulo");
		user.setEmail("luisar@hotmail.com");
		user.setPassword("passs123");
		user.setTelefono("123123");
		return user;
	}
	
	private boolean validarToken(String token) {
		String usuarioId = jwtUtil.getKey(token);
		return usuarioId != null;
	}
	
	@RequestMapping(value="usuarios")
	public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
		
		if(validarToken(token)) {
			return null;
		}
		return usuarioDao.getUsuarios();
	}
	
	@RequestMapping(value="usuario/{id}", method = RequestMethod.DELETE)
	public void eliminar(
			@RequestHeader(value="Authorization") String token,
			@PathVariable Long id) {
		
		if(validarToken(token)) {
			return;
		}
		
		usuarioDao.eliminar(id);
	}

	@RequestMapping(value="usuario", method = RequestMethod.POST)
	public void registrarUsuario(@RequestBody Usuario usuario) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
		usuario.setPassword(hash);
		usuarioDao.registrar(usuario);
	}
	
}
