package com.puthisastra.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puthisastra.rest.domain.Person;
import com.puthisastra.rest.repository.PersonRepository;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping
	public ResponseEntity<List<Person>> getAll() {
		return ResponseEntity.ok().body(personRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> getById(@PathVariable(value = "id") Long personId) {
		Optional<Person> person = personRepository.findById(personId);
		if (!person.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(person.get());
	}
	
	@PostMapping
	public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
		return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> update(@PathVariable(value = "id") Long personId, @Valid @RequestBody Person person) {
		Person personInDb = personRepository.getOne(personId);
		personInDb.setFirstname(person.getFirstname());
		personInDb.setLastname(person.getLastname());
		personInDb.setAge(person.getAge());
		return new ResponseEntity<>(personRepository.save(personInDb), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long personId) {
		if (!personRepository.existsById(personId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		personRepository.deleteById(personId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
