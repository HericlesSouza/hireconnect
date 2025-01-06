package com.hireconnect.core.utils;


import com.hireconnect.core.exception.BusinessException;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils() {
        //Construtor privado para evitar instanciação.
    }

    public static UUID fromString(String uuid) {
        try {
           return UUID.fromString(uuid);
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Invalid UUID format: " + uuid);
        }
    }
}
