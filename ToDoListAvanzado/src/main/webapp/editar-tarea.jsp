<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.todolist.model.Task" %>

<%
    Task tarea =
            (Task) request.getAttribute("tareaEditar");
%>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Editar tarea</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/tareas.css">

</head>

<body>

<h1>Editar tarea</h1>

<section class="formulario-tarea">

    <form action="${pageContext.request.contextPath}/tareas"
          method="post">

        <input type="hidden"
               name="accion"
               value="actualizar">

        <input type="hidden"
               name="id"
               value="<%= tarea.getId() %>">


        <div class="campo">

            <label for="titulo">
                Título
            </label>

            <input
                    type="text"
                    id="titulo"
                    name="titulo"
                    value="<%= tarea.getTitulo() %>"
                    maxlength="100"
                    required>

        </div>


        <div class="campo">

            <label for="descripcion">
                Descripción
            </label>

            <textarea
                    id="descripcion"
                    name="descripcion"
                    rows="4"><%= tarea.getDescripcion() != null
                    ? tarea.getDescripcion()
                    : "" %></textarea>

        </div>


        <div class="campo">

            <label for="fechaLimite">
                Fecha límite
            </label>

            <input
                    type="date"
                    id="fechaLimite"
                    name="fechaLimite"
                    value="<%= tarea.getFechaLimite() != null
                            ? tarea.getFechaLimite()
                            : "" %>">

        </div>


        <div class="campo">

            <label for="prioridad">
                Prioridad
            </label>

            <select
                    id="prioridad"
                    name="prioridad"
                    required>

                <option value="ALTA"
                        <%= tarea.getPrioridad().name().equals("ALTA")
                                ? "selected"
                                : "" %>>
                    Alta
                </option>

                <option value="MEDIA"
                        <%= tarea.getPrioridad().name().equals("MEDIA")
                                ? "selected"
                                : "" %>>
                    Media
                </option>

                <option value="BAJA"
                        <%= tarea.getPrioridad().name().equals("BAJA")
                                ? "selected"
                                : "" %>>
                    Baja
                </option>

            </select>

        </div>


        <div class="campo">

            <label for="estado">
                Estado
            </label>

            <select
                    id="estado"
                    name="estado"
                    required>

                <option value="PENDIENTE"
                        <%= tarea.getEstado().name().equals("PENDIENTE")
                                ? "selected"
                                : "" %>>
                    Pendiente
                </option>

                <option value="EN_PROGRESO"
                        <%= tarea.getEstado().name().equals("EN_PROGRESO")
                                ? "selected"
                                : "" %>>
                    En progreso
                </option>

                <option value="COMPLETADA"
                        <%= tarea.getEstado().name().equals("COMPLETADA")
                                ? "selected"
                                : "" %>>
                    Completada
                </option>

                <option value="VENCIDA"
                        <%= tarea.getEstado().name().equals("VENCIDA")
                                ? "selected"
                                : "" %>>
                    Vencida
                </option>

            </select>

        </div>


        <div class="campo">

            <label for="progreso">
                Progreso (%)
            </label>

            <input
                    type="number"
                    id="progreso"
                    name="progreso"
                    min="0"
                    max="100"
                    value="<%= tarea.getProgreso() %>"
                    required>

        </div>


        <button type="submit">
            Guardar cambios
        </button>

    </form>


    <br>

    <a href="${pageContext.request.contextPath}/tareas">
        Volver a tareas
    </a>

</section>

</body>

</html>