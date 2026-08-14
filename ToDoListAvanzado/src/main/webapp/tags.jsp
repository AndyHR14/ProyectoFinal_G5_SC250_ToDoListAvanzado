<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.todolist.model.Tag" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Etiquetas - ToDo List</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/recordatorios.css">

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


<h1>Etiquetas</h1>


<!-- ============================================================
     FORMULARIO
     ============================================================ -->

<section class="formulario-tarea">

    <%
        Tag tagEditar =
                (Tag) request.getAttribute("tagEditar");
    %>


    <% if (tagEditar == null) { %>

    <!-- ====================================================
         CREAR ETIQUETA
         ==================================================== -->

    <h2>Agregar etiqueta</h2>

    <form action="${pageContext.request.contextPath}/tags"
          method="post">

        <input type="hidden"
               name="accion"
               value="crear">


        <div class="campo">

            <label for="nombre">
                Nombre
            </label>

            <input
                    type="text"
                    id="nombre"
                    name="nombre"
                    maxlength="50"
                    required>

        </div>


        <button type="submit">
            Agregar etiqueta
        </button>

    </form>


    <% } else { %>


    <!-- ====================================================
         EDITAR ETIQUETA
         ==================================================== -->

    <h2>Editar etiqueta</h2>

    <form action="${pageContext.request.contextPath}/tags"
          method="post">

        <input type="hidden"
               name="accion"
               value="actualizar">


        <input type="hidden"
               name="id"
               value="<%= tagEditar.getIdEtiqueta() %>">


        <div class="campo">

            <label for="nombre">
                Nombre
            </label>

            <input
                    type="text"
                    id="nombre"
                    name="nombre"
                    maxlength="50"
                    value="<%= tagEditar.getNombre() %>"
                    required>

        </div>


        <button type="submit">
            Guardar cambios
        </button>


        <a href="${pageContext.request.contextPath}/tags">
            Cancelar
        </a>

    </form>


    <% } %>

</section>



<!-- ============================================================
     LISTA DE ETIQUETAS
     ============================================================ -->

<section class="lista-tareas">

    <h2>Etiquetas registradas</h2>


    <%
        List<Tag> etiquetas =
                (List<Tag>)
                        request.getAttribute("etiquetas");
    %>


    <% if (etiquetas != null && !etiquetas.isEmpty()) { %>


    <% for (Tag tag : etiquetas) { %>


    <article class="tarea">


        <h3>
            <%= tag.getNombre() %>
        </h3>


        <p>

            <strong>ID:</strong>

            <%= tag.getIdEtiqueta() %>

        </p>


        <!-- ============================================
             EDITAR
             ============================================ -->

        <form action="${pageContext.request.contextPath}/tags"
              method="get">

            <input
                    type="hidden"
                    name="accion"
                    value="editar">

            <input
                    type="hidden"
                    name="id"
                    value="<%= tag.getIdEtiqueta() %>">

            <button type="submit">
                Editar
            </button>

        </form>


        <!-- ============================================
             ELIMINAR
             ============================================ -->

        <form action="${pageContext.request.contextPath}/tags"
              method="post"
              onsubmit="return confirm('¿Está seguro de eliminar esta etiqueta?');">

            <input
                    type="hidden"
                    name="accion"
                    value="eliminar">

            <input
                    type="hidden"
                    name="id"
                    value="<%= tag.getIdEtiqueta() %>">

            <button type="submit">
                Eliminar
            </button>

        </form>


    </article>


    <% } %>


    <% } else { %>


    <p>
        No hay etiquetas registradas.
    </p>


    <% } %>


</section>



<!-- ============================================================
     VOLVER A TAREAS
     ============================================================ -->

<p>

    <a href="${pageContext.request.contextPath}/tareas">
        Volver a tareas
    </a>

</p>


</body>

</html>