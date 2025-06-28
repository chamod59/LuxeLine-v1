package com.chamo.BackEnd.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection ="product")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String id;

    private Double price;

    @Indexed(unique = true)
    private String title;

    private String details;
    private int qty;
    private Boolean status;

    @CreatedDate
    private Date createdAt;

    @DBRef
    private UserEntity user;

    public ProductEntity(int qty, String details, String title, Double price) {
        this.createdAt = new Date();
        this.status = false;
        this.qty = qty;
        this.details = details;
        this.title = title;
        this.price = price;
    }
}
