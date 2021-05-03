package com.example.olisaude.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectResponse<T, Long> {

    private Long id;
    private T object;

    public ObjectResponse(T object, Long id){
        this.object = object;
        this.id = id;
    }

}
