package springBoot.study.blog.services;

import springBoot.study.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
     CategoryDto addCategory(CategoryDto categoryDto);
     CategoryDto getCategoryById(long id);
     List<CategoryDto> getAllCategories() ;
     CategoryDto updateCategory(CategoryDto categoryDto , long id) ;
     void deleteCategory(long id) ;
}
