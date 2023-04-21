package com.store.controller;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.service.DecorationService;
import com.store.service.ImageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/decorations")
public class DecorationController {

    private final DecorationService decorationService;
    private final ImageService imageService;

    public DecorationController(DecorationService service, ImageService imageService) {
        this.decorationService = service;
        this.imageService = imageService;
    }

    @RequestMapping("/new")
    public String newDecoration(Model model){
        log.info("Adding new decoration...");
        List<DecorationCategory> categories = Arrays.asList(DecorationCategory.values());
        model.addAttribute("decorationDto", new DecorationDto());
        model.addAttribute("categories", categories);
        return "decorationForm";
    }

    @PostMapping
    public String saveDecoration(@Valid @ModelAttribute DecorationDto decorationDto,
                               BindingResult bindingResult,
                               @RequestParam("imagefile") MultipartFile file){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "decorationForm";
        }

        Decoration savedDecoration = decorationService.save(decorationDto);
        imageService.saveImageFile(Long.valueOf(savedDecoration.getDecorationId()), file);

        return "redirect:/decorations";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model){
        log.info("Updating decoration...");
        List<DecorationCategory> categories = new ArrayList<>(Arrays.asList(DecorationCategory.values()));
        Decoration decoration = decorationService.findDecorationByDecorationId(id);
        model.addAttribute("decoration", decoration);
        categories.removeIf(c -> c.equals(decoration.getCategory()));
        model.addAttribute("categories", categories);

        return "decorationFormUpdate";
    }

    @PostMapping("/update")
    public String updateDecoration(@Valid @ModelAttribute Decoration decoration,
                                 BindingResult bindingResult,
                                 @RequestParam("imagefile") MultipartFile file){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "decorationFormUpdate";
        }

        Decoration savedDecoration = decorationService.save(decoration);
        imageService.saveImageFile(Long.valueOf(savedDecoration.getDecorationId()), file);


        return "redirect:/decorations";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Decoration decorationFound = decorationService.findDecorationByDecorationId(id);
        model.addAttribute("decoration", decorationFound);

        return "decorationDetails";
    }


    @GetMapping
    public String getAllProducts(@RequestParam(required = false) String category, @RequestParam(required = false) String name, @RequestParam(required = false) String order,
                                 Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        log.info("Getting decorations...");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        Page<Decoration> decorationPage = decorationService.getDecorationsBy(category, name, order, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("decorationPage", decorationPage);
        int totalPages = decorationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "decorations";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        log.info("Deleting decoration...");
        decorationService.deleteById(Long.valueOf(id));

        return "redirect:/decorations";
    }
}
