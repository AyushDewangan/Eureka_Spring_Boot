package com.example.CitizenService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.CitizenService.Entity.Citizen;
import com.example.CitizenService.Repository.CitizenRepository;

import java.util.*;

@RestController
@RequestMapping("/citizen")
public class CitizenController {
	
	@Autowired
	private CitizenRepository citizenRepository;
	
	@RequestMapping(path = "/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>("Hello", HttpStatus.OK);
	}
	
	@RequestMapping(path = "/vaccinCenterId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Citizen>> getById(@PathVariable("id") int id){
		return new ResponseEntity<>(citizenRepository.findByVaccinationCenterId(id), HttpStatus.OK);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Citizen> postMethodName(@RequestBody Citizen citizen) {
		return new ResponseEntity<>(citizenRepository.save(citizen), HttpStatus.OK);
	}
	

}
