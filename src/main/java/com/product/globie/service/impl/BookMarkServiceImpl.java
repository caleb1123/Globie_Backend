package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.Bookmark;
import com.product.globie.entity.Product;
import com.product.globie.payload.DTO.BookMarkDTO;
import com.product.globie.payload.DTO.CommentDTO;
import com.product.globie.repository.BookMarkRepository;
import com.product.globie.repository.ProductRepository;
import com.product.globie.service.BookMarkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMarkServiceImpl implements BookMarkService {
    @Autowired
    BookMarkRepository bookMarkRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<BookMarkDTO> getAllBookMarkOfUser(int uId) {
        List<Bookmark> bookmarks = bookMarkRepository.getBookmarkByUser(uId);

        List<BookMarkDTO> bookMarkDTOS = bookmarks.stream()
                .map(bookmark -> {
                    BookMarkDTO bookMarkDTO = mapper.map(bookmark, BookMarkDTO.class);

                    if (bookmark.getProduct() != null) {
                        bookMarkDTO.setProductId(bookmark.getProduct().getProductId());
                    }
                    if (bookmark.getUser() != null) {
                        bookMarkDTO.setUserId(bookmark.getUser().getUserId());
                    }

                    return bookMarkDTO;
                })
                .collect(Collectors.toList());

        return bookMarkDTOS.isEmpty() ? null : bookMarkDTOS;
    }

    @Override
    public void addBookMark(int pId) {
        Bookmark bookmark = new Bookmark();

        Product product = productRepository
                .findById(pId).orElseThrow(() -> new RuntimeException("Product not found!"));
        bookmark.setProduct(product);
        bookmark.setUser(util.getUserFromAuthentication());
        bookmark.setCreatedTime(new Date());

        bookMarkRepository.save(bookmark);
    }

    @Override
    public void deleteBookMark(int bId) {
        Bookmark bookmark = bookMarkRepository.findById(bId)
                .orElseThrow(() -> new RuntimeException("Bookmark not found!"));

        bookMarkRepository.delete(bookmark);
    }
}
