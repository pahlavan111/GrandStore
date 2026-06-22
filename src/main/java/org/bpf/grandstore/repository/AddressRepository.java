package org.bpf.grandstore.repository;

import org.bpf.grandstore.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}