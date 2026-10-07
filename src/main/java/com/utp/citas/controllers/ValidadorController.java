package com.utp.citas.controllers;

import com.utp.citas.models.PagoRequest;
import com.utp.citas.services.MockDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validador")
public class ValidadorController {

    private final MockDataService dataService;

    public ValidadorController(MockDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/pagos-pendientes")
    public ResponseEntity<List<PagoRequest>> consultarPagosPendientes() {
        return ResponseEntity.ok(dataService.obtenerPagosPendientes());
    }

    @PostMapping("/pagos/{codigoReserva}/validar")
    public ResponseEntity<String> validarPago(
            @PathVariable String codigoReserva,
            @RequestParam boolean aprobado) {
        return ResponseEntity.ok(dataService.validarPago(codigoReserva, aprobado));
    }
}