# NisumTest
Test Nisum

La aplicacion corresponde a un Test para Nisum. Consiste en un API Rest con dos endpoints, uno el pedido, un alta de usuarios, 
y otro la lista de todos los usuarios, para fines practicos,
A estos endpoints se puede acceder via swagger, uno de los requerimientos.
Se pude compilar el codigo en un entorno de desarrollo  como el eclipse,idea, etc, y luego ejecutar  StartAplication
Esta clase lanza el servidor de SpringBoot, otro de los requerimientos. Esta configurado en el puerto por default, 8080

Para acceder al Swagger, la url es http://localhost:8080/swagger-ui.html
La url para listar los usuarios dados de Alta es http://localhost:8080/list

la url para dar de alta nuevos usuarios es http://localhost:8080/registerUser

Un ejemplo de request de este Post es

{
"email": "email@email.cl",
"name": "nombre",
"password": "Password12$",
"phone": {
    "citycode": 12,
    "countrycode": 21,
    "number": 123456
    }
}

Se entrega con Test Unitarios sobre la capa de Servicio y de Controlles, se pueden ejecutar a modo de prueba individual

Se opto por separar la capa de control, de la de servicios, ya que esta ultima tenia bastante logica de control.

Ademas se agrego un modelo de entrada de Datos UserInn, que luego, en la capa de servicio se transforma en un  User,
cuando se realizan todos los controles de logica (email y pass).

Se opto por separar en tres capas para implementar mejor el modelo MVC, separando la vista del control y el modelo.
Y dejar modulos mas independientes, pero cohesivos entre si.

Se persiste, en otra capa de Repositorio jpa, otro de los requerimientos, como un User, donde al usuario se le agrega 
las fecha de ingreso, y el token.

Se tomo tambien la decision de separar las definicnioes de las impleemntaciones en la capa de servicios,
Implementando el patron del Adaptador, donde una iternface pusde integrarse a diferentes implementaciones

Para el manejo de errores existe una capa de error, donde estan cada uno de los errores de ejecucion, en forma de 
excepcion, que se fueron estableciendo

Se dejo una explicacion de las restricciones con respecto a la password, en el archivo application.properties, que es 
desde donde se configura, por medio de una expresion regular

El correo, puede contener cualquier dominio, pero debe finalizar con .cl

