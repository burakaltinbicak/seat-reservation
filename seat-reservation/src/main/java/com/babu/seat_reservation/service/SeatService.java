package com.babu.seat_reservation.service;

import com.babu.seat_reservation.model.Seat;
import com.babu.seat_reservation.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bu sınıfın iş mantığı içerdiğini söyler
public class SeatService {

    @Autowired // Repository'yi buraya bağla (Enjekte et)
    private SeatRepository seatRepository;

    // Tüm koltukları getir
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    // Yeni bir koltuk oluştur (Admin paneli için lazım olacak)
    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

// Koltuk rezerve etme işlemi
    public String reserveSeat(String seatNumber, String customerName) {
        
        // 1. ÖNCE TEMİZLİK: Gelen veriyi büyüt ve boşlukları sil
        // Kullanıcı "a1 " girerse -> "A1" olur.
        String cleanedSeatNumber = seatNumber.toUpperCase().trim();

        // Veritabanında temizlenmiş halini ara
        Seat seat = seatRepository.findBySeatNumber(cleanedSeatNumber);

        if (seat == null) {
            return "Hata: '" + seatNumber + "' numaralı koltuk sistemde yok!";
        }

        if (seat.isReserved()) {
            return "Hata: " + cleanedSeatNumber + " maalesef dolu!";
        }

        // Rezervasyon işlemini yap
        seat.setReserved(true);
        seat.setReservedBy(customerName);
        seatRepository.save(seat); // Güncellemeyi kaydet

        return "Başarılı: " + cleanedSeatNumber + " nolu koltuk " + customerName + " adına ayrıldı. İyi seyirler!";
    }
    
    // Veritabanını başlangıç verileriyle doldur (Test için)
    public void initDemoData() {
        if (seatRepository.count() == 0) { // Eğer veritabanı boşsa
            // 3 Sıra x 5 Koltuk oluştur
            char[] rows = {'A', 'B', 'C'};
            for (int i = 0; i < rows.length; i++) {
                for (int j = 1; j <= 5; j++) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(rows[i] + "" + j); // A1, A2...
                    seat.setRowNum(i + 1);
                    seat.setColNum(j);
                    seat.setPrice(100.0); // Sabit fiyat
                    seat.setReserved(false);
                    seatRepository.save(seat);
                }
            }
            System.out.println("Demo verileri yüklendi: 15 Koltuk oluşturuldu.");
        }
    }

    // --- YENİ EKLENEN KISIM: İSTATİSTİK VE TEMİZLİK ---

    // 1. Dolu koltuk sayısını getir
    public long getReservedCount() {
        return seatRepository.findByIsReservedTrue().size();
    }

    // 2. Toplam hasılatı hesapla
    public double getTotalIncome() {
        List<Seat> soldSeats = seatRepository.findByIsReservedTrue();
        double total = 0;
        for (Seat seat : soldSeats) {
            total += seat.getPrice();
        }
        return total;
    }

    // 3. TÜM KOLTUKLARI BOŞALT (Reset)
    public void resetAllSeats() {
        List<Seat> allSeats = seatRepository.findAll();
        for (Seat seat : allSeats) {
            seat.setReserved(false);
            seat.setReservedBy(null);
        }
        seatRepository.saveAll(allSeats);
    }
}