<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="org.todolist.model.SubTask" %>
<%@ page import="org.todolist.model.Task" %>


<%
    // ============================================================
    // DATOS RECIBIDOS DESDE SubTaskServlet
    // ============================================================

    List<SubTask> subtareas =
            (List<SubTask>) request.getAttribute("subtareas");

    List<Task> tareas =
            (List<Task>) request.getAttribute("tareas");

    SubTask subTaskEditar =
            (SubTask) request.getAttribute("subTaskEditar");
%>


<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Subtareas</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/subtareas.css">

</head>


<body>

<!-- ============================================================
     CABECERA
     ============================================================ -->

<header class="barra-superior">

    <div class="barra-contenido">

        <div class="logo">

            <span class="logo-icono">✓</span>

            <span>ToDoList</span>

        </div>


        <div class="usuario-menu">

            <!-- Accesos rapidos -->
            <nav class="accesos-rapidos">

                <a
                    href="${pageContext.request.contextPath}/tareas"
                    class="acceso-rapido">
                    Mis tareas
                </a>

                <a href="${pageContext.request.contextPath}/tags"
                   class="acceso-rapido">
                    Tags
                </a>

                <a href="${pageContext.request.contextPath}/categorias"
                   class="acceso-rapido">
                    Categorías
                </a>

                <a href="${pageContext.request.contextPath}/subtareas"
                   class="acceso-rapido">
                    Subtareas
                </a>

                <a href="${pageContext.request.contextPath}/recordatorios"
                   class="acceso-rapido">
                    Recordatorios
                </a>

                <a href="${pageContext.request.contextPath}/timelog"
                   class="acceso-rapido">
                    Timelog
                </a>

            </nav>

            <a
                    class="boton-salir"
                    href="${pageContext.request.contextPath}/usuario?accion=logout">

                Cerrar sesión

            </a>

        </div>

    </div>

</header>

                    <br>
<h1>Subtareas</h1>



<!-- ============================================================
     FORMULARIO
     CREAR / EDITAR SUBTAREA
     ============================================================ -->

<section class="formulario-subtarea">


    <% if (subTaskEditar != null) { %>


    <!-- ========================================================
         EDITAR SUBTAREA
         ======================================================== -->

    <h2>Editar subtarea</h2>


    <form
            action="${pageContext.request.contextPath}/subtareas"
            method="post">


        <input
                type="hidden"
                name="accion"
                value="actualizar">


        <input
                type="hidden"
                name="id"
                value="<%= subTaskEditar.getIdSubtarea() %>">



        <!-- ==================================================
             DESCRIPCIÓN
             ================================================== -->

        <div class="campo">

            <label for="descripcion">
                Descripción
            </label>

            <input
                    type="text"
                    id="descripcion"
                    name="descripcion"
                    maxlength="255"
                    value="<%= subTaskEditar.getDescripcion() %>"
                    required>

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

                <% if (tareas != null) { %>

                <% for (Task tarea : tareas) { %>

                <option
                        value="<%= tarea.getId() %>"
                        <%= tarea.getId()
                                == subTaskEditar.getIdTarea()
                                ? "selected"
                                : "" %>>

                    <%= tarea.getTitulo() %>

                </option>

                <% } %>

                <% } %>

            </select>

        </div>



        <!-- ==================================================
             COMPLETADA
             ================================================== -->

        <div class="campo">

            <label>

                <input
                        type="checkbox"
                        name="completada"
                        value="true"
                    <%= subTaskEditar.isCompletada()
                                ? "checked"
                                : "" %>>

                Completada

            </label>

        </div>



        <!-- ==================================================
             BOTONES
             ================================================== -->

        <button type="submit">
            Guardar cambios
        </button>


        <a
                href="${pageContext.request.contextPath}/subtareas">

            Cancelar

        </a>


    </form>


    <% } else { %>


    <!-- ========================================================
         CREAR SUBTAREA
         ======================================================== -->

    <h2>Agregar subtarea</h2>


    <form
            action="${pageContext.request.contextPath}/subtareas"
            method="post">


        <input
                type="hidden"
                name="accion"
                value="crear">



        <!-- ==================================================
             DESCRIPCIÓN
             ================================================== -->

        <div class="campo">

            <label for="descripcion">
                Descripción
            </label>

            <input
                    type="text"
                    id="descripcion"
                    name="descripcion"
                    maxlength="255"
                    required>

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
                    Seleccione una tarea
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

            Agregar subtarea

        </button>


    </form>


    <% } %>


</section>



<!-- ============================================================
     LISTA DE SUBTAREAS
     ============================================================ -->

<section class="lista-subtareas">


    <h2>Lista de subtareas</h2>


    <% if (subtareas != null && !subtareas.isEmpty()) { %>


    <% for (SubTask subTask : subtareas) { %>


    <article class="subtarea">


        <!-- ==========================================
             DESCRIPCIÓN
             ========================================== -->

        <h3>

            <%= subTask.getDescripcion() %>

        </h3>



        <!-- ==========================================
             TAREA
             ========================================== -->

        <p>

            <strong>
                Tarea:
            </strong>


            <%
                String nombreTarea =
                        "Tarea no encontrada";

                if (tareas != null) {

                    for (Task tarea : tareas) {

                        if (tarea.getId()
                                == subTask.getIdTarea()) {

                            nombreTarea =
                                    tarea.getTitulo();

                            break;
                        }
                    }
                }
            %>


            <%= nombreTarea %>

        </p>



        <!-- ==========================================
             ESTADO
             ========================================== -->

        <p>

            <strong>
                Estado:
            </strong>

            <%= subTask.isCompletada()
                    ? "Completada"
                    : "Pendiente" %>

        </p>



        <!-- ==========================================
             EDITAR
             ========================================== -->

        <form
                action="${pageContext.request.contextPath}/subtareas"
                method="get">

            <input
                    type="hidden"
                    name="accion"
                    value="editar">

            <input
                    type="hidden"
                    name="id"
                    value="<%= subTask.getIdSubtarea() %>">

            <button type="submit">

                Editar

            </button>

        </form>



        <!-- ==========================================
             ELIMINAR
             ========================================== -->

        <form
                action="${pageContext.request.contextPath}/subtareas"
                method="post"
                onsubmit="return confirm('¿Está seguro de que desea eliminar esta subtarea?');">


            <input
                    type="hidden"
                    name="accion"
                    value="eliminar">


            <input
                    type="hidden"
                    name="id"
                    value="<%= subTask.getIdSubtarea() %>">


            <button type="submit">

                Eliminar

            </button>


        </form>


    </article>


    <% } %>


    <% } else { %>


    <p>
        No hay subtareas registradas.
    </p>


    <% } %>


</section>



<!-- ============================================================
     VOLVER A TAREAS
     ============================================================ -->

<p>

    <a href="${pageContext.request.contextPath}/tareas">

        ← Volver a tareas

    </a>

</p>


</body>

</html>