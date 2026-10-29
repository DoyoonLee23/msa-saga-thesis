package com.example.demo;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {
    private final TicketRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationService(TicketRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Long reserveTicket() {
        Ticket ticket = new Ticket();
        ticket.setStatus("RESERVED"); // 1. 좌석 임시 확보
        repository.save(ticket);
        
        System.out.println("[이벤트 발행] 결제 요청 이벤트 전송 (티켓 ID: " + ticket.getId() + ")");
        // 2. 비동기 이벤트 발행 (카프카 대체)
        eventPublisher.publishEvent(String.valueOf(ticket.getId()));
        return ticket.getId();
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = repository.findById(ticketId).orElseThrow();
        ticket.setStatus("CANCELLED"); // 3. 보상 트랜잭션 (재고 원상 복구)
        repository.save(ticket);
    }
}