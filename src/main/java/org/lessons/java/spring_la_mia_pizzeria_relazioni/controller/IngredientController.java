package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.IngredientRepository;
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
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findAll());
        return "ingredients/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create";
        }
        ingredientRepo.save(formIngredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepo.findById(id).get());
        return "ingredients/edit";
    }

    @PostMapping("edit/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult binding) {
        if (binding.hasErrors()) {
            return "ingredients/edit";
        }

        ingredientRepo.save(formIngredient);
        return "redirect:/ingredients";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ingredientRepo.deleteById(id);
        return "redirect:/ingredients";
    }

}
