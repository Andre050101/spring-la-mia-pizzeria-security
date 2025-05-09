package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.service.DiscountService;
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
    private DiscountService discService;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "discounts/create";
        }
        discService.create(formDiscount);
        return "redirect:/pizzas/" + formDiscount.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("discount", discService.getById(id));
        return "discounts/edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "discounts/edit";
        }
        discService.edit(formDiscount);
        return "redirect:/pizzas/" + formDiscount.getPizza().getId();
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Discount discountToDelete = discService.getById(id);
        Integer pizzaId = discountToDelete.getPizza().getId();
        discService.delete(discountToDelete);
        return "redirect:/pizzas/" + pizzaId;
    }

}
