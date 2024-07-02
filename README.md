# Proyecto Android - Documentación

## Descripción
Este documento describe las decisiones arquitectónicas clave, patrones de diseño y bibliotecas externas utilizadas en este proyecto de Android.

## Arquitectura

### MVVM (Model-View-ViewModel)
El proyecto sigue el patrón arquitectónico MVVM para separar responsabilidades y mejorar el mantenimiento.

- **Model**: Representa los datos y la lógica de negocio de la aplicación.
- **View**: Responsable de renderizar la interfaz de usuario y manejar las interacciones del usuario.
- **ViewModel**: Actúa como intermediario entre la Vista y el Modelo, proporcionando datos a la Vista y manejando las acciones del usuario.

### Patrón de Repositorio
El patrón de repositorio se utiliza para abstraer la lógica de acceso a datos del resto de la aplicación. Los repositorios proporcionan una interfaz limpia para interactuar con fuentes de datos, ya sean bases de datos locales, APIs remotas u otras fuentes.

## Patrones de Diseño

### Inyección de Dependencias (Hilt)
Hilt se utiliza para la inyección de dependencias, gestionando la creación de objetos y sus dependencias. Simplifica el proceso de proporcionar dependencias a las clases y mejora la capacidad de prueba.

### Corrutinas
Se utilizan corrutinas de Kotlin para la programación asíncrona, facilitando el manejo de tareas en segundo plano, llamadas de red y otras operaciones asíncronas.

### Gestión de Estado (Jetpack Compose)
Los mecanismos de gestión de estado integrados en Jetpack Compose se utilizan para manejar los cambios de estado de la interfaz de usuario de manera eficiente y reactiva.

## Bibliotecas Externas

- **Jetpack Compose**: Se utiliza para construir la interfaz de usuario de forma declarativa. Simplifica el desarrollo de la interfaz de usuario y proporciona un enfoque moderno para construir interfaces en Android.
- **Retrofit**: Se utiliza para realizar solicitudes de red y obtener datos de APIs. Simplifica el proceso de definir y ejecutar llamadas de red.
- **Gson**: Se utiliza para la serialización y deserialización de datos JSON. Proporciona una forma conveniente de convertir datos JSON a objetos Kotlin y viceversa.
- **Hilt**: Se utiliza para la inyección de dependencias, como se mencionó anteriormente.
- **Mockk**: Se utiliza para simular dependencias en pruebas unitarias. Permite aislar unidades de código y probarlas de forma independiente.

## Pruebas

- **Pruebas Unitarias (JUnit)**: JUnit se utiliza para escribir pruebas unitarias y verificar la corrección de unidades individuales de código. En este proyecto se realizaron pruebas unitarias a los casos de uso (GetNewsUseCase, GetUsersUseCase), donde se probó la llamada al repositorio y la comprobación de los elementos, como a los View Models (HomeViewModel y UserViewModel), donde para ejecutar este último test se recomienda comentar los métodos init de ambos viewmodels ya que suele haber problemas al realizar pruebas.
- **Pruebas de UI (Jetpack Compose)**: El marco de pruebas de UI de Jetpack Compose se utiliza para escribir pruebas que verifican el comportamiento de la interfaz de usuario. Se realizaron pruebas para verificar el comportamiento en diferentes escenarios (cuando no hay ninguna noticia en el view model, cuando se está cargando y cuando se realiza un click a una noticia verificar que se navegue hacia el screen indicado.)

## Pantallas
Como lo pedido, el proyecto cuenta con 4 pantallas. Se encontrarán fotos de estas pantallas en el zip adjunto.
-  **Pantalla de noticias (HomeScreen)**: donde se verán las noticias y una barra de búsqueda para filtrar por título o contenido.
-  **Pantalla del detalle de noticia (NewsDetailsScreen)**: al hacer click en la noticia seleccionada por medio del id se buscará la noticia y se creará una pantalla donde se detalle algunos aspectos relevantes de la noticia junto a un TopAppBar para regresar a la pantalla de noticias.
-  **Pantalla de usuarios (UsersScreen)**: se verán los usuarios, el nombre, apellido, mail, ciudad y empresa junto a un botón para redirigirlo a un mapa.
-  **Pantalla de mapa (MapScreen)**: por medio de la latitud y longitud del usuario seleccionado se mostrará un mapa con las coordenadas del usuario ubicadas.
