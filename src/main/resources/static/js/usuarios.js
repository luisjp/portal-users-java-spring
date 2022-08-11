// Call the dataTables jQuery plugin
$(document).ready(function() {
	cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario() {
	document.getElementById("txt-email-usuario").outerHTML = localStorage.email;
}

async function cargarUsuarios(){
	let usuarioHtml = '';
	const request = await fetch('usuarios',{
		method: 'GET',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		}
	});
	
	const usuarios = await request.json();
		
	for(let usuario of usuarios) { 
		let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
		let newUsuarioHtml = '<tr> <td>'+usuario.id+'</td> <td>'+usuario.nombre+'</td> <td>'+usuario.apellido+'</td> <td>'+usuario.email+'</td> <td>'+usuario.telefono+'</td> <td>' + botonEliminar + '</td> </tr>';
		usuarioHtml += newUsuarioHtml;
	}
	
	document.querySelector("#usuarios tbody").outerHTML = usuarioHtml;

}

function getHeaders(){
	return {
		'Accept' : 'application/json',
		'Content-type': 'application/json',
		'Authorization': localStorage.token
	}
}

async function eliminarUsuario(id) {
	
	if(!confirm('Desea eliminar este usuario?')){
		return;
	}
	
	const request = await fetch('usuario/' + id,{
		method: 'DELETE',
		headers: getHeaders()
	});
	
	location.reload();
	
}