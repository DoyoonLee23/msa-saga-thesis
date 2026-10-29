package com.example.demo;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentSagaConsumer {
    private final ReservationService reservationService;

    public PaymentSagaConsumer(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 결제 실패 상황을 가정하여 보상 트랜잭션 자동 실행
    @EventListener
    public void handlePaymentFail(String ticketId) {
        System.out.println("[장애 감지] 결제 실패! 티켓 ID: " + ticketId + " 보상 트랜잭션을 시작합니다.");
        reservationService.cancelTicket(Long.parseLong(ticketId));
    }
}