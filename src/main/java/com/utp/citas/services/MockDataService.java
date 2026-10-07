package com.utp.citas.services;

import com.utp.citas.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MockDataService {

    // Simulación de tablas en base de datos
    private final List<Sede> sedes = List.of(new Sede(1, "Sede Central", "Av. Principal 123"));
    private final List<Especialidad> especialidades = List.of(new Especialidad(1, "Cardiología"), new Especialidad(2, "Medicina General"));
    private final List<Medico> medicos = List.of(new Medico(1, "Juan", "Pérez", 1));
    private final List<Horario> horarios = new ArrayList<>(List.of(
            new Horario(1, 1, 1, "2026-10-15", "10:00", "DISPONIBLE")
    ));

    // Almacén en memoria para citas y pagos
    private final Map<String, ReservaResponse> reservas = new HashMap<>();
    private final Map<String, PagoRequest> pagosPendientes = new HashMap<>();

    // Catálogos
    public List<Sede> getSedes() { return sedes; }
    public List<Especialidad> getEspecialidades() { return especialidades; }
    public List<Horario> getHorariosDisponibles() {
        return horarios.stream().filter(h -> h.estado().equals("DISPONIBLE")).toList();
    }

    // Lógica de Reserva
    public ReservaResponse crearReserva(ReservaRequest request) {
        String codigoGenerado = "RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        ReservaResponse response = new ReservaResponse(
                codigoGenerado,
                "PENDIENTE_PAGO",
                LocalDateTime.now().plusHours(24) // Plazo de 24 horas según BPMN
        );
        reservas.put(codigoGenerado, response);
        return response;
    }

    public ReservaResponse consultarReserva(String dni, String codigoReserva) {
        return reservas.getOrDefault(codigoReserva, null);
    }

    // Lógica de Pagos
    public String registrarPago(PagoRequest request) {
        if (!reservas.containsKey(request.codigoReserva())) return "Reserva no encontrada";
        pagosPendientes.put(request.codigoReserva(), request);
        return "Comprobante adjuntado. Pendiente de validación.";
    }

    public List<PagoRequest> obtenerPagosPendientes() {
        return new ArrayList<>(pagosPendientes.values());
    }

    public String validarPago(String codigoReserva, boolean aprobado) {
        if (aprobado) {
            ReservaResponse reservaActualizada = new ReservaResponse(codigoReserva, "CONFIRMADA", null);
            reservas.put(codigoReserva, reservaActualizada);
            pagosPendientes.remove(codigoReserva);
            return "Pago aprobado, cita confirmada y constancia generada.";
        } else {
            return "Pago rechazado. Por favor, reintente.";
        }
    }

}
