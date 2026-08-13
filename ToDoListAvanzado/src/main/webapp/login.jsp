
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Iniciar sesión - ToDoList</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/css/login.css">
</head>

<body>

<div class="contenedor">

    <div class="encabezado">
        <h1>ToDoList</h1>
        <p>Organiza tus tareas de forma sencilla</p>
    </div>

    <div class="separador"></div>

    <h2>Iniciar sesión</h2>

    <% if (request.getAttribute("error") != null) { %>

    <div class="error">
        <%= request.getAttribute("error") %>
    </div>

    <% } %>

    <form action="<%= request.getContextPath() %>/usuario"
          method="post">

        <input type="hidden"
               name="accion"
               value="login">

        <div class="campo">

            <label for="correo">
                Correo electrónico
            </label>

            <input
                    type="email"
                    id="correo"
                    name="correo"
                    placeholder="ejemplo@correo.com"
                    required
            >

        </div>

        <div class="campo">

            <label for="contrasena">
                Contraseña
            </label>

            <input
                    type="password"
                    id="contrasena"
                    name="contrasena"
                    placeholder="Ingresa tu contraseña"
                    required
            >

        </div>

        <button type="submit">
            Iniciar sesión
        </button>

    </form>

    <div class="registro">

        <span>¿No tienes una cuenta?</span>

        <a href="<%= request.getContextPath() %>/usuario?accion=registro">
            Regístrate aquí
        </a>

    </div>

</div>

</body>
</html>

