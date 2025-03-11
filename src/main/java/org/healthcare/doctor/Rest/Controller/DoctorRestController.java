package org.healthcare.doctor.Rest.Controller;

import java.util.List;

import org.healthcare.appointment.dto.Appointment;
import org.healthcare.doctor.entity.Doctor;
import org.healthcare.doctor.exception.DoctorNotFoundException;
import org.healthcare.doctor.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {

	@Autowired
	private DoctorServiceImpl service;
	
	@ResponseStatus(code=HttpStatus.OK)
	@GetMapping
	public List<Doctor> findAll(){
		return service.getAll();
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@GetMapping("/{id}")
	public Doctor getById(@PathVariable int id) throws DoctorNotFoundException {
		return service.getById(id);
	}
	
	@ResponseStatus(code=HttpStatus.CREATED)
	@PostMapping
	public Doctor registerNewDoctor(@RequestBody Doctor doctor) {
		return service.create(doctor);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void DeleteById(@PathVariable int id) {
		service.deleteById(id);
	}
	
//	1. Fetch list of all appointments for one doctor
	@GetMapping(value="/doctorAppointments" , params="doctorId")
	public List<Appointment> getAppointmentsForOneDoctor(@RequestParam int doctorId) {
		
//		List<Appointment> a1 = RestClient.create("http://localhost:8092/api/appointment?doctorId="+doctorId)
//				.get()
//				.retrieve()
//				.body(Appointment.class);
		
		List<Appointment> a1 = RestClient.builder()
				.baseUrl("http://localhost:8092/api/appointment")
				.build()
				.get()
				.uri("?doctorId="+doctorId)
				.retrieve()
				.body(new ParameterizedTypeReference<List<Appointment>>() {});
		return a1;
		
	}
	
	
	@ExceptionHandler
	public ProblemDetail HandleDoctorNotFoundException(DoctorNotFoundException e) {
		System.out.println(e.getMessage());
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		detail.setTitle("Doctor Not Found.");
		return detail;
	}
}
