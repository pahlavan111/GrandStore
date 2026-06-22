package org.bpf.grandstore.repository;

import org.bpf.grandstore.entity.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}