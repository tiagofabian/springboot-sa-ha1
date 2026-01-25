package com.springboot_sa_ha1.modules.collections.service;

import com.springboot_sa_ha1.modules.categories.dto.CategoryResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionRequest;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionWithProductsResponse;
import com.springboot_sa_ha1.modules.collections.mapper.CollectionMapper;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.collections.service.CollectionService;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.model.Product;
import org.springframework.stereotype.Service;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImp implements CollectionService {

  private final CollectionRepository repository;
  private final CollectionMapper mapper;

  public CollectionServiceImp(CollectionRepository repository, CollectionMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<CollectionResponse> listarTodos(){
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }


  @Override
  public CollectionResponse obtenerPorId(Long id){
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
  }

  @Override
  public List<CollectionResponse> listarColeccionesPorSlug(List<String> slugs) {

    // 🔹 Normalización (misma regla que el otro método)
    List<String> normalizedSlugs = slugs.stream()
        .map(String::toLowerCase)
        .map(String::trim)
        .map(s -> s.replace("-", "_"))
        .toList();

    List<Collection> collections =
        repository.findBySlugIn(normalizedSlugs);

    if (collections.isEmpty()) {
      throw new RuntimeException("No se encontraron colecciones");
    }

    return collections.stream()
        .map(mapper::toResponse)
        .toList();
  }


  public List<CollectionWithProductsResponse> listarColeccionesConProductosPorSlug(List<String> slugs) {

    // ✅ Normalización de slugs (problema real ya identificado)
    List<String> normalizedSlugs = slugs.stream()
        .map(String::toLowerCase)
        .map(String::trim)
        .map(s -> s.replace("-", "_"))
        .toList();

    List<Collection> collections =
        repository.findAllBySlugsWithProducts(normalizedSlugs);

    return collections.stream()
        .map(c -> new CollectionWithProductsResponse(
            c.getId(),
            c.getName(),
            c.getDescription(),
            c.getSlug(),
            c.getImage(),
            c.getProductCollections().stream()
                .map(pc -> {
                  Product p = pc.getProduct();

                  return new ProductResponse(
                      p.getId(),
                      p.getName(),
                      p.getPrice(),
                      p.getStock(),
                      p.getDescription(),
                      p.getImages().stream()
                          .map(ProductImage::getImageUrl)
                          .filter(Objects::nonNull)
                          .toList(),
                      p.getCategory() != null
                          ? new CategoryResponse(
                          p.getCategory().getId(),
                          p.getCategory().getName(),
                          p.getCategory().getDescription(),
                          p.getCategory().getSlug(),
                          p.getCategory().getImage()
                      )
                          : null,
                      p.getProductCollections().stream()
                          .map(col -> new CollectionResponse(
                              col.getCollection().getId(),
                              col.getCollection().getName(),
                              col.getCollection().getDescription(),
                              col.getCollection().getSlug(),
                              col.getCollection().getImage()
                          ))
                          .toList()
                  );
                })
                .toList()
        ))
        .toList();
  }




  @Override
  public CollectionResponse guardar(CollectionRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Collection collection = new Collection();
    collection.setName(request.name());
    collection.setDescription(request.description());
    collection.setSlug(normalizedSlug);
    collection.setImage(request.image());

    return mapper.toResponse(repository.save(collection));
  }

  @Override
  public CollectionResponse actualizar(Long id, CollectionRequest request){
    // Generar slug automáticamente
    String normalizedSlug = request.name()
        .trim()
        .toLowerCase()
        .replace(" ", "_");
    Collection collection = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
    collection.setId(id);
    collection.setName(request.name());
    collection.setDescription(request.description());
    collection.setSlug(normalizedSlug);
    collection.setImage(request.image());

    return mapper.toResponse(repository.save(collection));
  }

  @Override
  public void eliminar(Long id){
    repository.deleteById(id);
  }
}
