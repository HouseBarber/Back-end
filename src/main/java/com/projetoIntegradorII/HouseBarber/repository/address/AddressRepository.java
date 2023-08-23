package com.projetoIntegradorII.HouseBarber.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.address.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
