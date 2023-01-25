package br.com.lucio.person.infra.controller;

import br.com.lucio.person.application.dto.PersonDTO;
import br.com.lucio.person.application.service.PersonService;
import br.com.lucio.person.infra.event.dto.PersonCreatedDTO;
import br.com.lucio.person.infra.event.dto.PersonUpdatedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO dto, UriComponentsBuilder uriBuilder) {
        PersonDTO personCreated = personService.create(dto);
        URI uri = uriBuilder.path("/person/{id}").buildAndExpand(personCreated.getId()).toUri();
        publisher.publishEvent(new PersonCreatedDTO(personCreated));
        return ResponseEntity.created(uri).body(personCreated);
    }


}
