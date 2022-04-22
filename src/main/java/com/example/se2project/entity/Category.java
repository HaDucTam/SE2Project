package com.example.se2project.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriesId;

    @Length(min = 2, max = 30)
    private String name;
    @Length(min = 2, max = 50)
    private String description;
    @NotEmpty(message = "Image can not be empty")
    @Column(length = Integer.MAX_VALUE, nullable = false)
    private String image;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<Product>();

    public void addProduct(Product product) {
        product.setCategory(this);
        products.add(product);
    }
    @Transient
    public String getImagePath() {
        if(image == null || categoriesId == 0) {
            return null;
        }
        return "/product-image/" + categoriesId + "/" + image;
    }
}
