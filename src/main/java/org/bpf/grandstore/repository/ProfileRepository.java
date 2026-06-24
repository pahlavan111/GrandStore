package org.bpf.grandstore.repository;

import org.bpf.grandstore.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}