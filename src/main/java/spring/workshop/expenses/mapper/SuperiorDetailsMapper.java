package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.SuperiorDetailsDTO;
import spring.workshop.expenses.entities.Superior;

@Mapper(componentModel = "spring")
public interface SuperiorDetailsMapper extends AbstractMapper<Superior, SuperiorDetailsDTO> {
}
