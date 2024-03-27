package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import spring.workshop.expenses.dto.SuperiorDetailsDTO;
import spring.workshop.expenses.mapper.SuperiorDetailsMapper;
import spring.workshop.expenses.services.SuperiorService;

@RestController
@RequestMapping(path = "/superiors")
public class SuperiorController {

    @Autowired
    private SuperiorService superiorService;

    @Autowired
    private SuperiorDetailsMapper superiorDetailsMapper;

    @GetMapping
    @Operation(summary = "Get all superiors", description = "Fetches all superiors from the database")
    public ResponseEntity<List<SuperiorDetailsDTO>> getAllSuperiors() {
        return new ResponseEntity<>(superiorDetailsMapper.toDto(superiorService.getAllSuperiors()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a superior", description = "Fetche the superior with the given id from the database")
    public ResponseEntity<SuperiorDetailsDTO> getSuperior(
            @PathVariable @Parameter(description = "ID of the superior") Long id) {
        return new ResponseEntity<>(superiorDetailsMapper.toDto(superiorService.getSuperiorById(id)), HttpStatus.OK);
    }

}
