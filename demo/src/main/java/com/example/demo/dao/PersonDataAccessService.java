package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        return jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)",
                id, person.getFirstName(), person.getLastName(), person.getAge());
    }

    @Override
    public List<Person> selectAllPeople() {
        return jdbcTemplate.query("SELECT * FROM person", ((resultSet, i) ->{
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String fName = resultSet.getString("firstName");
                    String lName = resultSet.getString("lastName");
                    int age = resultSet.getInt("age");
                    return new Person(id, fName, lName, age);
        }));
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        Person person = jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?", new Object[]{id}, ((resultSet, i) ->{
            UUID personId = UUID.fromString(resultSet.getString("id"));
            String fName = resultSet.getString("firstName");
            String lName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");
            return new Person(personId, fName, lName, age);
        }));
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        /*Optional<Person> personExists = selectPersonById(id);
        if(personExists.isEmpty()){
            return 0;
        }else{
            jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
            return 1;
        }*/
        return jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    @Override
    public int updatePersonById(UUID id, Person updatedPerson) {
        return jdbcTemplate.update("UPDATE person SET firstName = ?, lastName = ?, age = ? WHERE ID = ?",
                updatedPerson.getFirstName(), updatedPerson.getLastName(), updatedPerson.getAge(), id);
    }
}
