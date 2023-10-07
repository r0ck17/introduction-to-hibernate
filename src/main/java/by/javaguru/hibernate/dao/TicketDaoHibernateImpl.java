package by.javaguru.hibernate.dao;

import by.javaguru.hibernate.entity.Ticket;
import by.javaguru.hibernate.exception.DaoException;
import by.javaguru.hibernate.util.ConnectionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TicketDaoHibernateImpl implements Dao<Ticket, Long> {
    private final SessionFactory sessionFactory = ConnectionManager.getSessionFactory();
    private static final TicketDaoHibernateImpl INSTANCE = new TicketDaoHibernateImpl();

    private TicketDaoHibernateImpl() {

    }
    @Override
    public void save(Ticket ticket) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(ticket);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void remove(Ticket ticket) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(ticket);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Ticket ticket) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(ticket);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).getResultList();
            session.getTransaction().commit();

            return tickets;
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long key) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, key);
            session.getTransaction().commit();

            return Optional.ofNullable(ticket);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDaoHibernateImpl getInstance() {
        return INSTANCE;
    }
}
