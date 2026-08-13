
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>

<%@ page import="org.todolist.model.Reminder" %>
<%@ page import="org.todolist.model.Task" %>

<%
    // ============================================================
    // DATOS RECIBIDOS DESDE ReminderServlet
    // ============================================================

    List<Reminder> recordatorios =
            (List<Reminder>) request.getAttribute("recordatorios");

    List<Task> tareas =
            (List<Task>) request.getAttribute("tareas");

    Reminder reminderEditar =
            (Reminder) request.getAttribute("reminderEditar");
%>


<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Recordatorios</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/tareas.css">

</head>


<body>


<h1>Recordatorios</h1>


<!-- ============================================================
     FORMULARIO
     ============================================================ -->

<section class="formulario-tarea">


    <% if (reminderEditar != null) { %>


    <!-- ========================================================
         EDITAR RECORDATORIO
         ======================================================== -->

    <h2>Editar recordatorio</h2>


    <form
            action="${pageContext.request.contextPath}/recordatorios"
            method="post">


        <input
                type="hidden"
                name="accion"
                value="actualizar">


        <input
                type="hidden"
                name="id"
                value="<%= reminderEditar.getIdRecordatorio() %>">


        <!-- ==================================================
             FECHA Y HORA
             ================================================== -->

        <div class="campo">

            <label for="fechaHora">
                Fecha y hora
            </label>

            <input
                    type="datetime-local"
                    id="fechaHora"
                    name="fechaHora"
                    value="<%= reminderEditar.getFechaHora() != null
                            ? reminderEditar.getFechaHora()
                            : "" %>"
                    required>

        </div>


        <!-- ==================================================
             MENSAJE
             ================================================== -->

        <div class="campo">

            <label for="mensaje">
                Mensaje
            </label>

            <textarea
                    id="mensaje"
                    name="mensaje"
                    rows="4"><%= reminderEditar.getMensaje() != null
                    ? reminderEditar.getMensaje()
                    : "" %></textarea>

        </div>


        <!-- ==================================================
             TAREA
             ================================================== -->

        <div class="campo">

            <label for="idTarea">
                Tarea
            </label>

            <select
                    id="idTarea"
                    name="idTarea"
                    required>

                <option value="">
                    Seleccionar tarea
                </option>


                <% if (tareas != null) { %>

                <% for (Task tarea : tareas) { %>

                <option
                        value="<%= tarea.getId() %>"
                        <%= reminderEditar.getIdTarea()
                                == tarea.getId()
                                ? "selected"
                                : "" %>>

                    <%= tarea.getTitulo() %>

                </option>

                <% } %>

                <% } %>

            </select>

        </div>


        <!-- ==================================================
             ESTADO
             ================================================== -->

        <div class="campo">

            <label for="activo">
                Estado
            </label>

            <select
                    id="activo"
                    name="activo">

                <option
                        value="true"
                        <%= reminderEditar.isActivo()
                                ? "selected"
                                : "" %>>

                    Activo

                </option>


                <option
                        value="false"
                        <%= !reminderEditar.isActivo()
                                ? "selected"
                                : "" %>>

                    Inactivo

                </option>

            </select>

        </div>


        <!-- ==================================================
             BOTONES
             ================================================== -->

        <button type="submit">

            Guardar cambios

        </button>


        <a
                href="${pageContext.request.contextPath}/recordatorios">

            Cancelar

        </a>


    </form>


    <% } else { %>


    <!-- ========================================================
         CREAR RECORDATORIO
         ======================================================== -->

    <h2>Agregar recordatorio</h2>


    <form
            action="${pageContext.request.contextPath}/recordatorios"
            method="post">


        <input
                type="hidden"
                name="accion"
                value="crear">


        <!-- ==================================================
             FECHA Y HORA
             ================================================== -->

        <div class="campo">

            <label for="fechaHora">
                Fecha y hora
            </label>

            <input
                    type="datetime-local"
                    id="fechaHora"
                    name="fechaHora"
                    required>

        </div>


        <!-- ==================================================
             MENSAJE
             ================================================== -->

        <div class="campo">

            <label for="mensaje">
                Mensaje
            </label>

            <textarea
                    id="mensaje"
                    name="mensaje"
                    rows="4"
                    placeholder="Mensaje del recordatorio"></textarea>

        </div>


        <!-- ==================================================
             TAREA
             ================================================== -->

        <div class="campo">

            <label for="idTarea">
                Tarea
            </label>

            <select
                    id="idTarea"
                    name="idTarea"
                    required>

                <option value="">
                    Seleccionar tarea
                </option>


                <% if (tareas != null) { %>

                <% for (Task tarea : tareas) { %>

                <option
                        value="<%= tarea.getId() %>">

                    <%= tarea.getTitulo() %>

                </option>

                <% } %>

                <% } %>

            </select>

        </div>


        <!-- ==================================================
             BOTÓN
             ================================================== -->

        <button type="submit">

            Agregar recordatorio

        </button>


    </form>


    <% } %>


</section>


<!-- ============================================================
     LISTA DE RECORDATORIOS
     ============================================================ -->

<section class="lista-tareas">


    <h2>Recordatorios registrados</h2>


    <% if (recordatorios != null
            && !recordatorios.isEmpty()) { %>


    <% for (Reminder reminder : recordatorios) { %>


    <article class="tarea">


        <!-- =================================================
             TAREA
             ================================================= -->

        <%
            String nombreTarea =
                    "Tarea no encontrada";

            if (tareas != null) {

                for (Task tarea : tareas) {

                    if (tarea.getId()
                            == reminder.getIdTarea()) {

                        nombreTarea =
                                tarea.getTitulo();

                        break;
                    }
                }
            }
        %>


        <h3>

            <%= nombreTarea %>

        </h3>


        <!-- =================================================
             FECHA Y HORA
             ================================================= -->

        <p>

            <strong>
                Fecha y hora:
            </strong>

            <%= reminder.getFechaHora() != null
                    ? reminder.getFechaHora()
                    : "Sin fecha y hora" %>

        </p>


        <!-- =================================================
             MENSAJE
             ================================================= -->

        <p>

            <strong>
                Mensaje:
            </strong>

            <%= reminder.getMensaje() != null
                    && !reminder.getMensaje().isBlank()
                    ? reminder.getMensaje()
                    : "Sin mensaje" %>

        </p>


        <!-- =================================================
             ESTADO
             ================================================= -->

        <p>

            <strong>
                Estado:
            </strong>


            <% if (reminder.isActivo()) { %>

            Activo

            <% } else { %>

            Inactivo

            <% } %>

        </p>


        <!-- =================================================
             EDITAR
             ================================================= -->

        <form
                action="${pageContext.request.contextPath}/recordatorios"
                method="get">

            <input
                    type="hidden"
                    name="accion"
                    value="editar">

            <input
                    type="hidden"
                    name="id"
                    value="<%= reminder.getIdRecordatorio() %>">

            <button type="submit">

                Editar

            </button>

        </form>


        <!-- =================================================
             ACTIVAR / DESACTIVAR
             ================================================= -->

        <form
                action="${pageContext.request.contextPath}/recordatorios"
                method="post">


            <input
                    type="hidden"
                    name="accion"
                    value="toggle">


            <input
                    type="hidden"
                    name="id"
                    value="<%= reminder.getIdRecordatorio() %>">


            <button type="submit">


                <% if (reminder.isActivo()) { %>

                Desactivar

                <% } else { %>

                Activar

                <% } %>


            </button>


        </form>


        <!-- =================================================
             ELIMINAR
             ================================================= -->

        <form
                action="${pageContext.request.contextPath}/recordatorios"
                method="post"
                onsubmit="return confirm('¿Está seguro de que desea eliminar este recordatorio?');">


            <input
                    type="hidden"
                    name="accion"
                    value="eliminar">


            <input
                    type="hidden"
                    name="id"
                    value="<%= reminder.getIdRecordatorio() %>">


            <button type="submit">

                Eliminar

            </button>


        </form>


    </article>


    <% } %>


    <% } else { %>


    <p>

        No hay recordatorios registrados.

    </p>


    <% } %>


</section>


</body>

</html>

