package com.hireconnect.adapters.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    public static <T> void mapNonNullProperties(T source, T target) {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(source, target);
    }
}
