package com.deymerhmarker.persistence;

import com.deymerhmarker.domain.Purchase;
import com.deymerhmarker.domain.repository.PurchaseRepository;
import com.deymerhmarker.persistence.crud.CompraCrudRepository;
import com.deymerhmarker.persistence.entity.Compra;
import com.deymerhmarker.persistence.entity.ComprasProducto;
import com.deymerhmarker.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ComprasRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        return purchaseMapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map((compras) -> purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);
        compra.getProductos().forEach((producto) -> producto.setCompra(compra));
        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
}
