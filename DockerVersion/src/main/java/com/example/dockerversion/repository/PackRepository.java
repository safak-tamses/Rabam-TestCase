package com.example.dockerversion.repository;

import com.example.dockerversion.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackRepository extends JpaRepository<Package, Long> {
}
