package my.spring.controller;

import my.spring.Person;
import my.spring.PersonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

/**
 * Демонтсрация типичного контроллера, которые обрабатывает запросы, по урлу "/путь диспетчера/persons/*"
 * Если ну указывать в контроллере путь, то все пути RequestMapping, будут абсолютными к п диспетчеру, если не
 * указать путь в RequestMapping
 */
@Controller("/persons")
public class PersonController {

    @Inject
    PersonService personService;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Person> get() {
        return personService.getPersonsByName();
    }

    @RequestMapping(value="/{day}", method = RequestMethod.GET)
    public Map<Date, Person> getForDay(@PathVariable @DateTimeFormat(iso= DATE) Date day, Model model) {
        return personService.getPersonsByBithDay();
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public Person getNewForm() {
        return new Person();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "appointments/new";
        }
        personService.addNewPerson( person );
        return "redirect:/appointments";
    }
}
