package com.example.doctor_service.service;

import com.example.doctor_service.entity.Doctor;
import com.example.doctor_service.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    // Save new doctor
    public Doctor saveDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    // Get doctor by ID
    public Optional<Doctor> getDoctorById(Long id) {
        return repository.findById(id);
    }

    // Update doctor
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor existingDoctor = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        existingDoctor.setName(doctorDetails.getName());
        existingDoctor.setSpecialization(doctorDetails.getSpecialization());
        existingDoctor.setContactNumber(doctorDetails.getContactNumber());
        // Update other fields if your Doctor entity has more

        return repository.save(existingDoctor);
    }

    // Delete doctor safely
    public void deleteDoctor(Long id) {
        Doctor existingDoctor = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        repository.delete(existingDoctor);
    }
}
