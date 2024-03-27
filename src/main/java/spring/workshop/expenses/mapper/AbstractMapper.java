package spring.workshop.expenses.mapper;

import java.util.List;

public interface AbstractMapper<T, TDTO> {

    TDTO toDto(T object);

    T toEntity(TDTO objectDTO);

    List<TDTO> toDto(List<T> objects);

    List<T> toEntity(List<TDTO> objectDTOs);
}
