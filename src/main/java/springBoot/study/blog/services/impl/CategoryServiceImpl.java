package springBoot.study.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springBoot.study.blog.dto.CategoryDto;
import springBoot.study.blog.exception.ResourceNotFoundException;
import springBoot.study.blog.models.Category;
import springBoot.study.blog.repository.CategoryRepository;
import springBoot.study.blog.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper ;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category =  modelMapper.map(categoryDto , Category.class) ;
        Category savedCategory = categoryRepository.save(category) ;
        return modelMapper.map(savedCategory , CategoryDto.class) ;
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id" , id)) ;
        return modelMapper.map(category,CategoryDto.class) ;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map((category -> modelMapper.map(category,CategoryDto.class)))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(CategoryDto requestedCategory, long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id" , id)) ;

        category.setName(requestedCategory.getName());
        category.setDescription(requestedCategory.getDescription());
        Category updatedCategory = categoryRepository.save(category) ;

        return modelMapper.map(updatedCategory,CategoryDto.class) ;

    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id" , id)) ;
        categoryRepository.delete(category) ;
    }


}
