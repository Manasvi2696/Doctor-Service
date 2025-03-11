package org.healthcare.doctor.exception;

public class DoctorNotFoundException extends Exception{

	public DoctorNotFoundException(int id) {
		super("Doctor with id :"+id+" Not found.");
	}
}
