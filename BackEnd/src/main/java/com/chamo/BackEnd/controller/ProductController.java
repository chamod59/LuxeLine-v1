package com.chamo.BackEnd.controller;

import com.chamo.BackEnd.dto.product.ProductReqDto;
import com.chamo.BackEnd.dto.product.ProductResDto;
import com.chamo.BackEnd.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<ProductResDto> createProduct(@RequestBody ProductReqDto productData){
//        ProductResDto res = productService.addProduct(productData);
//        if(res.getError()!=null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(res);
//    }

    @PostMapping("/create")
    public ResponseEntity<ProductResDto> createProduct(@Valid @RequestBody ProductReqDto productData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ProductResDto(null, String.join(", ", errors)));
        }

        ProductResDto res = productService.addProduct(productData);
        if(res.getError()!=null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
