package com.redhat.coolstore.model;


import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Product implements Serializable {

    private String itemId;

    private String name;

    private String desc;

    private double price;

    private String location;

    private String link;



}
