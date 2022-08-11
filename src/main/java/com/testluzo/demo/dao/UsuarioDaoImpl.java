package com.testluzo.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.testluzo.demo.models.Usuario;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


@Repository
@Transactional
public class UsuarioDaoImpl implements UsuarioDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Usuario> getUsuarios() {
		String query = "FROM Usuario";
		return entityManager.createQuery(query).getResultList();
		
	}

	@Override
	public void eliminar(Long id) {
		Usuario usuario = entityManager.find(Usuario.class, id);
		entityManager.remove(usuario);
	}

	@Override
	public void registrar(Usuario usuario) {
		entityManager.merge(usuario);
	}

	@Override
	public Usuario obtenerUsuariosPorCredenciales(Usuario usuario) {
		String query = "FROM Usuario WHERE email = :email";
		List<Usuario> lista = entityManager.createQuery(query)
				.setParameter("email", usuario.getEmail())
				.getResultList();
		
		if(lista.isEmpty()) {
			return null;
		}
		
		String passwordHashes = lista.get(0).getPassword();
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		
		if(argon2.verify(passwordHashes, usuario.getPassword())) {
			return lista.get(0);
		}
		return null;
	}

}
