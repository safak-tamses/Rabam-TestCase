package com.example.dockerversion.repository;

import com.example.dockerversion.model.Package;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("packageRepository")
public interface PackageRepository extends JpaRepository<Package, Long> {
}
