package spring.workshop.expenses.mapper;

import org.mapstruct.Mapper;

import spring.workshop.expenses.dto.ShopDTO;
import spring.workshop.expenses.entities.Shop;

@Mapper(componentModel = "spring")
public interface ShopMapper extends AbstractMapper<Shop, ShopDTO> {
}
