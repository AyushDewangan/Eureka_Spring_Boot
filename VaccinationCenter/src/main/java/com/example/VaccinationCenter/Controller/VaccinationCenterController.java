package com.example.VaccinationCenter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VaccinationCenter.Entity.VaccinationCenter;
import com.example.VaccinationCenter.Response.VaccinationCenterVM;
import com.example.VaccinationCenter.Service.VaccinationCenterService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {
	
	@Autowired
	private VaccinationCenterService vaccinationCenterService;
	
	@PostMapping("/add")
	public ResponseEntity<VaccinationCenter> postMethodName(@RequestBody VaccinationCenter entity) {		
		return new ResponseEntity<>(vaccinationCenterService.saveVaccinationCenter(entity), HttpStatus.CREATED);
	}
	
	@GetMapping("/vaccinationcenterid/{id}")
	public ResponseEntity<VaccinationCenterVM> fetchVaccinationById(@PathVariable("id") int id){
		return new ResponseEntity<>(vaccinationCenterService.fetchVaccinationCenterService(id), HttpStatus.OK);
		
	}
	

}
