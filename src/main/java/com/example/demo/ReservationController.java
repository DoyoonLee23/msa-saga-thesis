package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/api/reservations")
    public String makeReservation() {
        Long id = reservationService.reserveTicket();
        return "예매 요청 완료 (티켓 ID: " + id + ")";
    }
}