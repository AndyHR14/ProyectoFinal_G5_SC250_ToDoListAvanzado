<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.todolist.model.Category" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <title>Categorías - ToDo List</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/categorias.css">
</head>

<body>

<h1>Mis categorías</h1>


<!-- ============================================================
     FORMULARIO CREAR / EDITAR
     ============================================================ -->

<%
    Category categoriaEditar =
            (Category) request.getAttribute("categoriaEditar");
%>


<section class="formulario-categoria">

    <% if (categoriaEditar == null) { %>

    <h2>Agregar categoría</h2>

    <form action="${pageContext.request.contextPath}/categorias"
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


        <div class="campo">

            <label for="color">
                Color
            </label>

            <input
                    type="text"
                    id="color"
                    name="color"
                    maxlength="20"
                    placeholder="Ej: Rojo, Azul, Verde">

        </div>


        <button type="submit">
            Agregar categoría
        </button>

    </form>


    <% } else { %>


    <h2>Editar categoría</h2>

    <form action="${pageContext.request.contextPath}/categorias"
          method="post">

        <input type="hidden"
               name="accion"
               value="actualizar">

        <input type="hidden"
               name="id"
               value="<%= categoriaEditar.getId() %>">


        <div class="campo">

            <label for="nombre">
                Nombre
            </label>

            <input
                    type="text"
                    id="nombre"
                    name="nombre"
                    maxlength="50"
                    value="<%= categoriaEditar.getNombre() %>"
                    required>

        </div>


        <div class="campo">

            <label for="color">
                Color
            </label>

            <input
                    type="text"
                    id="color"
                    name="color"
                    maxlength="20"
                    value="<%= categoriaEditar.getColor() != null
                                ? categoriaEditar.getColor()
                                : "" %>"
                    placeholder="Ej: Rojo, Azul, Verde">

        </div>


        <button type="submit">
            Guardar cambios
        </button>


        <a href="${pageContext.request.contextPath}/categorias">
            Cancelar
        </a>

    </form>

    <% } %>

</section>



<!-- ============================================================
     LISTA DE CATEGORÍAS
     ============================================================ -->

<section class="lista-categorias">

    <h2>Categorías registradas</h2>


    <%
        List<Category> categorias =
                (List<Category>) request.getAttribute("categorias");
    %>


    <% if (categorias == null || categorias.isEmpty()) { %>

    <p>
        No hay categorías registradas.
    </p>

    <% } else { %>


    <div class="categorias">

        <% for (Category categoria : categorias) { %>

        <article class="categoria">

            <h3>
                <%= categoria.getNombre() %>
            </h3>


            <p>
                <strong>ID:</strong>
                <%= categoria.getId() %>
            </p>


            <p>
                <strong>Color:</strong>

                <%= categoria.getColor() != null
                        ? categoria.getColor()
                        : "Sin color" %>
            </p>


            <!-- =================================================
                 EDITAR
                 ================================================= -->

            <div class="acciones">

                <form action="${pageContext.request.contextPath}/categorias"
                      method="get"
                      style="display:inline;">

                    <input type="hidden"
                           name="accion"
                           value="editar">

                    <input type="hidden"
                           name="id"
                           value="<%= categoria.getId() %>">

                    <button type="submit">
                        Editar
                    </button>

                </form>


                <!-- ============================================
                     ELIMINAR
                     ============================================ -->

                <form action="${pageContext.request.contextPath}/categorias"
                      method="post"
                      style="display:inline;">

                    <input type="hidden"
                           name="accion"
                           value="eliminar">

                    <input type="hidden"
                           name="id"
                           value="<%= categoria.getId() %>">


                    <button type="submit"
                            onclick="return confirm('¿Está seguro de eliminar esta categoría?');">

                        Eliminar

                    </button>

                </form>

            </div>

        </article>

        <% } %>

    </div>


    <% } %>

</section>



<script src="${pageContext.request.contextPath}/js/categorias.js"></script>

</body>

</html>