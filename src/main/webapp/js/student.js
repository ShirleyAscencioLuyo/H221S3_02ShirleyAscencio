//import {createToast} from './toast.js';

// Constantes del CRUD
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
let soloActivos = 'A';
let personaIdSeleccionada;

//Acceder a control de boton buscar
let inputBuscarEstudiante = document.getElementById('idBuscarEstudiante');
let filtroTipoDocumento = document.getElementById('idSelectTipoDocumento');

//Accerder a control de boton guardar 
let btnGuardarEstudiante = document.getElementById('btnGuardarEstudiante');
btnGuardarEstudiante.addEventListener('click', fnProcesarEstudiante);

// Acceder al modal
//let profesorModal = document.getElementById('profesorModal');
//let modal;
//			
//window.addEventListener('load', function() {
//  modal = bootstrap.Modal.getInstance(profesorModal); // Returns a Bootstrap modal instance
//});

estudianteModal.addEventListener('hidden.bs.modal', function (event) {
  fnLimpiarForm();
});

let btnNuevo = document.getElementById('btnNuevo');
btnNuevo.addEventListener('click', fnCrearEstudiante);

//Filtro para activar por DNI O CNE
filtroTipoDocumento.addEventListener('change', fnBuscarEstudiante);

let activeCheckbox = document.getElementById("idActiveCheckbox");
activeCheckbox.addEventListener('change', function() {
	if (activeCheckbox.checked) {
		soloActivos = 'A';
	} else {
		soloActivos = 'I';
	}
	fnBuscarEstudiante();
});

let listaEstudiantes = [];

let timeoutId;
window.onload = fnBuscarEstudiante;

inputBuscarEstudiante.addEventListener('input', function() {
  clearTimeout(timeoutId);
});

inputBuscarEstudiante.addEventListener('keyup', () => {
	clearTimeout(timeoutId);

	timeoutId = setTimeout(function() {
		console.log('Han pasado 0.5 segundos');
		fnBuscarEstudiante();
	}, 500);
});

inputBuscarEstudiante.addEventListener('search', fnBuscarEstudiante);


//Funcion para crear profesor
function fnCrearEstudiante(){
	let estudianteModal = document.querySelector('#estudianteModal');
	let modal = bootstrap.Modal.getOrCreateInstance(estudianteModal);
	modal.show();
	
	document.getElementById("accion").value = ACCION_NUEVO;
	document.getElementById('estudianteModalLabel').innerHTML = 'Crear nuevo estudiante';
}



//Funcion para crear o editar un profesor.
function fnProcesarEstudiante() {
	console.log('Guardando.......');

	//	 Obteniendo los datos del formulario de profesores
	let accionSeleccionada = document.getElementById('accion').value
	let datos = 'accion=' + accionSeleccionada;
	datos += "&id=" + document.getElementById("frmId").value;
	datos += '&names=' + document.getElementById('frmNombres').value;
	datos += '&lastname=' + document.getElementById('frmApellidos').value;
	datos += '&email=' + document.getElementById('frmEmail').value;
	datos += '&cellphone=' + document.getElementById('frmCelular').value;
	datos += '&career=' + document.getElementById('frmCarrera').value;
	datos += '&semester=' + document.getElementById('frmSemestre').value;
	datos += '&document_type=' + document.getElementById('frmTipoDocumento').value;
	datos += '&document_number=' + document.getElementById('frmNumeroDocumento').value;

	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'StudentProcesar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);
			let estudianteModal = document.querySelector('#estudianteModal');
			let modal = bootstrap.Modal.getOrCreateInstance(estudianteModal);

			modal.hide();
			fnLimpiarForm();
			fnBuscarEstudiante();
			
			if (accionSeleccionada == ACCION_NUEVO) {
				createToast('success', 'Estudiante creado correctamente');
			}
			if (accionSeleccionada === ACCION_EDITAR) {
				createToast('success', 'Estudiante editado correctamente');
			}
		}
	};
	xhr.send(datos);

	console.log('Datos: ', datos);
}



//Funcion para listar profesores
function fnBuscarEstudiante() {
	console.log('Buscando.......');

	//datos
	let filter = inputBuscarEstudiante.value;
	
	//Preparando la URL
	let url = 'StudentBuscar?active='+ soloActivos + '&filter=' + filter + '&document_type=' + filtroTipoDocumento.value;
	//La llama AJAX
	let xhttp = new XMLHttpRequest();
	xhttp.open('GET', url, true);
	xhttp.onreadystatechange = function() {

		if (this.readyState == 4 && this.status == 200) {
			let respuesta = xhttp.responseText;
			listaEstudiantes = JSON.parse(respuesta);
			let detalleTabla = '';
			listaEstudiantes
				.forEach(function(item) {
					
					detalleTabla += `
						<tr>
							<td>${item.id}</td>
							<td>${item.names}</td>
							<td>${item.lastname}</td>
							<td>${item.email}</td>
							<td>${item.cellphone}</td>
							<td>${item.career}</td>
							<td>${item.semester}</td>
							<td>${item.document_type}</td>
							<td>${item.document_number}</td>
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

function obtenerBtnEditar(studentId) {
	if (soloActivos === 'A') {
		return `
			<button type='button' class='btn btn-light' onclick='fnEditarEstudiante(${studentId})'>
				<i class='bx bxs-edit'></i>
			</button>		
		`;
	} 
	
	if (soloActivos === 'I') {
		return '';
	}
}

function obtenerTipoBtnActivo(estado, studentId) {
	if (estado === 'A') {
			return `<button type='button' class='btn btn-light' onclick='abrirModalConfirmar(${studentId})'>
						<i class='bx bxs-trash' style='color: #dc3545;'></i>
					</button>`;
	} else {
		return `<button type='button' class='btn btn-light' onclick='fnActivar(${studentId})'>
					<i class='bx bxs-share' style='color: #20c997;'></i>

				</button>`;
	}
}	

function abrirModalConfirmar(studentId) {
	personaIdSeleccionada = studentId;
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
  xhr.open('POST', 'StudentActivar', true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      // La solicitud se completó correctamente
      console.log(xhr.responseText);
      createToast('success', 'Profesor reactivado correctamente');
      fnBuscarEstudiante();
    }
  };
  xhr.send(datos);
}
//Funcion para editar o actualizar profesor
function fnEditarEstudiante(id) {
	console.log('id: ', id);
	document.getElementById("accion").value = ACCION_EDITAR;
	document.getElementById('estudianteModalLabel').innerHTML = 'Editar profesor';
	
	let estudianteModal = document.querySelector('#estudianteModal');
	let modal = bootstrap.Modal.getOrCreateInstance(estudianteModal);
	modal.show();
	
	fnCargarForm(id);


	estudianteModal.addEventListener("hidden.bs.modal", fnLimpiarForm);
}


//Funcion para eliminar profesor
function fnEliminar(id) {
	console.log('id: ', id);
	
	let datos = 'id=' + id;
	
	let xhr = new XMLHttpRequest();
	xhr.open('POST', 'StudentEliminar', true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			// La solicitud se completó correctamente
			console.log(xhr.responseText);
			createToast('info', 'Estudiante desactivado correctamente');
			
			let confirmacionModal = document.querySelector('#confirmacionModal');
			let modal = bootstrap.Modal.getOrCreateInstance(confirmacionModal);
			modal.hide();
			
			fnBuscarEstudiante();
		}
	};
	xhr.send(datos);

}

//Funcion para cargar formulario de profesor
function fnCargarForm(id){
	const estudianteEncontrado = listaEstudiantes.find(estudiante => estudiante.id === id);
	document.getElementById('frmId').value = estudianteEncontrado.id;
	document.getElementById('frmNombres').value = estudianteEncontrado.names;
	document.getElementById('frmApellidos').value = estudianteEncontrado.lastname;
	document.getElementById('frmEmail').value = estudianteEncontrado.email;
	document.getElementById('frmCelular').value = estudianteEncontrado.cellphone;
	document.getElementById('frmCarrera').value = estudianteEncontrado.career;
	document.getElementById('frmSemestre').value = estudianteEncontrado.semester;
	document.getElementById('frmTipoDocumento').value = estudianteEncontrado.document_type;
	document.getElementById('frmNumeroDocumento').value = estudianteEncontrado.document_number;
	
	console.log('estudiante', estudianteEncontrado);
}

//Funcion para limpiar el formulario de profesor
function fnLimpiarForm(){
	document.getElementById('frmId').value = '';
	document.getElementById('frmNombres').value = '';
	document.getElementById('frmApellidos').value = '';
	document.getElementById('frmEmail').value = '';
	document.getElementById('frmCelular').value = '';
	document.getElementById('frmCarrera').value = '';
	document.getElementById('frmSemestre').value = '';
	document.getElementById('frmTipoDocumento').selectedIndex = 0;
	document.getElementById('frmNumeroDocumento').value = '';
	
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
	