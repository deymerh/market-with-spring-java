package com.deymerhmarker.persistence;

import com.deymerhmarker.domain.Product;
import com.deymerhmarker.domain.repository.ProductRepository;
import com.deymerhmarker.persistence.crud.ProductoCrudRepository;
import com.deymerhmarker.persistence.entity.Producto;
import com.deymerhmarker.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Le indicamos a la clase que es la encargada de interactuar con la base de datos
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScaresProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map((product) -> productMapper.toProducts(product));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map((product) -> (productMapper.toProduct(product)));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }

    //public List<Producto> getByCategoria(int idCategoria){
    //    return productoCrudRepository.findByIdCategoriaOrderByAsc(idCategoria);
    //}

    //public Optional<List<Producto>> getEscasos(int cantidad){
    //    return productoCrudRepository.findByCantidadStockLessThanAnsEstado(cantidad, true);
    //}

    //public Optional<Producto> getProducto(int idProducto){
    //    return productoCrudRepository.findById(idProducto);
    //}

    //public Producto save(Producto producto){
    //    return productoCrudRepository.save(producto);
    //}
}
