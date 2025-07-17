package com.example.VaccinationCenter.Response;

import java.util.List;

import com.example.VaccinationCenter.Entity.VaccinationCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationCenterVM {
	
	private VaccinationCenter vaccinationCenter;
	
	private List<Citizen> citizenList;

}
