package com.babu.seat_reservation.repository;

import com.babu.seat_reservation.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring'e bunun bir depo olduğunu söyler
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Özel sorgularımızı buraya yazarız (Spring otomatik anlar)

    // 1. Dolu olan koltukları getir
    List<Seat> findByIsReservedTrue();

    // 2. Boş olan koltukları getir
    List<Seat> findByIsReservedFalse();

    // 3. Numarasına göre koltuk bul (Örn: A1)
    Seat findBySeatNumber(String seatNumber);
}