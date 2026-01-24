package com.springboot_sa_ha1.modules.products.service;

import com.springboot_sa_ha1.modules.categories.model.Category;
import com.springboot_sa_ha1.modules.categories.repository.CategoryRepository;
import com.springboot_sa_ha1.modules.collections.dto.CollectionInputResponse;
import com.springboot_sa_ha1.modules.collections.dto.CollectionResponse;
import com.springboot_sa_ha1.modules.collections.model.Collection;
import com.springboot_sa_ha1.modules.collections.repository.CollectionRepository;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollection;
import com.springboot_sa_ha1.modules.product_collections.model.ProductCollectionId;
import com.springboot_sa_ha1.modules.product_collections.repository.ProductCollectionRepository;
import com.springboot_sa_ha1.modules.productimages.model.ProductImage;
import com.springboot_sa_ha1.modules.products.dto.ProductRequest;
import com.springboot_sa_ha1.modules.products.dto.ProductResponse;
import com.springboot_sa_ha1.modules.products.mapper.ProductMapper;
import com.springboot_sa_ha1.modules.products.model.Product;
import com.springboot_sa_ha1.modules.products.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final CollectionRepository collectionRepository;
  private final ProductCollectionRepository productCollectionRepository;
  private final ProductMapper mapper;

  public ProductServiceImp(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      CollectionRepository collectionRepository,
      ProductCollectionRepository productCollectionRepository,
      ProductMapper mapper
  ) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.collectionRepository = collectionRepository;
    this.productCollectionRepository = productCollectionRepository;
    this.mapper = mapper;
  }

  @Override
  public List<ProductResponse> searchByTerm(String term) {
    return productRepository.searchByTerm(term).stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> listarPorCategoriaSlug(String slug) {
    return productRepository.findByCategorySlug(slug)
        .stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public List<ProductResponse> listarPorColeccionSlug(String slug) {
    return productRepository.findByCollectionSlug(slug)
        .stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public List<ProductResponse> listarTodos() {
    return productRepository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse obtenerPorId(Long id) {
    return productRepository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
  }

  @Override
  @Transactional
  public ProductResponse guardar(ProductRequest request) {

    // 🔹 Obtener categoría
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // 🔹 Crear producto
    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // 🔹 Imágenes
    if (request.images() != null) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    // 🔹 Colecciones
    if (request.collections() != null && !request.collections().isEmpty()) {
      for (CollectionInputResponse colResp : request.collections()) {
        Collection collection = collectionRepository.findById(colResp.id())
            .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colResp.id()));

        ProductCollection pc = new ProductCollection();

        // ID compuesto
        ProductCollectionId pcId = new ProductCollectionId();
        pcId.setProductId(null); // no asignar aún
        pcId.setCollectionId(collection.getId());
        pc.setId(pcId);

        pc.setProduct(product);
        pc.setCollection(collection);

        // 🔹 solo agregar al producto, NO llamar a productCollectionRepository.save(pc)
        product.getProductCollections().add(pc);
      }
    }

    // 🔹 Guardar producto y todas las relaciones por cascade
    Product savedProduct = productRepository.save(product);

    return mapper.toResponse(savedProduct);
  }



  @Override
  @Transactional
  public ProductResponse actualizar(Long id, ProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    // Actualizar campos básicos
    Category category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStock(request.stock());
    product.setDescription(request.description());
    product.setCategory(category);

    // Limpiar y agregar imágenes nuevas
    product.getImages().clear();
    if (request.images() != null && !request.images().isEmpty()) {
      for (String url : request.images()) {
        ProductImage image = new ProductImage();
        image.setImageUrl(url);
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    // Limpiar y agregar colecciones nuevas
    product.getProductCollections().clear();
    if (request.collections() != null && !request.collections().isEmpty()) {
      for (CollectionInputResponse colInput : request.collections()) {

        Collection collection;

        if (colInput.id() != null) {
          collection = collectionRepository.findById(colInput.id())
              .orElseThrow(() -> new RuntimeException("Colección no encontrada: " + colInput.id()));
        } else if (colInput.name() != null && !colInput.name().isBlank()) {
          collection = collectionRepository.findByName(colInput.name())
              .orElseGet(() -> {
                Collection c = new Collection();
                c.setName(colInput.name());
                return collectionRepository.save(c);
              });
        } else {
          continue;
        }

        // Crear relación
        ProductCollection pc = new ProductCollection();
        ProductCollectionId pcId = new ProductCollectionId(product.getId(), collection.getId());
        pc.setId(pcId);
        pc.setProduct(product);
        pc.setCollection(collection);
        product.getProductCollections().add(pc);
      }
    }

    return mapper.toResponse(productRepository.save(product));
  }

  @Override
  public void eliminar(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    productRepository.delete(product);
  }
}