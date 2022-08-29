package org.darius.repository;

import org.darius.entity.flight.Seat;
import org.darius.entity.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {


}
