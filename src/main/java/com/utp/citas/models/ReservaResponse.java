package com.utp.citas.models;

import java.time.LocalDateTime;

public record ReservaResponse(String codigoReserva, String estado, LocalDateTime fechaVencimiento) {
}
