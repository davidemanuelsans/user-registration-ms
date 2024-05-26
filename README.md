
# APP Órdenes

La idea es crear una pequeña app que muestre órdenes de un usuario. Para eso hay que registrarse como usuario, loguearse y, jwt en mano, consultar nuestras órdenes.

![Flow](https://github.com/davidemanuelsans/user-registration-ms/blob/master/order-service-auth.png)

## Arquitectura

La arquitectura es bastante simple: es un sólo microservicio montado en Spring-boot 3.2 con Java 17. El gesto de dependencias es maven. El proveedor de autenticación es propio(a través de spring-security) y, tanto la data de autenticación como de negocio(órdenes) están montado sobre la misma base de datos relacional H2(in-memory).

Sigue estos pasos para ejecutar los microservicios de la aplicación.

## Clonar el repo

```bash
git clone https://github.com/davidemanuelsans/user-registration-ms.git
```

## Instalación

```
mvn install
```

## Ejecutar la aplicación

```
ejecutar

```

## Pruebas

```
probar

```



## Verificación

Para verificar, visita [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) Ahí están listados los endpoints de la aplicación que son sólo 4: registrarse, loguearse, actualizar mi perfil y consultar mis órdenes(ver el diagrama del principio). Cabe destacar que los 2 últimos endpoints necesitan de un jwt para poder consumirse. Además, tienen algunas validaciones básicas sobre el tipo de dato que se envía, a saber: el email debe seguir la expresión regular para emails, la contraseña debe llevar una mayúscula, una minúscula y un número y no ser menor a 6 caracteres. De otro modo va a arrojar un error.
También tenemos un script de seed que crea órdenes para los clientes con IDs 1, 2, 3 y 4. De modo que si consulta las órdenes con un usuairo con alguno de esos IDs va a traer data(de otro modo va a tirar NOT_FOUND). Al respecto de migraciones; spring-boot debiera generar automáticamente las tablas en H2. Si asi no lo hiciera, en el archivo **migraciones.sql** está la estructura para las tablas de usuario y teléfono.
A continuación dejo un ejemplo de los 4 endpoints en cuestión en formato CURL.

```
## Registro
curl --location 'http://localhost:8081/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "pepe@papa.com",
    "password": "Pa121113",
    "name": "pepe papa",
    "phones": [
        {
            "number": "1234567",
            "citycode": "21211",
            "contrycode": "57"
        }
    ]
}'
```
```
## Login
curl --location 'http://localhost:8081/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "pepe@papa.com",
    "password": "Pa121113" 
}'
```
```
## Update
curl --location 'http://localhost:8081/users/update' \
--header 'Authorization: Bearer {SU_TOKEN}' \
--header 'Content-Type: application/json' \
--data '{
    "name": "peperino pomoro"
}'
```
```
## Consulta órdenes
curl --location 'http://localhost:8081/orders' \
--header 'Authorization: Bearer {SU_TOKEN}' \
--header 'Cookie: JSESSIONID=8D5AE5C82500E521D9C59412C71E1919'
```

## Cierre
Se acepta cualquier feedback acerca del estilo, diseño, respuesta http(404 cuando no hay data vs JSON vacío, etc.). Desde ya, gracias.
