package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<SuperiorDetailsDTO>> getAllSuperiors() {
        return new ResponseEntity<>(superiorDetailsMapper.toDto(superiorService.getAllSuperiors()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperiorDetailsDTO> getSuperior(@PathVariable Long id) {
        return new ResponseEntity<>(superiorDetailsMapper.toDto(superiorService.getSuperiorById(id)), HttpStatus.OK);
    }

}
