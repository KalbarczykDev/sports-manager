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

    protected abstract void prepareFormModel(Model model, T entity, String formAction, String title);

    protected abstract void validateEntity(T entity, BindingResult bindingResult);

    protected abstract T createNewInstance();


    @GetMapping
    public String index(
            final Model model,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {
        log.info("Fetching all {} sorted by {} {}", getEntityNamePlural(), sortBy, sortDir);
        val entities = getBaseService().findAll(sortBy, sortDir);
        model.addAttribute(getEntityNamePlural(), entities);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("view", "modules/" + getEntityNameSingular() + "/index");
        return "layout/layout";
    }

    @GetMapping("{id}")
    public String show(final @PathVariable Long id, final Model model) {
        log.info("Fetching {} with id {}", getEntityNameSingular(), id);
        val entity = getBaseService().findById(id);
        model.addAttribute(getEntityNameSingular(), entity);
        model.addAttribute("view", "modules/" + getEntityNamePlural() + "/show");
        return "layout/layout";
    }

    @PostMapping({"", "/{id}"})
    public String save(
            @PathVariable(required = false) Long id,
            @ModelAttribute T entity,
            BindingResult bindingResult,
            Model model) {

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
    public String showNewForm(Model model) {
        prepareFormModel(model, createNewInstance(), "/" + getEntityNamePlural(), "New " + getEntityNameSingular());
        return "layout/layout";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        prepareFormModel(model,
                getBaseService().findById(id),
                "/" + getEntityNamePlural() + "/" + id,
                "Edit " + getEntityNameSingular());
        return "layout/layout";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting {} with id {}", getEntityNameSingular(), id);
        getBaseService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
