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
public interface PersonDAOInterface {

    public Person getPersonById(int id);
    
    public Person getPersonByName(String personName);

    public List<Person> getPersons();

    public int insertPerson(Person person);

    public int updatePersonById(int id, Person updatedPerson);

    public int deletePersonById(int id);
}
