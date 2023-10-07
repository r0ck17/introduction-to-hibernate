package by.javaguru.jdbc;

import by.javaguru.jdbc.entity.Ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Тренировка для понимания принципа формирования запросов в Hibernate с помощью рефлексии
 */
public class QueryGeneratorRunner {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/flight_repo?currentSchema=flights";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_GROOT = "groot";

    public static void main(String[] args) {
        Ticket ticket = Ticket.builder()
                .passportNo("123")
                .passengerName("Ivan")
                .flightId(1L)
                .seatNo("Z66")
                .cost(134)
                .build();

        String insertSql = """
                INSERT INTO %s
                (%s)
                VALUES
                (%s)
                """;

        String tableName = Optional.ofNullable(Ticket.class.getAnnotation(Table.class))
                .map(table -> table.name())
                .orElse(ticket.getClass().getName());

        Field[] fields = ticket.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(fields)
                .filter(field -> !("id".equals(field.getName())))
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(fields)
                .filter(field -> !("id".equals(field.getName())))
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        String select = insertSql.formatted(tableName, columnNames, columnValues);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_GROOT);
             PreparedStatement statement = connection.prepareStatement(select)) {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i, fields[i].get(ticket));
            }
            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
