package com.gcu.cst323activity.controller;

import com.gcu.cst323activity.model.Product;
import com.gcu.cst323activity.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
            return "redirect:/products";
        }
        model.addAttribute("product", existingProduct.get());
        return "product-form";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }
        return "redirect:/products";
    }
}
