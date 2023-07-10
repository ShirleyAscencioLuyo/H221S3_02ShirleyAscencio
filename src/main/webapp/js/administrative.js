//import {createToast} from './toast.js';

// Constantes del CRUD
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
let soloActivos = 'A';
let personaIdSeleccionada;

//Acceder a control de boton buscar
let inputBuscarAdministrador = document.getElementById('idBuscarAdministrador');
let filtroTipoDocumento = document.getElementById('idSelectTipoDocumento');

//Accerder a control de boton guardar 
let btnGuardarAdministrador = document.getElementById('btnGuardarAdministrador');
btnGuardarAdministrador.addEventListener('click', fnProcesarAdministrador);

// Acceder al modal
//let profesorModal = document.getElementById('profesorModal');
//let modal;
//			
//window.addEventListener('load', function() {
//  modal = bootstrap.Modal.getInstance(profesorModal); // Returns a Bootstrap modal instance
//});

administradorModal.addEventListener('hidden.bs.modal', function (event) {
  fnLimpiarForm();
});

let btnNuevo = document.getElementById('btnNuevo');
btnNuevo.addEventListener('click', fnCrearAdministrador);

//Filtro para activar por DNI O CNE
filtroTipoDocumento.addEventListener('change', fnBuscarAdministrador);

let activeCheckbox = document.getElementById("idActiveCheckbox");
activeCheckbox.addEventListener('change', function() {
	if (activeCheckbox.checked) {
		soloActivos = 'A';
	} else {
		soloActivos = 'I';
	}
	fnBuscarAdministrador();
});

let listaAdministradores = [];

let timeoutId;
window.onload = fnBuscarAdministrador;

inputBuscarAdministrador.addEventListener('input', function() {
  clearTimeout(timeoutId);
});

inputBuscarAdministrador.addEventListener('keyup', () => {
	clearTimeout(timeoutId);

	timeoutId = setTimeout(function() {
		console.log('Han pasado 0.5 segundos');
		fnBuscarAdministrador();
	}, 500);
});

inputBuscarAdministrador.addEventListener('search', fnBuscarAdministrador);


//Funcion para crear profesor
function fnCrearAdministrador(){
	let administradorModal = document.querySelector('#administradorModal');
	let modal = bootstrap.Modal.getOrCreateInstance(administradorModal);
	modal.show();
	
	document.getElementById("accion").value = ACCION_NUEVO;
	document.getElementById('administradorModalLabel').innerHTML = 'Crear nuevo Administrador';
}



//Funcion para crear o editar un profesor.
function fnProcesarAdministrador() {
	console.log('Guardando.......');

	//	 Obteniendo los datos del formulario de Administrador
	let accionSeleccionada = document.getElementById('accion').value
	let datos = 'accion=' + accionSeleccionada;
	datos += "&id=" + document.getElementById("frmId").value;
	datos += '&document_type=' + document.getElementById('frmTipoDocumento').value;
	datos += '&document_number=' + document.getElementById('frmNumeroDocumento').value;
	datos += '&names=' + document.getElementById('frmNombres').value;
	datos += '&lastnames=' + document.getElementById('frmApellidos').value;
	datos += '&email=' + document.getElementById('frmEmail').value;
	datos += '&cellphone=' + document.getElementById('frmCelular').value;
	datos += '&type_charge=' + document.getElementById('frmTipoCargo').value;

	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'AdministrativeProcesar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);
			let administradorModal = document.querySelector('#administradorModal');
			let modal = bootstrap.Modal.getOrCreateInstance(administradorModal);

			modal.hide();
			fnLimpiarForm();
			fnBuscarAdministrador();
			
			if (accionSeleccionada == ACCION_NUEVO) {
				createToast('success', 'Administrador creado correctamente');
			}
			if (accionSeleccionada === ACCION_EDITAR) {
				createToast('success', 'Administrador editado correctamente');
			}
		}
	};
	xhr.send(datos);

	console.log('Datos: ', datos);
}



//Funcion para listar profesores
function fnBuscarAdministrador() {
	console.log('Buscando.......');

	//datos
	let filter = inputBuscarAdministrador.value;
	
	//Preparando la URL
	let url = 'AdministrativeBuscar?active='+ soloActivos + '&filter=' + filter + '&documentType=' + filtroTipoDocumento.value;
	//La llama AJAX
	let xhttp = new XMLHttpRequest();
	xhttp.open('GET', url, true);
	xhttp.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			let respuesta = xhttp.responseText;
			listaAdministradores = JSON.parse(respuesta);
			let detalleTabla = '';
			listaAdministradores
				.forEach(function(item) {
					
					detalleTabla += `
						<tr>
							<td>${item.id}</td>
							<td>${item.documentType}</td>
							<td>${item.documentNumber}</td>
							<td>${item.names}</td>
							<td>${item.lastnames}</td>
							<td>${item.email}</td>
							<td>${item.cellphone}</td>
							<td>${item.typeCharge}</td>
							<td>
								<div class='d-flex gap-2'>
									${obtenerBtnEditar(item.id)}
									${obtenerTipoBtnActivo(item.active, item.id)}
								</div>
							</td>
						</tr>
					`;
				});
			document.getElementById("detalleTabla").innerHTML = detalleTabla;
		}
	};
	xhttp.send();
}

function obtenerBtnEditar(AdministrativeId) {
	if (soloActivos === 'A') {
		return `
			<button type='button' class='btn btn-light' onclick='fnEditarAdministrador(${AdministrativeId})'>
				<i class='bx bxs-edit'></i>
			</button>		
		`;
	} 
	
	if (soloActivos === 'I') {
		return '';
	}
}

function obtenerTipoBtnActivo(estado, AdministrativeId) {
	if (estado === 'A') {
			return `<button type='button' class='btn btn-light' onclick='abrirModalConfirmar(${AdministrativeId})'>
						<i class='bx bxs-trash' style='color: #dc3545;'></i>
					</button>`;
	} else {
		return `<button type='button' class='btn btn-light' onclick='fnActivar(${AdministrativeId})'>
					<i class='bx bxs-share' style='color: #20c997;'></i>

				</button>`;
	}
}	

function abrirModalConfirmar(AdministrativeId) {
	personaIdSeleccionada = AdministrativeId;
	let confirmacionModal = document.querySelector('#confirmacionModal');
	let modal = bootstrap.Modal.getOrCreateInstance(confirmacionModal);
	modal.show();
}

let btnConfirmar = document.getElementById('btnConfirmar');
btnConfirmar.addEventListener('click', () => fnEliminar(personaIdSeleccionada));
	
//Funcion para reactivar profesor
function fnActivar(id) {
  console.log('id: ', id);
  
  let datos = 'id=' + id;
  
  let xhr = new XMLHttpRequest();
  xhr.open('POST', 'AdministrativeActivar', true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      // La solicitud se completó correctamente
      console.log(xhr.responseText);
      createToast('success', 'Administrador reactivado correctamente');
      fnBuscarAdministrador();
    }
  };
  xhr.send(datos);
}
//Funcion para editar o actualizar profesor
function fnEditarAdministrador(id) {
	console.log('id: ', id);
	document.getElementById("accion").value = ACCION_EDITAR;
	document.getElementById('administradorModalLabel').innerHTML = 'Editar Administrador';
	
	let administradorModal = document.querySelector('#administradorModal');
	let modal = bootstrap.Modal.getOrCreateInstance(administradorModal);
	modal.show();
	
	fnCargarForm(id);


	administradorModal.addEventListener("hidden.bs.modal", fnLimpiarForm);
}


//Funcion para eliminar profesor
function fnEliminar(id) {
	console.log('id: ', id);
	
	let datos = 'id=' + id;
	
	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'AdministrativeEliminar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);
			createToast('info', 'Administrador desactivado correctamente');
			
			let confirmacionModal = document.querySelector('#confirmacionModal');
			let modal = bootstrap.Modal.getOrCreateInstance(confirmacionModal);
			modal.hide();
			
			fnBuscarAdministrador();
		}
	};
	xhr.send(datos);

}

//Funcion para cargar formulario de profesor
function fnCargarForm(id){
	const administradorEncontrado = listaAdministradores.find(administrador => administrador.id === id);
	document.getElementById('frmId').value = administradorEncontrado.id;
	document.getElementById('frmTipoDocumento').value = administradorEncontrado.documentType;
	document.getElementById('frmNumeroDocumento').value = administradorEncontrado.documentNumber;
	document.getElementById('frmNombres').value = administradorEncontrado.names;
	document.getElementById('frmApellidos').value = administradorEncontrado.lastnames;
	document.getElementById('frmEmail').value = administradorEncontrado.email;
	document.getElementById('frmCelular').value = administradorEncontrado.cellphone;
	document.getElementById('frmTipoCargo').value = administradorEncontrado.typeCharge;
	
	console.log('administrador', administradorEncontrado);
}

//Funcion para limpiar el formulario de profesor
function fnLimpiarForm(){
	document.getElementById('frmId').value = '';
	document.getElementById('frmTipoDocumento').selectedIndex = 0;
	document.getElementById('frmNumeroDocumento').value = '';
	document.getElementById('frmNombres').value = '';
	document.getElementById('frmApellidos').value = '';
	document.getElementById('frmEmail').value = '';
	document.getElementById('frmCelular').value = '';
	document.getElementById('frmTipoCargo').selectedIndex = 0;
	
	fnLimpiarValidaciones();
	
}

function fnLimpiarValidaciones() {
    document.getElementById('frmNumeroDocumento').classList.remove('is-invalid');
    document.getElementById('frmNumeroDocumento').classList.remove('is-valid');
    
    document.getElementById('frmNombres').classList.remove('is-invalid');
    document.getElementById('frmNombres').classList.remove('is-valid');
    
    document.getElementById('frmApellidos').classList.remove('is-invalid');
    document.getElementById('frmApellidos').classList.remove('is-valid');
    
    document.getElementById('frmEmail').classList.remove('is-invalid');
    document.getElementById('frmEmail').classList.remove('is-valid');
    
    document.getElementById('frmCelular').classList.remove('is-invalid');
    document.getElementById('frmCelular').classList.remove('is-valid');
    
    document.getElementById('inputEmailError').textContent = '';
    document.getElementById('inputCelularError').textContent = '';
}
	
	