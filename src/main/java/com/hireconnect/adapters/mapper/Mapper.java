package com.hireconnect.adapters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper;

    /**
     * Método genérico para mapear qualquer entidade para qualquer DTO.
     *
     * @param source      O objeto de origem (entidade ou DTO).
     * @param targetClass A classe do tipo de destino (DTO ou entidade).
     * @param <S>         Tipo de origem.
     * @param <T>         Tipo de destino.
     * @return O objeto convertido para o tipo de destino.
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
