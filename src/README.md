# Instalacón de Odoo
#### Por: Alan, Adrián e Isaac

## Preparación del archivo .yaml

![Captura desde 2026-10-08 11-18-09.png](../Fotos/Captura%20desde%202026-10-08%2011-18-09.png)

Se crea un `compose.yaml` mediante `touch` donde se agregara las siguientes lineas: 

- Version: Es la versión del docker compose que se utiliza.
- Services:
  - db:
    - Imagen: Es la imagen de la base de datos del postgres y su versión.
    - Environment: Configuración postgresSQL.
      - POSTGRES_DB: Nombre de la base de datos.
      - POSTGRES_USER: Nombre del ususario admin.
      - POSTGRES_PASSORD: Contraseña del admin.
    - Volumen: 
      - Conecta la carpeta la carpeta interna del ordenador de odoo con la carpeta de la base de datos de postgress.
  - web:
    - Imagen: Es la imagen de odoo y su versión.
    - Ports:
      - Son los puertos que utiliza para poder acceder a el desde el navegador.
    - Enviroment:
      - HOST: Indica que la base de datos ensta en el contenedor db.
      - USER: Ususario de la base de datos. 
      - PASSWORD: Contraseña para la conexión.
    - depends_on:
      - db: Garantiza el orden de arranque.
    -Volumen:
      - Conecta la caprtea segura en tuerdonador con la carpeta interna de odoo.
  Volumes:
      - Crea el volumen gestionado por docker para almacenar los datos de postgres.
      - Crea el volumen gestionado por docker para almacenar los datos de odoo.
    
```Terminal
version: '3.8'

services:
  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=postgres
      - POSTGRES_USER=triade
      - POSTGRES_PASSWORD=alanguapo
    volumes:
      - odoo-db-data:/var/lib/postgresql/data

  web:
    image: odoo:18
    ports:
      - "8069:8069"
    environment:
      - HOST=db
      - USER=triade
      - PASSWORD=alanguapo
    depends_on:
      - db
    volumes:
      - odoo-web-data:/var/lib/odoo

volumes:
  odoo-db-data:
  odoo-web-data:
```
## Instalación odoo
![Captura desde 2026-10-08 11-19-45.png](../Fotos/Captura%20desde%202026-10-08%2011-19-45.png)

Para la montar odoo en un dispositivo se debe lanzar el comando `docker compose up -d`, compose es el archvio que se configuro anteriormente y el `-d` ejecuta los comtenedores en segundoplano dejando la terminal libre.

## Configuración de odoo web

Una vez montado se accedera a la web, sepone en el buscar ip_del_equipo:8069 del dispositivo en el buscador, una vez dentro se realizara una configuración básica de datos personales para completar la configuración de odoo. 

![Captura desde 2026-10-08 11-23-28.png](../Fotos/Captura%20desde%202026-10-08%2011-23-28.png)
![Captura desde 2026-10-08 11-25-57.png](../Fotos/Captura%20desde%202026-10-08%2011-25-57.png)

## Sistema montado.
Una vez acabado todo se mostrara la siguente pagina con nuestro sistema odoo montado.

![Captura desde 2026-10-14 14-31-29.png](../Fotos/Captura%20desde%202026-10-14%2014-31-29.png)







