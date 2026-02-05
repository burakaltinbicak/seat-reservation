package com.babu.seat_reservation.controller;

import com.babu.seat_reservation.model.Seat;
import com.babu.seat_reservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SeatController {

    @Autowired
    private SeatService seatService;

    // 1. Ana Sayfa (Koltuk Haritası)
    @GetMapping("/")
    public String showSeatMap(Model model) {
        model.addAttribute("seats", seatService.getAllSeats());
        return "index";
    }

    // 2. Rezervasyon Yap (Değişti!)
    @PostMapping("/reserve")
    public String reserveSeat(@RequestParam("seatNumber") String seatNumber, 
                              @RequestParam("customerName") String customerName,
                              RedirectAttributes redirectAttributes) { // Model yerine bunu kullanıyoruz
        
        String result = seatService.reserveSeat(seatNumber, customerName);

        // Eğer hata varsa ana sayfada kalsın
        if (result.startsWith("Hata")) {
            redirectAttributes.addFlashAttribute("message", result);
            return "redirect:/";
        }

        // Başarılıysa Bilet Sayfasına git (Verileri oraya taşı)
        // URL şöyle olacak: /ticket?seatNumber=A1&customerName=Babu
        return "redirect:/ticket?seatNumber=" + seatNumber.toUpperCase().trim() + "&customerName=" + customerName;
    }

    // 3. YENİ: Bilet Gösterme Sayfası
    @GetMapping("/ticket")
    public String showTicket(@RequestParam("seatNumber") String seatNumber,
                             @RequestParam("customerName") String customerName,
                             Model model) {
        
        // Bilet bilgilerini sayfaya gönder
        model.addAttribute("seatNumber", seatNumber);
        model.addAttribute("customerName", customerName);
        model.addAttribute("movieName", "Matrix: The Simulation"); // Film adı sabit olsun şimdilik
        model.addAttribute("price", "100 TL");
        
        return "ticket"; // ticket.html dosyasını açacak
    }
}