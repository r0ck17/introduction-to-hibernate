package by.javaguru.hibernate.dao;

import by.javaguru.hibernate.entity.Ticket;
import by.javaguru.hibernate.util.ConnectionManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicketDaoHibernateImplTest {
    private static SessionFactory sessionFactory;
    private static Dao<Ticket, Long> ticketDao;

    @BeforeAll
    public static void setUp() {
        sessionFactory = ConnectionManager.getSessionFactory();
        ticketDao = TicketDaoHibernateImpl.getInstance();
    }

    @AfterAll
    public static void tearDown() {
        ConnectionManager.closeSessionFactory();
    }

    @Test
    public void crudTest() {
        Ticket ticket = generateTicket();

        assertDoesNotThrow(() -> ticketDao.save(ticket), "Save method throws exception");

        Long ticketId = ticket.getId();
        assertNotNull(ticketId, "Saved ticket ID was not assigned");

        Optional<Ticket> ticketById = ticketDao.findById(ticketId);
        assertNotNull(ticketById.get(), "Saved ticket was not found by ID");

        int newCost = 777;
        ticket.setCost(newCost);
        ticketDao.update(ticket);

        Optional<Ticket> updatedTicket = ticketDao.findById(ticketId);
        assertEquals(updatedTicket.get().getCost(), newCost);

        assertDoesNotThrow(() -> ticketDao.remove(ticket), "Delete method throws exception");

        assertEquals(ticketDao.findById(ticketId), Optional.empty(),
                "Ticket that was deleted by ID found in database");
    }

    @Test
    public void getAllTicketsTest() {
        List<Ticket> tickets = ticketDao.getAll();
        int expectedTicketsCount = 55;

        assertEquals(expectedTicketsCount, tickets.size()); // very simplified
    }

    private static Ticket generateTicket() {
        return Ticket.builder()
                .passportNo("123")
                .passengerName("Ivan")
                .flightId(1L)
                .seatNo("Z55")
                .cost(134)
                .build();
    }
}