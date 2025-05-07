package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountRepository repo;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "discounts/create";
        }
        repo.save(formDiscount);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("discount", repo.findById(id).get());
        return "discounts/edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "discounts/edit";
        }
        repo.save(formDiscount);
        return "redirect:/pizzas/" + formDiscount.getPizza().getId();
    }

}
