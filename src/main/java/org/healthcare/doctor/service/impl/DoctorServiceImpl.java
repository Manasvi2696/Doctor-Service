package org.healthcare.doctor.service.impl;

import java.util.List;
import java.util.Optional;

import org.healthcare.doctor.dao.DoctorDao;
import org.healthcare.doctor.entity.Doctor;
import org.healthcare.doctor.exception.DoctorNotFoundException;
import org.healthcare.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService{

	@Autowired
	private DoctorDao dao;
	
	@Override
	public List<Doctor> getAll() {
		return dao.findAll();
	}

	@Override
	public Doctor getById(int id) throws DoctorNotFoundException{
		Optional<Doctor> o = dao.findById(id);
		if(o.isPresent()) {
			return o.get();
		}
		throw new DoctorNotFoundException(id);
	}

	@Override
	public Doctor create(Doctor doctor) {
		return dao.save(doctor);
	}

	@Override
	public void deleteById(int id) {
		dao.deleteById(id);
	}

}
