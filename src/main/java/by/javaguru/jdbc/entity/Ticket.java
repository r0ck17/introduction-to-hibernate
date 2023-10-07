package by.javaguru.jdbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс с явно прописанными аннотациями @Column для формирования запроса в QueryGenerator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "seat_no")
    private String seatNo;

    @Column(name = "cost")
    private Integer cost;
}
