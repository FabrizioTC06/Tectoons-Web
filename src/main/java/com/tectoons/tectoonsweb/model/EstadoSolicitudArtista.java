package com.tectoons.tectoonsweb.model;

public enum EstadoSolicitudArtista {

    PENDIENTE,   // La solicitud fue enviada y aún no revisada por un admin
    APROBADA,    // El usuario fue aceptado como artista
    RECHAZADA    // El admin revisó y rechazó la solicitud
}
