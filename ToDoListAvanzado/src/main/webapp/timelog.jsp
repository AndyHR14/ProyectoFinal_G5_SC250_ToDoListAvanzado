<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="org.todolist.model.TimeLog" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Registro de tiempo - ToDo List</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/timelog.css">

</head>

<body>

<h1>Mis registros de tiempo</h1>


<!-- ============================================================
     FORMULARIO CREAR / EDITAR
     ============================================================ -->

<%
    TimeLog timelogEditar =
            (TimeLog) request.getAttribute("timelogEditar");

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
%>


<section class="formulario-timelog">


    <% if (timelogEditar == null) { %>


    <!-- ========================================================
         CREAR REGISTRO
         ======================================================== -->

    <h2>Agregar registro de tiempo</h2>


    <form action="${pageContext.request.contextPath}/timelog"
          method="post">


        <input type="hidden"
               name="accion"
               value="crear">


        <div class="campo">

            <label for="inicio">
                Fecha y hora de inicio
            </label>

            <input
                    type="datetime-local"
                    id="inicio"
                    name="inicio"
                    required>

        </div>


        <div class="campo">

            <label for="fin">
                Fecha y hora de fin
            </label>

            <input
                    type="datetime-local"
                    id="fin"
                    name="fin">

        </div>


        <div class="campo">

            <label for="idTarea">
                ID de tarea
            </label>

            <input
                    type="number"
                    id="idTarea"
                    name="idTarea"
                    min="1"
                    required>

        </div>


        <button type="submit">
            Agregar registro
        </button>


    </form>


    <% } else { %>


    <!-- ========================================================
         EDITAR REGISTRO
         ======================================================== -->

    <h2>Editar registro de tiempo</h2>


    <form action="${pageContext.request.contextPath}/timelog"
          method="post">


        <input type="hidden"
               name="accion"
               value="actualizar">


        <input type="hidden"
               name="idRegistro"
               value="<%= timelogEditar.getIdRegistro() %>">


        <div class="campo">

            <label for="inicio">
                Fecha y hora de inicio
            </label>

            <input
                    type="datetime-local"
                    id="inicio"
                    name="inicio"
                    value="<%= timelogEditar.getInicio() != null
                            ? timelogEditar.getInicio().format(formato)
                            : "" %>"
                    required>

        </div>


        <div class="campo">

            <label for="fin">
                Fecha y hora de fin
            </label>

            <input
                    type="datetime-local"
                    id="fin"
                    name="fin"
                    value="<%= timelogEditar.getFin() != null
                            ? timelogEditar.getFin().format(formato)
                            : "" %>">

        </div>


        <div class="campo">

            <label for="idTarea">
                ID de tarea
            </label>

            <input
                    type="number"
                    id="idTarea"
                    name="idTarea"
                    min="1"
                    value="<%= timelogEditar.getIdTarea() %>"
                    required>

        </div>


        <button type="submit">
            Guardar cambios
        </button>


        <a href="${pageContext.request.contextPath}/timelog">
            Cancelar
        </a>


    </form>


    <% } %>


</section>



<!-- ============================================================
     LISTA DE REGISTROS
     ============================================================ -->

<section class="lista-timelog">


    <h2>Registros de tiempo</h2>


    <%
        List<TimeLog> timelogs =
                (List<TimeLog>) request.getAttribute("timelogs");
    %>


    <% if (timelogs == null || timelogs.isEmpty()) { %>


    <p>
        No hay registros de tiempo.
    </p>


    <% } else { %>


    <div class="timelogs">


        <% for (TimeLog registro : timelogs) { %>


        <article class="timelog">


            <h3>
                Registro #<%= registro.getIdRegistro() %>
            </h3>


            <!-- =================================================
                 INFORMACIÓN DEL REGISTRO
                 ================================================= -->

            <p>

                <strong>Tarea:</strong>

                <%= registro.getIdTarea() %>

            </p>


            <p>

                <strong>Inicio:</strong>

                <%= registro.getInicio() != null
                        ? registro.getInicio().format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy HH:mm"
                                )
                        )
                        : "Sin inicio" %>

            </p>


            <p>

                <strong>Fin:</strong>

                <%= registro.getFin() != null
                        ? registro.getFin().format(
                                DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy HH:mm"
                                )
                        )
                        : "En curso" %>

            </p>


            <p>

                <strong>Duración:</strong>

                <%= registro.getDuracionMinutos() %>
                minutos

            </p>


            <!-- =================================================
                 ESTADO
                 ================================================= -->

            <p>

                <strong>Estado:</strong>


                <% if (registro.estaActivo()) { %>

                    <span class="estado-activo">
                        En curso
                    </span>

                <% } else { %>

                    <span class="estado-finalizado">
                        Finalizado
                    </span>

                <% } %>

            </p>



            <!-- =================================================
                 ACCIONES
                 ================================================= -->

            <div class="acciones">


                <!-- =============================================
                     INICIAR / FINALIZAR
                     ============================================= -->

                <% if (!registro.estaActivo()) { %>


                <form action="${pageContext.request.contextPath}/timelog"
                      method="post"
                      style="display:inline;">


                    <input type="hidden"
                           name="accion"
                           value="iniciar">


                    <input type="hidden"
                           name="idTarea"
                           value="<%= registro.getIdTarea() %>">


                    <button type="submit">

                        Iniciar conteo

                    </button>


                </form>


                <% } else { %>


                <form action="${pageContext.request.contextPath}/timelog"
                      method="post"
                      style="display:inline;">


                    <input type="hidden"
                           name="accion"
                           value="finalizar">


                    <input type="hidden"
                           name="idRegistro"
                           value="<%= registro.getIdRegistro() %>">


                    <button type="submit">

                        Finalizar conteo

                    </button>


                </form>


                <% } %>



                <!-- =============================================
                     EDITAR
                     ============================================= -->

                <form action="${pageContext.request.contextPath}/timelog"
                      method="get"
                      style="display:inline;">


                    <input type="hidden"
                           name="accion"
                           value="editar">


                    <input type="hidden"
                           name="id"
                           value="<%= registro.getIdRegistro() %>">


                    <button type="submit">

                        Editar

                    </button>


                </form>



                <!-- =============================================
                     ELIMINAR
                     ============================================= -->

                <form action="${pageContext.request.contextPath}/timelog"
                      method="post"
                      style="display:inline;">


                    <input type="hidden"
                           name="accion"
                           value="eliminar">


                    <input type="hidden"
                           name="id"
                           value="<%= registro.getIdRegistro() %>">


                    <button
                            type="submit"
                            onclick="return confirm(
                                '¿Está seguro de eliminar este registro de tiempo?'
                            );">

                        Eliminar

                    </button>


                </form>


            </div>


        </article>


        <% } %>


    </div>


    <% } %>


</section>



<script src="${pageContext.request.contextPath}/js/timelog.js"></script>


</body>

</html>