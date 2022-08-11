package com.testluzo.demo.dao;

import java.util.List;

import com.testluzo.demo.models.Usuario;

public interface UsuarioDao {
	List<Usuario> getUsuarios();

	void eliminar(Long id);

	void registrar(Usuario usuario);

	Usuario obtenerUsuariosPorCredenciales(Usuario usuario);
}
