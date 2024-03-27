package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.SuperiorDTO;
import spring.workshop.expenses.entities.Superior;

@Mapper(componentModel = "spring")
public interface SupriorMapper extends AbstractMapper<Superior, SuperiorDTO> {
}
