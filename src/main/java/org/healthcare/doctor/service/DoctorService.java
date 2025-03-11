package org.healthcare.doctor.service;

import java.util.List;

import org.healthcare.doctor.entity.Doctor;
import org.healthcare.doctor.exception.DoctorNotFoundException;

public interface DoctorService {

	public List<Doctor> getAll();
	
	public Doctor getById(int id) throws DoctorNotFoundException;
	
	public Doctor create(Doctor doctor);
	
	public void deleteById(int id);
}
