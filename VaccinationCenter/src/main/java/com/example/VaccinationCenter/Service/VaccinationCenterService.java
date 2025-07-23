package com.example.VaccinationCenter.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.VaccinationCenter.Entity.VaccinationCenter;
import com.example.VaccinationCenter.Repository.VaccinationCenterRepository;
import com.example.VaccinationCenter.Response.Citizen;
import com.example.VaccinationCenter.Response.VaccinationCenterVM;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class VaccinationCenterService {

	@Autowired
	private VaccinationCenterRepository vaccinationCenterRepository;

	@Autowired
	private RestTemplate restTemplate;

	public VaccinationCenter saveVaccinationCenter(VaccinationCenter entity) {
		return vaccinationCenterRepository.save(entity);
	}

//	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	@CircuitBreaker(name = "vaccinationCenter", fallbackMethod = "handleCitizenDownTime")
	public VaccinationCenterVM fetchVaccinationCenterService(int id) {
		VaccinationCenterVM centerVM = new VaccinationCenterVM();
		VaccinationCenter vaccinationCenter = vaccinationCenterRepository.findById(id).get();
		centerVM.setVaccinationCenter(vaccinationCenter);

		// Fetch Citizen from CitizenService MicroService
//		 List<Citizen> citizenList = restTemplate.getForObject("http://localhost:8081/citizen/vaccinCenterId/" + id, List.class);
		List<Citizen> citizenList = restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/vaccinCenterId/" + id,
				List.class);

		centerVM.setCitizenList(citizenList);
		return centerVM;
	}
	
	public VaccinationCenterVM handleCitizenDownTime(int id, Throwable t) {
		VaccinationCenterVM centerVM = new VaccinationCenterVM();
		VaccinationCenter vaccinationCenter = vaccinationCenterRepository.findById(id).get();
		centerVM.setVaccinationCenter(vaccinationCenter);
		return centerVM;
	}
}
