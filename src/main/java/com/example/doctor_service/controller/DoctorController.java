package com.example.doctor_service.controller;

import com.example.doctor_service.entity.Doctor;
import com.example.doctor_service.service.DoctorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(service.saveDoctor(doctor));
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(service.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return service.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update doctor by ID
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        return service.getDoctorById(id)
                .map(existingDoctor -> ResponseEntity.ok(service.updateDoctor(id, doctorDetails)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete doctor by ID (already exists, but improved with existence check)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        Optional<Doctor> existingDoctor = service.getDoctorById(id);
        if (existingDoctor.isPresent()) {
            service.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
