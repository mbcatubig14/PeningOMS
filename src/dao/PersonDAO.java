/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Person;

/**
 *
 * @author Muhammad
 */
public abstract class PersonDAO implements PersonDAOInterface {

    @Override
    public abstract Person getPersonById(int id);

    @Override
    public abstract Person getPersonByName(String name);

    @Override
    public abstract List<Person> getPersons();

    @Override
    public abstract int insertPerson(Person person);

    @Override
    public abstract int updatePersonById(int id, Person updatedPerson);

    @Override
    public abstract int deletePersonById(int id);

}
