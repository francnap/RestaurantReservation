package com.restaurant.RestaurantReservation.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class StaticModelMapper {
	
	private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
    }

    public static <T, U> U convert(T source, Class<U> destinationType) {
        return modelMapper.map(source, destinationType);
    }
    
    public static <T, U> List<U> convertList(List<T> sourceList, Class<U> destinationType) {
        return sourceList.stream()
                .map(source -> convert(source, destinationType))
                .collect(Collectors.toList());
    }
}