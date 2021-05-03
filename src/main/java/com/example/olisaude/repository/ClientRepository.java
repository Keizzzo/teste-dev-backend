package com.example.olisaude.repository;

import com.example.olisaude.model.Client;
import com.example.olisaude.model.enums.Gender;
import com.example.olisaude.util.ObjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ClientRepository implements Dao<Client> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO CLIENT_REGISTER(NAME, BIRTHDAY, GENDER) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setDate(2, Date.valueOf(client.getBirthday()));
            ps.setString(3, client.getGender().toString());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Client update(ObjectResponse<Client, Long> clientResponse) {

        var client = find(clientResponse.getId());

        Optional.ofNullable(clientResponse.getObject())
                .ifPresent(newClient -> {

                    Optional.of(newClient.getName())
                            .ifPresent(client::setName);

                    Optional.of(newClient.getBirthday())
                            .ifPresent(client::setBirthday);

                    Optional.of(newClient.getGender())
                            .ifPresent(client::setGender);
                });

        jdbcTemplate.update("update client_register set name = ?, birthday = ?, gender = ? where id = ?",
                client.getName(),
                client.getBirthday(),
                client.getGender(),
                clientResponse.getId()
        );

        return client;
    }

    @Override
    public Client find(Long id) {
        return jdbcTemplate.queryForObject("select id, name, birthday, gender from client_register where id = ?",
                new Object[]{id},
                (rs, row) -> new Client(rs.getString("NAME"),
                        rs.getDate("BIRTHDAY").toLocalDate(),
                        Gender.fromValue(rs.getString("GENDER")),
                        null)
        );
    }

    @Override
    public List<ObjectResponse<Client, Long>> list() {

        return jdbcTemplate.query("select id, name, birthday, gender from client_register",
                (rs, row) -> new ObjectResponse<Client, Long>(new Client(rs.getString("NAME"),
                        rs.getDate("BIRTHDAY").toLocalDate(),
                        Gender.fromValue(rs.getString("GENDER")),
                        null), rs.getLong("ID"))
        );
    }
}
