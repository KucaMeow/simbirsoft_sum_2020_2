package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.model.ProductsInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsInfoRepository extends JpaRepository<ProductsInfo, Long> {
    //Means that this product pack is in some shop
    List<ProductsInfo> findAllByAtStorageFalse();
    List<ProductsInfo> findByProductIdAndAtStorageFalse(long id);
    //Means that this product pack is at storage, but not in shop
    List<ProductsInfo> findAllByAtStorageTrueAndShopIsNull();
    List<ProductsInfo> findByProductIdAndAtStorageTrueAndShopIsNull(long id);
    //Means that this product pack is at storage, but is in deliver to shop
    List<ProductsInfo> findAllByAtStorageTrueAndShopNotNull();
    List<ProductsInfo> findByProductIdAndAtStorageTrueAndShopNotNull(long id);
    //Find products in current shop by id
    List<ProductsInfo> findAllByShopId (long id);

    Optional<ProductsInfo> findByProductIdAndShopIdAndAtStorage(Long product_id, Long shop_id, boolean atStorage);

    //Can be added new
}
