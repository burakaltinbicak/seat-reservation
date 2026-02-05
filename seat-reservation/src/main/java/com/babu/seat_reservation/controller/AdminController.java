package com.babu.seat_reservation.controller;

import com.babu.seat_reservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // Tüm adresler /admin ile başlayacak
public class AdminController {

    @Autowired
    private SeatService seatService;

    // Admin Panelini Göster
    @GetMapping
    public String showDashboard(Model model) {
        long totalSeats = seatService.getAllSeats().size();
        long reservedCount = seatService.getReservedCount();
        double income = seatService.getTotalIncome();

        model.addAttribute("totalSeats", totalSeats);
        model.addAttribute("reservedCount", reservedCount);
        model.addAttribute("income", income);
        
        return "admin"; // admin.html sayfasına git
    }

    // Salonu Sıfırla
    @PostMapping("/reset")
    public String resetCinema() {
        seatService.resetAllSeats();
        return "redirect:/admin"; // İşlem bitince yine admin paneline dön
    }
}