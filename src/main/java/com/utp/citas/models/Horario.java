package com.utp.citas.models;

public record Horario(int idHorario, int idMedico, int idSede, String fecha, String horaInicio, String estado) {
}
