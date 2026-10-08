import Services.Servicios;

void main() {
    Servicios.insertarAnimeParametros("One Piece", "Un joven pirata llamado Luffy busca el tesoro legendario para convertirse en el Rey de los Piratas.", "1999-10-20", 96);
    Servicios.leerAnimes();
    Servicios.leerAnimesPorPuntuacion(96);
    Servicios.actualizarAnimeParametros("One piece", 99);
    Servicios.eliminarAnimeParametros("One Piece");

}
