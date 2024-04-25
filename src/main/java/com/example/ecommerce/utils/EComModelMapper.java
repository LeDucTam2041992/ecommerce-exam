package com.example.ecommerce.utils;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class EComModelMapper {
    private static volatile ModelMapper modelMapper = null;

    private EComModelMapper() {
        throw new IllegalStateException("Singleton class");
    }

    public static ModelMapper getInstance() {
        ModelMapper localMapper = modelMapper;
        if (localMapper == null) {
            synchronized (EComModelMapper.class) {
                localMapper = modelMapper;
                if (localMapper == null) {
                    var mapper = new ModelMapper();
                    mapper.addConverter(new AbstractConverter<String, UUID>() {
                        protected UUID convert(String s) {
                            return UUID.fromString(s);
                        }
                    });
                    modelMapper = localMapper = mapper;
                }
            }
        }
        return localMapper;
    }
}
