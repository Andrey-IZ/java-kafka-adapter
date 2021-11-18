package com.sber.javaschool.finalproject.crud.controller;

import com.sber.javaschool.finalproject.crud.exception.ResourceNotFound;
import com.sber.javaschool.finalproject.crud.persistance.model.CrudEntity;
import com.sber.javaschool.finalproject.crud.persistance.repository.CrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/crud")
public class CrudController {
    private final CrudRepository repository;

    public CrudController(CrudRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/count")
    @ResponseStatus(value = HttpStatus.OK)
    long count() {
        return repository.count();
    }

    @GetMapping("readall")
    List<CrudEntity> readAll() {
        return repository.findAll();
    }

    @GetMapping("/read/{id}")
    CrudEntity read(@PathVariable("id") final Long id) throws ResourceNotFound {
        return getCrudEntity(id);
    }

    @GetMapping("/read")
    CrudEntity readWithParam(@RequestParam("id") final Long id) throws ResourceNotFound {
        return getCrudEntity(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    CrudEntity create(@RequestBody CrudEntity newCrudEntity) {
        return repository.save(newCrudEntity);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    CrudEntity updateOrSave(@PathVariable("id") Long id, @RequestBody CrudEntity crudEntity) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setTitle(crudEntity.getTitle());
                    entity.setBody(crudEntity.getBody());
                    return repository.save(entity);
                })
                .orElseGet(() -> repository.save(crudEntity));
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable("id") final Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/delete")
    void deleteAll() {
        repository.deleteAll();
    }

    @PatchMapping("/patch/{id}")
    CrudEntity updateOnly(@PathVariable("id") Long id, @RequestBody Map<String, String> update) throws ResourceNotFound {
        return repository.findById(id)
                .map(entity -> {
                    var copyCrudEntity = entity.clone();
                    try {
                        updateEntityProp(copyCrudEntity, update);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        entity = copyCrudEntity;
                    }
                    return repository.save(entity);
                })
                .orElseThrow(() -> new ResourceNotFound(MessageFormat.format("Couldn't patch the entity by id", id)));
    }

    @RequestMapping(method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.OK)
    public void head(final HttpServletResponse resp) {
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setHeader("count-enities", String.valueOf(repository.count()));
    }

    private void updateEntityProp(CrudEntity entity, Map<String, String> update) throws InvocationTargetException, IllegalAccessException {
        for (Method method : entity.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            String methodName = method.getName();
            String name = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            if (methodName.startsWith("set") && method.getParameterCount() == 1
                    && method.getParameterTypes()[0].isAssignableFrom(String.class)
                    && update.containsKey(name)) {
                method.invoke(entity, update.get(name));
            }
        }

    }

    @ExceptionHandler({ResourceNotFound.class})
    public void handleException(final Exception ex) {
        final String error = "Resource hasn't been found";
        log.error(error, ex);
    }

    private CrudEntity getCrudEntity(Long id) throws ResourceNotFound {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(MessageFormat.format("Entity with id={0} doesn't exist", id)));
    }
}
