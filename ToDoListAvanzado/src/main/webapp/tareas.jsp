
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page import="org.todolist.model.Task" %>
<%@ page import="org.todolist.model.Category" %>
<%@ page import="org.todolist.model.Tag" %>
<%@ page import="org.todolist.model.SubTask" %>
<%@ page import="org.todolist.model.Reminder" %>


<%
    List<Task> tareas =
            (List<Task>) request.getAttribute("tareas");

    List<Category> categorias =
            (List<Category>) request.getAttribute("categorias");

    List<Tag> etiquetas =
            (List<Tag>) request.getAttribute("etiquetas");

    Task tareaEditar =
            (Task) request.getAttribute("tareaEditar");

    List<Integer> etiquetasTarea =
            (List<Integer>) request.getAttribute("etiquetasTarea");

    Map<Integer, List<SubTask>> subtareasPorTarea =
            (Map<Integer, List<SubTask>>)
                    request.getAttribute("subtareasPorTarea");

    Map<Integer, List<Reminder>> recordatoriosPorTarea =
            (Map<Integer, List<Reminder>>)
                    request.getAttribute("recordatoriosPorTarea");
%>


<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Mis tareas - ToDoList</title>

    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/tareas.css">

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

            </nav>

            <a
                    class="boton-salir"
                    href="${pageContext.request.contextPath}/usuario?accion=logout">

                Cerrar sesión

            </a>

        </div>

    </div>

</header>


<!-- ============================================================
     CONTENIDO PRINCIPAL
     ============================================================ -->

<main class="contenedor-principal">


    <!-- ========================================================
         ENCABEZADO
         ======================================================== -->

    <section class="encabezado-pagina">

        <div>

            <h1>
                Mis tareas
            </h1>

            <p>
                Organiza tus pendientes y mantén el control de tu día.
            </p>

        </div>

        <div class="contador-tareas">

            <span class="contador-numero">
                <%= tareas != null ? tareas.size() : 0 %>
            </span>

            <span class="contador-texto">
                tareas
            </span>

        </div>

    </section>


    <!-- ========================================================
         FORMULARIO
         ======================================================== -->

    <section class="formulario-tarea">


        <% if (tareaEditar != null) { %>


        <!-- ====================================================
             EDITAR
             ==================================================== -->

        <div class="titulo-formulario">

            <div class="icono-formulario">
                ✎
            </div>

            <div>

                <h2>
                    Editar tarea
                </h2>

                <p>
                    Modifica la información de tu tarea.
                </p>

            </div>

        </div>


        <form
                action="${pageContext.request.contextPath}/tareas"
                method="post"
                class="form-tarea">


            <input
                    type="hidden"
                    name="accion"
                    value="actualizar">


            <input
                    type="hidden"
                    name="id"
                    value="<%= tareaEditar.getId() %>">


            <div class="grid-formulario">


                <!-- TÍTULO -->

                <div class="campo campo-completo">

                    <label for="titulo">
                        Título
                    </label>

                    <input
                            type="text"
                            id="titulo"
                            name="titulo"
                            maxlength="100"
                            value="<%= tareaEditar.getTitulo() %>"
                            required>

                </div>


                <!-- DESCRIPCIÓN -->

                <div class="campo campo-completo">

                    <label for="descripcion">
                        Descripción
                    </label>

                    <textarea
                            id="descripcion"
                            name="descripcion"
                            rows="4"><%= tareaEditar.getDescripcion() != null
                            ? tareaEditar.getDescripcion()
                            : "" %></textarea>

                </div>


                <!-- FECHA -->

                <div class="campo">

                    <label for="fechaLimite">
                        Fecha límite
                    </label>

                    <input
                            type="date"
                            id="fechaLimite"
                            name="fechaLimite"
                            value="<%= tareaEditar.getFechaLimite() != null
                                    ? tareaEditar.getFechaLimite()
                                    : "" %>">

                </div>


                <!-- PRIORIDAD -->

                <div class="campo">

                    <label for="prioridad">
                        Prioridad
                    </label>

                    <select
                            id="prioridad"
                            name="prioridad"
                            required>

                        <option
                                value="ALTA"
                                <%= tareaEditar.getPrioridad() != null
                                        && tareaEditar.getPrioridad().name().equals("ALTA")
                                        ? "selected"
                                        : "" %>>
                            Alta
                        </option>

                        <option
                                value="MEDIA"
                                <%= tareaEditar.getPrioridad() != null
                                        && tareaEditar.getPrioridad().name().equals("MEDIA")
                                        ? "selected"
                                        : "" %>>
                            Media
                        </option>

                        <option
                                value="BAJA"
                                <%= tareaEditar.getPrioridad() != null
                                        && tareaEditar.getPrioridad().name().equals("BAJA")
                                        ? "selected"
                                        : "" %>>
                            Baja
                        </option>

                    </select>

                </div>


                <!-- ESTADO -->

                <div class="campo">

                    <label for="estado">
                        Estado
                    </label>

                    <select
                            id="estado"
                            name="estado"
                            required>

                        <option
                                value="PENDIENTE"
                                <%= tareaEditar.getEstado() != null
                                        && tareaEditar.getEstado().name().equals("PENDIENTE")
                                        ? "selected"
                                        : "" %>>
                            Pendiente
                        </option>

                        <option
                                value="EN_PROGRESO"
                                <%= tareaEditar.getEstado() != null
                                        && tareaEditar.getEstado().name().equals("EN_PROGRESO")
                                        ? "selected"
                                        : "" %>>
                            En progreso
                        </option>

                        <option
                                value="COMPLETADA"
                                <%= tareaEditar.getEstado() != null
                                        && tareaEditar.getEstado().name().equals("COMPLETADA")
                                        ? "selected"
                                        : "" %>>
                            Completada
                        </option>

                        <option
                                value="VENCIDA"
                                <%= tareaEditar.getEstado() != null
                                        && tareaEditar.getEstado().name().equals("VENCIDA")
                                        ? "selected"
                                        : "" %>>
                            Vencida
                        </option>

                    </select>

                </div>


                <!-- PROGRESO -->

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
                            value="<%= tareaEditar.getProgreso() %>"
                            required>

                </div>


                <!-- CATEGORÍA -->

                <div class="campo">

                    <label for="idCategoria">
                        Categoría
                    </label>

                    <select
                            id="idCategoria"
                            name="idCategoria">

                        <option value="">
                            Sin categoría
                        </option>

                        <% if (categorias != null) { %>

                        <% for (Category categoria : categorias) { %>

                        <option
                                value="<%= categoria.getId() %>"
                                <%= tareaEditar.getIdCategoria() != null
                                        && tareaEditar.getIdCategoria()
                                        == categoria.getId()
                                        ? "selected"
                                        : "" %>>

                            <%= categoria.getNombre() %>

                        </option>

                        <% } %>

                        <% } %>

                    </select>

                </div>


                <!-- ETIQUETA -->

                <div class="campo">

                    <label for="idEtiqueta">
                        Etiqueta
                    </label>

                    <select
                            id="idEtiqueta"
                            name="idEtiqueta">

                        <option value="">
                            Sin etiqueta
                        </option>

                        <% if (etiquetas != null) { %>

                        <% for (Tag etiqueta : etiquetas) { %>

                        <option
                                value="<%= etiqueta.getIdEtiqueta() %>"
                                <%= etiquetasTarea != null
                                        && etiquetasTarea.contains(
                                        etiqueta.getIdEtiqueta()
                                )
                                        ? "selected"
                                        : "" %>>

                            <%= etiqueta.getNombre() %>

                        </option>

                        <% } %>

                        <% } %>

                    </select>

                </div>

            </div>


            <div class="acciones-formulario">

                <button
                        type="submit"
                        class="boton boton-principal">

                    Guardar cambios

                </button>


                <a
                        href="${pageContext.request.contextPath}/tareas"
                        class="boton boton-secundario">

                    Cancelar

                </a>

            </div>


        </form>


        <% } else { %>


        <!-- ====================================================
             CREAR
             ==================================================== -->

        <div class="titulo-formulario">

            <div class="icono-formulario">
                +
            </div>

            <div>

                <h2>
                    Agregar tarea
                </h2>

                <p>
                    Crea una nueva tarea para mantener todo organizado.
                </p>

            </div>

        </div>


        <form
                action="${pageContext.request.contextPath}/tareas"
                method="post"
                class="form-tarea">


            <input
                    type="hidden"
                    name="accion"
                    value="crear">


            <div class="grid-formulario">


                <!-- TÍTULO -->

                <div class="campo campo-completo">

                    <label for="titulo">
                        Título
                    </label>

                    <input
                            type="text"
                            id="titulo"
                            name="titulo"
                            maxlength="100"
                            placeholder="Ej. Terminar proyecto de programación"
                            required>

                </div>


                <!-- DESCRIPCIÓN -->

                <div class="campo campo-completo">

                    <label for="descripcion">
                        Descripción
                    </label>

                    <textarea
                            id="descripcion"
                            name="descripcion"
                            rows="4"
                            placeholder="Describe brevemente la tarea..."></textarea>

                </div>


                <!-- FECHA -->

                <div class="campo">

                    <label for="fechaLimite">
                        Fecha límite
                    </label>

                    <input
                            type="date"
                            id="fechaLimite"
                            name="fechaLimite">

                </div>


                <!-- PRIORIDAD -->

                <div class="campo">

                    <label for="prioridad">
                        Prioridad
                    </label>

                    <select
                            id="prioridad"
                            name="prioridad"
                            required>

                        <option value="ALTA">
                            Alta
                        </option>

                        <option
                                value="MEDIA"
                                selected>
                            Media
                        </option>

                        <option value="BAJA">
                            Baja
                        </option>

                    </select>

                </div>


                <!-- CATEGORÍA -->

                <div class="campo">

                    <label for="idCategoria">
                        Categoría
                    </label>

                    <select
                            id="idCategoria"
                            name="idCategoria">

                        <option value="">
                            Sin categoría
                        </option>

                        <% if (categorias != null) { %>

                        <% for (Category categoria : categorias) { %>

                        <option
                                value="<%= categoria.getId() %>">

                            <%= categoria.getNombre() %>

                        </option>

                        <% } %>

                        <% } %>

                    </select>

                </div>


                <!-- ETIQUETA -->

                <div class="campo">

                    <label for="idEtiqueta">
                        Etiqueta
                    </label>

                    <select
                            id="idEtiqueta"
                            name="idEtiqueta">

                        <option value="">
                            Sin etiqueta
                        </option>

                        <% if (etiquetas != null) { %>

                        <% for (Tag etiqueta : etiquetas) { %>

                        <option
                                value="<%= etiqueta.getIdEtiqueta() %>">

                            <%= etiqueta.getNombre() %>

                        </option>

                        <% } %>

                        <% } %>

                    </select>

                </div>

            </div>


            <div class="acciones-formulario">

                <button
                        type="submit"
                        class="boton boton-principal">

                    + Agregar tarea

                </button>

            </div>


        </form>


        <% } %>

    </section>


    <!-- ========================================================
         LISTA DE TAREAS
         ======================================================== -->

    <section class="lista-tareas">

        <div class="titulo-lista">

            <div>

                <h2>
                    Tus tareas
                </h2>

                <p>
                    Revisa el estado de tus actividades.
                </p>

            </div>

        </div>


        <% if (tareas != null && !tareas.isEmpty()) { %>


        <div class="grid-tareas">


            <% for (Task tarea : tareas) { %>


            <article class="tarea">


                <!-- ==============================================
                     CABECERA TARJETA
                     ============================================== -->

                <div class="tarea-cabecera">

                    <h3>
                        <%= tarea.getTitulo() %>
                    </h3>


                    <div class="acciones-tarea">

                        <form
                                action="${pageContext.request.contextPath}/tareas"
                                method="get">

                            <input
                                    type="hidden"
                                    name="accion"
                                    value="editar">

                            <input
                                    type="hidden"
                                    name="id"
                                    value="<%= tarea.getId() %>">

                            <button
                                    type="submit"
                                    class="boton-icono editar"
                                    title="Editar tarea">

                                ✎

                            </button>

                        </form>


                        <form
                                action="${pageContext.request.contextPath}/tareas"
                                method="post"
                                onsubmit="return confirm('¿Está seguro de que desea eliminar esta tarea?');">

                            <input
                                    type="hidden"
                                    name="accion"
                                    value="eliminar">

                            <input
                                    type="hidden"
                                    name="id"
                                    value="<%= tarea.getId() %>">

                            <button
                                    type="submit"
                                    class="boton-icono eliminar"
                                    title="Eliminar tarea">

                                🗑

                            </button>

                        </form>

                    </div>

                </div>


                <!-- ==============================================
                     DESCRIPCIÓN
                     ============================================== -->

                <p class="descripcion-tarea">

                    <%= tarea.getDescripcion() != null
                            && !tarea.getDescripcion().isBlank()
                            ? tarea.getDescripcion()
                            : "Sin descripción" %>

                </p>


                <!-- ==============================================
                     BADGES
                     ============================================== -->

                <div class="badges">


                    <span class="badge prioridad-<%= tarea.getPrioridad() != null
                            ? tarea.getPrioridad().name().toLowerCase()
                            : "sin-prioridad" %>">

                        <%= tarea.getPrioridad() != null
                                ? tarea.getPrioridad().name()
                                : "Sin prioridad" %>

                    </span>


                    <span class="badge estado-<%= tarea.getEstado() != null
                            ? tarea.getEstado().name().toLowerCase()
                            : "sin-estado" %>">

                        <%= tarea.getEstado() != null
                                ? tarea.getEstado().name().replace("_", " ")
                                : "Sin estado" %>

                    </span>

                </div>


                <!-- ==============================================
                     INFORMACIÓN
                     ============================================== -->

                <div class="informacion-tarea">


                    <div class="dato-tarea">

                        <span class="dato-label">
                            Fecha límite
                        </span>

                        <span class="dato-valor">

                            <%= tarea.getFechaLimite() != null
                                    ? tarea.getFechaLimite()
                                    : "Sin fecha" %>

                        </span>

                    </div>


                    <div class="dato-tarea">

                        <span class="dato-label">
                            Categoría
                        </span>

                        <span class="dato-valor">

                            <%
                                String nombreCategoria =
                                        "Sin categoría";

                                if (tarea.getIdCategoria() != null
                                        && categorias != null) {

                                    for (Category categoria :
                                            categorias) {

                                        if (categoria.getId()
                                                == tarea.getIdCategoria()) {

                                            nombreCategoria =
                                                    categoria.getNombre();

                                            break;
                                        }
                                    }
                                }
                            %>

                            <%= nombreCategoria %>

                        </span>

                    </div>


                    <div class="dato-tarea">

                        <span class="dato-label">
                            Etiqueta
                        </span>

                        <span class="dato-valor">

                            <%
                                List<Integer> idsEtiquetas =
                                        (List<Integer>)
                                                request.getAttribute(
                                                        "etiquetasTarea_"
                                                                + tarea.getId()
                                                );

                                boolean tieneEtiqueta =
                                        false;
                            %>


                            <% if (idsEtiquetas != null
                                    && etiquetas != null) { %>

                                <% for (Integer idEtiqueta :
                                        idsEtiquetas) { %>

                                    <% for (Tag etiqueta :
                                            etiquetas) { %>

                                        <% if (etiqueta.getIdEtiqueta()
                                                == idEtiqueta) { %>

                                            <%= etiqueta.getNombre() %>

                                            <%
                                                tieneEtiqueta = true;
                                            %>

                                        <% } %>

                                    <% } %>

                                <% } %>

                            <% } %>


                            <% if (!tieneEtiqueta) { %>

                                Sin etiqueta

                            <% } %>

                        </span>

                    </div>

                </div>


                <!-- ==============================================
                     PROGRESO
                     ============================================== -->

                <div class="progreso">

                    <div class="progreso-cabecera">

                        <span>
                            Progreso
                        </span>

                        <strong>
                            <%= tarea.getProgreso() %>%
                        </strong>

                    </div>


                    <div class="barra-progreso">

                        <div
                                class="barra-progreso-valor"
                                style="width: <%= tarea.getProgreso() %>%">
                        </div>

                    </div>

                </div>


                <!-- ==============================================
                     SUBTAREAS
                     ============================================== -->

                <div class="subtareas">


                    <div class="seccion-tarea-titulo">

                        <span>
                            ✓
                        </span>

                        <strong>
                            Subtareas
                        </strong>

                    </div>


                    <%
                        List<SubTask> subtareas =
                                subtareasPorTarea != null
                                        ? subtareasPorTarea.get(
                                        tarea.getId()
                                )
                                        : null;
                    %>


                    <% if (subtareas != null
                            && !subtareas.isEmpty()) { %>


                    <ul class="lista-subtareas">

                        <% for (SubTask subTask :
                                subtareas) { %>

                        <li class="subtarea">


                            <form
                                    action="${pageContext.request.contextPath}/subtareas"
                                    method="post">

                                <input
                                        type="hidden"
                                        name="accion"
                                        value="toggle">

                                <input
                                        type="hidden"
                                        name="id"
                                        value="<%= subTask.getIdSubtarea() %>">

                                <input
                                        type="checkbox"
                                        onchange="this.form.submit()"
                                    <%= subTask.isCompletada()
                                                        ? "checked"
                                                        : "" %>>

                            </form>


                            <span class="<%= subTask.isCompletada()
                                            ? "completada"
                                            : "" %>">

                                        <%= subTask.getDescripcion() %>

                                    </span>

                        </li>

                        <% } %>

                    </ul>


                    <% } else { %>


                    <p class="sin-datos">
                        No hay subtareas.
                    </p>


                    <% } %>

                </div>


                <!-- ==============================================
                     RECORDATORIOS
                     ============================================== -->

                <%
                    List<Reminder> recordatorios =
                            recordatoriosPorTarea != null
                                    ? recordatoriosPorTarea.get(
                                    tarea.getId()
                            )
                                    : null;
                %>


                <% if (recordatorios != null
                        && !recordatorios.isEmpty()) { %>


                <div class="recordatorios">


                    <div class="seccion-tarea-titulo">

                            <span>
                                ⏰
                            </span>

                        <strong>
                            Recordatorios
                        </strong>

                    </div>


                    <ul>

                        <% for (Reminder reminder :
                                recordatorios) { %>

                        <li>

                            <strong>
                                <%= reminder.getFechaHora() %>
                            </strong>


                            <% if (reminder.getMensaje() != null
                                    && !reminder.getMensaje().isBlank()) { %>

                            <span>
                                            <%= reminder.getMensaje() %>
                                        </span>

                            <% } %>

                        </li>

                        <% } %>

                    </ul>


                </div>


                <% } %>


            </article>


            <% } %>


        </div>


        <% } else { %>


        <!-- ====================================================
             SIN TAREAS
             ==================================================== -->

        <div class="sin-tareas">

            <div class="sin-tareas-icono">
                ✓
            </div>

            <h3>
                No tienes tareas todavía
            </h3>

            <p>
                Crea tu primera tarea usando el formulario de arriba.
            </p>

        </div>


        <% } %>


    </section>


</main>


<script
        src="${pageContext.request.contextPath}/js/tareas.js">
</script>


</body>

</html>

