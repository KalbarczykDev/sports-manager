package dev.kalbarczyk.sportsmanager.common.controller;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
public abstract class AbstractCrudController<T extends BaseEntity> {

    protected abstract CrudService<T> getBaseService();

    protected abstract String getEntityNameSingular();

    protected abstract String getEntityNamePlural();

    protected abstract void prepareFormModel(final Model model, final T entity, final String formAction, final String title);

    protected abstract void validateEntity(final T entity, final BindingResult bindingResult);

    protected abstract void addOptionalContentToModelInSHowView(final T entity, final Model model);

    protected abstract T createNewInstance();


    @GetMapping
    public String index(final Model model, final @RequestParam(name = "page", defaultValue = "0") int page, final @RequestParam(name = "size", defaultValue = "30") int size, final @RequestParam(name = "sortBy", defaultValue = "id") String sortBy, final @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        val pageResult = getBaseService().findAll(page, size, sortBy, sortDir);
        model.addAttribute(getEntityNamePlural(), pageResult.getContent());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("view", "modules/" + getEntityNameSingular() + "/index");
        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        return "layout/layout";
    }

    @GetMapping("{id}")
    public String show(final @PathVariable Long id, final Model model) {
        val entity = getBaseService().findById(id);
        model.addAttribute(getEntityNameSingular(), entity);
        model.addAttribute("view", "modules/" + getEntityNameSingular() + "/show");
        addOptionalContentToModelInSHowView(entity, model);
        return "layout/layout";
    }

    @PostMapping({"", "/{id}"})
    public String save(final @PathVariable(required = false) Long id, final @ModelAttribute T entity, final BindingResult bindingResult, final Model model) {

        validateEntity(entity, bindingResult);

        if (bindingResult.hasErrors()) {
            String formAction = (id == null) ? "/" + getEntityNamePlural() : "/" + getEntityNamePlural() + "/" + id;
            String title = (id == null ? "New " : "Edit ") + getEntityNameSingular();
            prepareFormModel(model, entity, formAction, title);
            return "layout/layout";
        }

        if (id == null) {
            getBaseService().save(entity);
        } else {
            getBaseService().update(id, entity);
        }

        return "redirect:/" + getEntityNamePlural();
    }

    @GetMapping("/new")
    public String showNewForm(final Model model) {
        prepareFormModel(model, createNewInstance(), "/" + getEntityNamePlural(), "New " + getEntityNameSingular());
        return "layout/layout";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(final @PathVariable Long id, final Model model) {
        prepareFormModel(model, getBaseService().findById(id), "/" + getEntityNamePlural() + "/" + id, "Edit " + getEntityNameSingular());
        return "layout/layout";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(final @PathVariable Long id) {
        getBaseService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
