package com.tech.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.DateConversion;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class RestaurantDetails  implements Serializable {

    private String sessionId;
    private String[] hotelNames;
    private Date submtTme;

}
