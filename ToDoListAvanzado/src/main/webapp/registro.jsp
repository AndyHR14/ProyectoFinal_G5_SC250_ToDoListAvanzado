
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Crear cuenta - ToDoList</title>

    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/registro.css">

</head>

<body>

<div class="contenedor">

    <div class="encabezado">

        <h1>Crear cuenta</h1>

        <p>
            Crea tu cuenta para comenzar a organizar tus tareas.
        </p>

    </div>


    <%
        String error =
                (String) request.getAttribute("error");
    %>


    <% if (error != null) { %>

    <div class="error">
        <%= error %>
    </div>

    <% } %>


    <form
            action="${pageContext.request.contextPath}/usuario"
            method="post">

        <input
                type="hidden"
                name="accion"
                value="registrar">


        <div class="campo">

            <label for="nombre">
                Nombre
            </label>

            <input
                    type="text"
                    id="nombre"
                    name="nombre"
                    maxlength="100"
                    autocomplete="name"
                    required>

        </div>


        <div class="campo">

            <label for="correo">
                Correo
            </label>

            <input
                    type="email"
                    id="correo"
                    name="correo"
                    maxlength="150"
                    autocomplete="email"
                    required>

        </div>


        <div class="campo">

            <label for="contrasena">
                Contraseña
            </label>

            <input
                    type="password"
                    id="contrasena"
                    name="contrasena"
                    minlength="8"
                    autocomplete="new-password"
                    required>

            <small>
                La contraseña debe tener al menos 8 caracteres.
            </small>

        </div>


        <button type="submit">
            Registrarse
        </button>

    </form>


    <div class="login">

        <span>
            ¿Ya tienes una cuenta?
        </span>

        <a
                href="${pageContext.request.contextPath}/usuario?accion=login">

            Iniciar sesión

        </a>

    </div>

</div>

</body>

</html>

