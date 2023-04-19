package com.store.mapper;

import com.store.domain.BankAccount;
import com.store.dto.BankAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountDto mapToDto(BankAccount bankAccount);
    BankAccount mapToEntity(BankAccountDto bankAccountDto);
}
