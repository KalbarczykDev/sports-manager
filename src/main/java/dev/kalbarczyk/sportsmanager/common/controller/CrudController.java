package dev.kalbarczyk.sportsmanager.common.controller;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import dev.kalbarczyk.sportsmanager.common.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Generic controller providing CRUD operations for entities extending {@link BaseEntity}.
 *
 * @param <T> the type of the entity
 */
@Slf4j
public abstract class CrudController<T extends BaseEntity> {

    /**
     * Returns the service responsible for CRUD operations on the entity.
     *
     * @return the {@link CrudService} for the entity
     */
    protected abstract CrudService<T> getBaseService();

    /**
     * Returns the singular name of the entity, used for model attributes and views.
     *
     * @return singular entity name
     */
    protected abstract String getEntityNameSingular();

    /**
     * Returns the plural name of the entity, used for model attributes and views.
     *
     * @return plural entity name
     */
    protected abstract String getEntityNamePlural();

    /**
     * Prepares the model for the entity form view.
     *
     * @param model      the model to populate
     * @param entity     the entity instance to bind to the form
     * @param formAction the URL the form should submit to
     * @param title      the title for the form page
     */
    protected abstract void prepareFormModel(final Model model, final T entity, final String formAction, final String title);

    /**
     * Validates the entity before saving or updating.
     *
     * @param entity        the entity to validate
     * @param bindingResult the result holder for validation errors
     */
    protected abstract void validateEntity(final T entity, final BindingResult bindingResult);

    /**
     * Adds optional content to the model for the show view.
     *
     * @param entity the entity to display
     * @param model  the model to populate
     */
    protected abstract void addOptionalContentToModelInSHowView(final T entity, final Model model);

    /**
     * Creates a new instance of the entity.
     *
     * @return a new entity instance
     */
    protected abstract T createNewInstance();


    /**
     * Displays a paginated list of entities.
     *
     * @param model   the model to add attributes to
     * @param page    page number
     * @param size    page size
     * @param sortBy  field to sort by
     * @param sortDir sort direction (asc/desc)
     * @return the layout view name
     */
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

    /**
     * Displays a single entity by ID.
     *
     * @param id    the entity ID
     * @param model the model to add attributes to
     * @return the layout view name
     */
    @GetMapping("{id}")
    public String show(final @PathVariable Long id, final Model model) {
        val entity = getBaseService().findById(id);
        model.addAttribute(getEntityNameSingular(), entity);
        model.addAttribute("view", "modules/" + getEntityNameSingular() + "/show");
        addOptionalContentToModelInSHowView(entity, model);
        return "layout/layout";
    }

    /**
     * Saves a new entity or updates an existing one.
     *
     * @param id            optional entity ID (null for new entity)
     * @param entity        the entity to save or update
     * @param bindingResult result of validation
     * @param model         the model to add attributes to in case of errors
     * @return redirect to the entity list or the layout view if errors occurred
     */
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

    /**
     * Shows the form for creating a new entity.
     *
     * @param model the model to add attributes to
     * @return the layout view name
     */
    @GetMapping("/new")
    public String showNewForm(final Model model) {
        prepareFormModel(model, createNewInstance(), "/" + getEntityNamePlural(), "New " + getEntityNameSingular());
        return "layout/layout";
    }

    /**
     * Shows the form for editing an existing entity.
     *
     * @param id    the entity ID
     * @param model the model to add attributes to
     * @return the layout view name
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(final @PathVariable Long id, final Model model) {
        prepareFormModel(model, getBaseService().findById(id), "/" + getEntityNamePlural() + "/" + id, "Edit " + getEntityNameSingular());
        return "layout/layout";
    }

    /**
     * Deletes an entity by ID.
     *
     * @param id the entity ID
     * @return response entity with no content
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(final @PathVariable Long id) {
        getBaseService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
