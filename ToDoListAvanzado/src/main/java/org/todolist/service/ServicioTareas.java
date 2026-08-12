package org.todolist.service;

import org.todolist.enums.Estado;
import org.todolist.enums.Prioridad;
import org.todolist.model.Task;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ServicioTareas aplica el paradigma de Programacion Funcional para
 * filtrar, buscar y ordenar tareas de forma modular, mediante el uso
 * de funciones puras (Predicate, Comparator, Streams) que no modifican
 * la lista original ni generan efectos secundarios.
 */
public class ServicioTareas {

	private final TaskService taskService;
	private List<Task> listaTareas;

	public ServicioTareas() {
		this.taskService = new TaskService();
		this.listaTareas = taskService.obtenerTodasLasTareas();
	}

	public ServicioTareas(List<Task> listaTareas) {
		this.taskService = new TaskService();
		this.listaTareas = listaTareas;
	}

	/**
	 * Vuelve a cargar la lista de tareas desde el repositorio/servicio.
	 */
	public void refrescarTareas() {
		this.listaTareas = taskService.obtenerTodasLasTareas();
	}

	public List<Task> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Task> listaTareas) {
		this.listaTareas = listaTareas;
	}

	// -----------------------------------------------------------
	// Filtro generico (funcion de orden superior): recibe cualquier
	// Predicate<Task> y devuelve la lista filtrada. Reutilizado por
	// el resto de metodos de filtrado especificos.
	// -----------------------------------------------------------
	public List<Task> filtrarPersonalizado(Predicate<Task> filtro) {

		if (filtro == null) {
			throw new IllegalArgumentException("El filtro no puede ser null");
		}

		if (listaTareas == null) {
			return List.of();
		}

		return listaTareas.stream()
				.filter(filtro)
				.collect(Collectors.toList());
	}

	// -----------------------------------------------------------
	// Filtrar por prioridad
	// -----------------------------------------------------------
	public List<Task> filtrarPorPrioridad(Prioridad prioridad) {

		if (prioridad == null) {
			throw new IllegalArgumentException("La prioridad no puede ser null");
		}

		return filtrarPersonalizado(tarea -> tarea.getPrioridad() == prioridad);
	}

	// -----------------------------------------------------------
	// Filtrar por estado
	// -----------------------------------------------------------
	public List<Task> filtrarPorEstado(Estado estado) {

		if (estado == null) {
			throw new IllegalArgumentException("El estado no puede ser null");
		}

		return filtrarPersonalizado(tarea -> tarea.getEstado() == estado);
	}

	// -----------------------------------------------------------
	// Filtrar por rango de fechas (fecha limite)
	// -----------------------------------------------------------
	public List<Task> filtrarPorFecha(LocalDate desde, LocalDate hasta) {

		if (desde == null || hasta == null) {
			throw new IllegalArgumentException("Las fechas 'desde' y 'hasta' no pueden ser null");
		}

		if (hasta.isBefore(desde)) {
			throw new IllegalArgumentException("La fecha 'hasta' no puede ser anterior a 'desde'");
		}

		return filtrarPersonalizado(tarea ->
				tarea.getFechaLimite() != null &&
						!tarea.getFechaLimite().isBefore(desde) &&
						!tarea.getFechaLimite().isAfter(hasta)
		);
	}

	// -----------------------------------------------------------
	// Filtrar por categoria
	// -----------------------------------------------------------
	public List<Task> filtrarPorCategoria(Integer idCategoria) {

		if (idCategoria == null) {
			throw new IllegalArgumentException("El idCategoria no puede ser null");
		}

		return filtrarPersonalizado(tarea -> idCategoria.equals(tarea.getIdCategoria()));
	}

	// -----------------------------------------------------------
	// Filtrar por usuario
	// -----------------------------------------------------------
	public List<Task> filtrarPorUsuario(int idUsuario) {

		if (idUsuario <= 0) {
			throw new IllegalArgumentException("El idUsuario debe ser mayor que 0");
		}

		return filtrarPersonalizado(tarea -> tarea.getIdUsuario() == idUsuario);
	}

	// -----------------------------------------------------------
	// Filtrar tareas vencidas: fecha limite anterior a hoy y no completadas
	// -----------------------------------------------------------
	public List<Task> filtrarPorVencimiento() {

		LocalDate hoy = LocalDate.now();

		return filtrarPersonalizado(tarea ->
				tarea.getFechaLimite() != null &&
						tarea.getFechaLimite().isBefore(hoy) &&
						tarea.getEstado() != Estado.COMPLETADA
		);
	}

	// -----------------------------------------------------------
	// Buscar por texto en titulo o descripcion (case-insensitive)
	// -----------------------------------------------------------
	public List<Task> buscarPorTexto(String texto) {

		if (texto == null || texto.trim().isEmpty()) {
			throw new IllegalArgumentException("El texto de busqueda no puede estar vacio");
		}

		String textoBuscado = texto.trim().toLowerCase();

		return filtrarPersonalizado(tarea ->
				(tarea.getTitulo() != null &&
						tarea.getTitulo().toLowerCase().contains(textoBuscado)) ||
				(tarea.getDescripcion() != null &&
						tarea.getDescripcion().toLowerCase().contains(textoBuscado))
		);
	}

	// -----------------------------------------------------------
	// Ordenar por fecha de vencimiento ascendente (nulls al final)
	// -----------------------------------------------------------
	public List<Task> ordenarPorVencimiento() {

		if (listaTareas == null) {
			return List.of();
		}

		return listaTareas.stream()
				.sorted(Comparator.comparing(
						Task::getFechaLimite,
						Comparator.nullsLast(Comparator.naturalOrder())
				))
				.collect(Collectors.toList());
	}

	// -----------------------------------------------------------
	// Ordenar por prioridad (ALTA > MEDIA > BAJA)
	// -----------------------------------------------------------
	public List<Task> ordenarPorPrioridad() {

		if (listaTareas == null) {
			return List.of();
		}

		Comparator<Task> comparadorPrioridad = Comparator.comparingInt(tarea -> switch (tarea.getPrioridad()) {
			case ALTA -> 0;
			case MEDIA -> 1;
			case BAJA -> 2;
		});

		return listaTareas.stream()
				.sorted(comparadorPrioridad)
				.collect(Collectors.toList());
	}

	// -----------------------------------------------------------
	// Ordenar por fecha de creacion (mas recientes primero)
	// -----------------------------------------------------------
	public List<Task> ordenarPorFechaCreacion() {

		if (listaTareas == null) {
			return List.of();
		}

		return listaTareas.stream()
				.sorted(Comparator.comparing(
						Task::getFechaCreacion,
						Comparator.nullsLast(Comparator.reverseOrder())
				))
				.collect(Collectors.toList());
	}
}
