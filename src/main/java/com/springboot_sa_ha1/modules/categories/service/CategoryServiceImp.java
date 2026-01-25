package com.springboot_sa_ha1.modules.categories.service;

import com.springboot_sa_ha1.modules.categories.dto.CategoryWithProductsResponse;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.categories.dto.CategoryRequest;
import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.categories.mapper.CategoryMapper;
import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.service.CategoryService;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

  private final CategoryRepository repository;
  private final CategoryMapper mapper;

  public CategoryServiceImp(CategoryRepository repository, CategoryMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<CategoryResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryResponse obtenerPorId(Long id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
  }

  public List<CategoryWithProductsResponse> listarCategoriasConProductosPorSlug(List<String> slugs) {

    // Normalización de slugs
    List<String> normalizedSlugs = slugs.stream()
        .map(String::toLowerCase)
        .map(String::trim)
        .map(s -> s.replace("-", "_"))
        .toList();

    // Traemos las categorías con productos usando EntityGraph
    List<Category> categories = repository.findAllBySlugIn(normalizedSlugs);

    // Mapear a DTO
    return categories.stream()
        .map(c -> new CategoryWithProductsResponse(
            c.getId(),
            c.getName(),
            c.getDescription(),
            c.getSlug(),
            c.getImage(),
            c.getProducts().stream()   // List<Product> directo de Category
                .map(p -> new ProductResponse(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getStock(),
                    p.getDescription(),
                    p.getImages().stream()
                        .map(ProductImage::getImageUrl)
                        .filter(Objects::nonNull)
                        .toList(),
                    new CategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.getDescription(),
                        c.getSlug(),
                        c.getImage()
                    ),
                    p.getProductCollections().stream()
                        .map(col -> new CollectionResponse(
                            col.getCollection().getId(),
                            col.getCollection().getName(),
                            col.getCollection().getDescription(),
                            col.getCollection().getSlug(),
                            col.getCollection().getImage()
                        ))
                        .toList()
                ))
                .toList()
        ))
        .toList();
  }


  @Override
  public CategoryResponse guardar(CategoryRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Category category = new Category();
    category.setName(request.name());
    category.setDescription(request.description());
    category.setSlug(normalizedSlug);
    category.setImage(request.image());
    return mapper.toResponse(repository.save(category));
  }

  @Override
  public CategoryResponse actualizar(Long id, CategoryRequest request){
    Category category = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    category.setName(request.name());
    category.setDescription(request.description());
    category.setImage(request.image());

    return mapper.toResponse(repository.save(category));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}