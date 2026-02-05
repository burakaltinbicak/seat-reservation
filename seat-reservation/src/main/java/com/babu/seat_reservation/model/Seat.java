package com.babu.seat_reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Bu sınıfın bir Veritabanı Tablosu olduğunu söyler
@Table(name = "seats") // Tablonun adı "seats" olsun
@Data // Lombok: Getter, Setter, ToString hepsini otomatik yazar
@NoArgsConstructor // Boş constructor üretir
@AllArgsConstructor // Dolu constructor üretir
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Koltuğun benzersiz kimliği (1, 2, 3...)

    @Column(nullable = false)
    private String seatNumber; // Koltuk Numarası (A1, B3, C5 gibi)

    @Column(nullable = false)
    private int rowNum; // Sıra numarası (Ön arka hesabı için)

    @Column(nullable = false)
    private int colNum; // Sütun numarası (Yan yana dizilim için)

    private double price; // Koltuğun fiyatı

    private boolean isReserved = false; // Dolu mu boş mu? (Varsayılan: Boş)

    private String reservedBy; // Kim rezerve etti? (İsim yazacağız)
}