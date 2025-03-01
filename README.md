# API Partidas

Una API para manejar el alamcenamiento de partidas jugadas de un videojuego.

---
## Procedimientos para la gestión de Partidas

### ENDPOINTS

| Método | Ruta                                   | Descripción                                                                                                                                                                                                                 |
|--------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | /partida                               | Obtener partidas. Según los parámetros de consulta se puede filtrar: <br><br>• **nombrePartida:** Retorna la partida en formato _updatePartida_. <br>• **resultado:** Retorna las partidas con el resultado indicado. <br>• Sin parámetros: Retorna todas las partidas.  |
| GET    | /partida/{nombrePartida}               | Obtener los detalles de una partida identificada por su nombre.                                                                                                                                                             |
| GET    | /partida/{resultado}                   | **[Endpoint reservado]** Endpoint declarado para recibir el parámetro `resultado` vía path, pero sin implementación en el código actual.                                                                                     |
| POST   | /partida                               | Insertar una nueva partida. Recibe un JSON que se mapea al modelo **Partida** y, en caso de conflicto (por ejemplo, partida ya existente), devuelve un error.                                                            |
| PATCH  | /partida/{nombrePartida}               | Actualizar una partida existente. Recibe un JSON con los nuevos datos (modelo **UpdatePartida**) y actualiza la partida identificada por el parámetro `nombrePartida`. En caso de fallo (por ejemplo, partida no encontrada) se devuelve un error.         |
| DELETE | /partida/{nombrePartida}               | Eliminar una partida identificada por su nombre. Si la partida no existe se devuelve un error; de lo contrario, se elimina y se notifica la acción realizada.                                                              |

### Gestión de Partidas

Todos los endpoints del grupo `/partida` requieren autenticación mediante **JWT**. El token debe enviarse en la cabecera **Authorization** con el prefijo `Bearer`.

#### Obtener partidas (GET /partida)

- **Autenticación:** Se valida el token recibido en la cabecera.
- **Parámetros de consulta:**
  - `nombrePartida`: Si se envía, se busca la partida por nombre y se retorna en su representación de actualización (_updatePartida_).  
    >[!IMPORTANT]  
    >Si la partida no se encuentra, se responde con un código **404 Not Found** y un mensaje indicativo.
    
  - `resultado`: Si se envía, se filtran las partidas por el valor del resultado (convertido a mayúsculas y mapeado a un enum).  
    >[!IMPORTANT]  
    >Si el valor no es válido, se responde con **400 Bad Request**.
    
- **Sin parámetros:** Devuelve la lista completa de partidas.

#### Obtener partida por nombre (GET /partida/{nombrePartida})

- **Autenticación:** Se valida el token.
- **Parámetro de ruta:**  
  - `nombrePartida`: Nombre de la partida a buscar.  
    >[!IMPORTANT]  
    >Si no se proporciona o no se encuentra la partida, se responde con **400 Bad Request** o **404 Not Found** respectivamente.

#### Insertar una nueva partida (POST /partida)

- **Autenticación:** Se valida el token.
- **Cuerpo de la solicitud:**  
  - Se debe enviar un JSON que se mapea al modelo **Partida**.
- **Respuestas:**  
  - **201 Created:** Si la inserción es exitosa.  
  - **409 Conflict:** Si la partida ya existe o se genera conflicto.  
  - **400 Bad Request:** En caso de errores en el formato o datos insuficientes.

#### Actualizar una partida (PATCH /partida/{nombrePartida})

- **Autenticación:** Se valida el token.
- **Parámetro de ruta:**  
  - `nombrePartida`: Nombre de la partida a actualizar.
- **Cuerpo de la solicitud:**  
  - Se debe enviar un JSON que se mapea al modelo **UpdatePartida**.
- **Respuestas:**  
  - **201 Created:** Si la actualización es exitosa.  
  - **409 Conflict:** Si no se pudo modificar (por ejemplo, la partida no existe).  
  - **400 Bad Request:** En caso de errores en el formato o lectura de datos.

#### Eliminar una partida (DELETE /partida/{nombrePartida})

- **Autenticación:** Se valida el token.
- **Parámetro de ruta:**  
  - `nombrePartida`: Nombre de la partida a eliminar.
- **Respuestas:**  
  - **200 OK:** Si la eliminación se realiza correctamente.  
  - **404 Not Found:** Si la partida no se encuentra.  
  - **204 No Content:** Si no se proporciona el nombre de la partida.

---

## Procedimientos para la autenticación y registro de Usuarios

### ENDPOINTS

| Método | Ruta      | Descripción                                                                                                         |
|--------|-----------|---------------------------------------------------------------------------------------------------------------------|
| POST   | /auth     | Iniciar sesión. Recibe credenciales de usuario y, si son correctas, retorna los datos del usuario junto con el token. |
| POST   | /register | Registrar un nuevo usuario. Recibe un JSON con los datos del usuario y, en caso de éxito, retorna el usuario creado. |

### Gestión de Usuarios

#### Iniciar sesión (POST /auth)

- **Cuerpo de la solicitud:**  
  - Se debe enviar un JSON que se mapea al modelo **UpdateUsuario** y que contiene, al menos, el `dni` y `password`.
- **Proceso:**  
  1. Se recibe la solicitud de inicio de sesión.
  2. Se valida la autenticación a través del método `login` de la capa de uso.
  3. Si la autenticación es exitosa, se retorna el objeto de usuario (con el token asignado) y se responde con **200 OK**.
  4. En caso contrario, se responde con **401 Unauthorized**.
- **Errores comunes:**  
  - **400 Bad Request:** En caso de formato incorrecto de la solicitud.

#### Registrar un nuevo usuario (POST /register)

- **Cuerpo de la solicitud:**  
  - Se debe enviar un JSON que se mapea al modelo **UpdateUsuario**.
- **Proceso:**  
  1. Se recibe la solicitud de registro.
  2. Se procesa el registro mediante el método `register` de la capa de uso.
  3. Si el registro es exitoso, se retorna el usuario creado en su representación de actualización y se responde con **201 Created**.
  4. Si no se puede realizar el registro (por ejemplo, conflicto en datos), se responde con **409 Conflict**.
- **Errores comunes:**  
  - **400 Bad Request:** En caso de errores en el formato o lectura del JSON.

---
