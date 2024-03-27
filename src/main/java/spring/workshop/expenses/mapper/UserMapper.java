package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.UserDTO;
import spring.workshop.expenses.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserDTO> {
}
