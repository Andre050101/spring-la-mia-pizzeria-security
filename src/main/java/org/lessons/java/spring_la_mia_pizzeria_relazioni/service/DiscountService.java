package org.lessons.java.spring_la_mia_pizzeria_relazioni.service;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.exception.NotFoundException;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepo;

    public Discount create(Discount discount) {
        return discountRepo.save(discount);
    }

    public Discount getById(Integer id) {
        return discountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sconto con ID " + id + " non trovato."));
    }

    public Discount edit(Discount discount) {
        if (!discountRepo.existsById(discount.getId())) {
            throw new NotFoundException("Discount con id" + discount.getId() + " non trovato");
        }
        return create(discount);
    }

    public void delete(Discount disc) {
        discountRepo.deleteById(disc.getId());
    }

}
