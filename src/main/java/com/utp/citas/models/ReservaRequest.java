package com.utp.citas.models;

import java.time.LocalDateTime;

public record ReservaRequest(String codigoReserva, String estado, LocalDateTime fechaVencimiento) {
}
