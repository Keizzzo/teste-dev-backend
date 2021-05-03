package com.example.olisaude.repository;

import com.example.olisaude.model.Client;
import com.example.olisaude.model.HealthProblem;
import com.example.olisaude.model.enums.Gender;
import com.example.olisaude.model.enums.ProblemDegree;
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
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HealthProblemRepository implements Dao<HealthProblem>, SubClassesDao<HealthProblem> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(HealthProblem healthProblem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into client_health_problem(client_id, name, problem_degree) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, healthProblem.getClientId());
            ps.setString(2, healthProblem.getName());
            ps.setInt(3, Integer.parseInt(healthProblem.getProblemDegree().toString()));
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public HealthProblem update(ObjectResponse<HealthProblem, Long> healthProblemResponse) {
        var healthProblem = find(healthProblemResponse.getId());

        Optional.ofNullable(healthProblemResponse.getObject())
                .ifPresent(newHealthProblem -> {

                    Optional.of(newHealthProblem.getName())
                            .ifPresent(healthProblem::setName);

                    Optional.of(newHealthProblem.getClientId())
                            .ifPresent(healthProblem::setClientId);

                    Optional.of(newHealthProblem.getProblemDegree())
                            .ifPresent(healthProblem::setProblemDegree);
                });

        jdbcTemplate.update("update client_health_problem set client_id = ?, name = ?,  problem_degree = ? where id = 1",
                healthProblem.getClientId(),
                healthProblem.getName(),
                healthProblem.getProblemDegree().toString(),
                healthProblemResponse.getId()
        );

        return healthProblem;
    }

    @Override
    public HealthProblem find(Long id) {

        return jdbcTemplate.queryForObject("select client_id, name, problem_degree from client_health_problem where id = 1",
                new Object[]{id},
                (rs, row) -> new HealthProblem(rs.getLong("CLIENT_ID"),
                        rs.getString("NAME"),
                        ProblemDegree.fromValue(rs.getString("PROBLEM_DEGREE")))
        );
    }

    @Override
    public List<ObjectResponse<HealthProblem, Long>> list() {
        //BROKEN OPEN-CLOSED PRINCIPLE :(
        return null;
    }

    @Override
    public List<HealthProblem> listBySuperId(Long id){
        return jdbcTemplate.query("select name, problem_degree from client_health_problem where client_id = ?",
                new Object[]{id},
                (rs, row) -> new HealthProblem(id,
                        rs.getString("NAME"),
                        ProblemDegree.fromValue(rs.getString("PROBLEM_DEGREE")))
        );
    }
}
