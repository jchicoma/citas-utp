package com.utp.citas.controllers;

import com.utp.citas.models.*;
import com.utp.citas.services.MockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    private final MockDataService dataService;

    public PacienteController(MockDataService dataService) {
        this.dataService = dataService;
    }

    // Flujo de selección
    @GetMapping("/sedes")
    public List<Sede> listarSedes() { return dataService.getSedes(); }

    @GetMapping("/especialidades")
    public List<Especialidad> listarEspecialidades() { return dataService.getEspecialidades(); }

    @GetMapping("/horarios")
    public List<Horario> listarHorariosDisponibles() { return dataService.getHorariosDisponibles(); }

    // Gestión de Citas
    @PostMapping("/reservas")
    public ResponseEntity<ReservaResponse> realizarReserva(@RequestBody ReservaRequest request) {
        return ResponseEntity.ok(dataService.crearReserva(request));
    }

    @GetMapping("/reservas")
    public ResponseEntity<ReservaResponse> consultarReserva(@RequestParam String dni, @RequestParam String codigo) {
        ReservaResponse reserva = dataService.consultarReserva(dni, codigo);
        return reserva != null ? ResponseEntity.ok(reserva) : ResponseEntity.notFound().build();
    }

    @PostMapping("/pagos")
    public ResponseEntity<String> realizarPago(@RequestBody PagoRequest pagoRequest) {
        return ResponseEntity.ok(dataService.registrarPago(pagoRequest));
    }
}