package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.model.ProductsInfo;

import java.util.List;

@Repository
public interface ProductsInfoRepository extends JpaRepository<ProductsInfo, Long> {
    //Means that this product pack is in some shop
    public List<ProductsInfo> findAllByAtStorageFalse();
    public List<ProductsInfo> findByProductIdAndAtStorageFalse(long id);
    //Means that this product pack is at storage, but not in shop
    public List<ProductsInfo> findAllByAtStorageTrueAndShopIsNull();
    public List<ProductsInfo> findByProductIdAndAtStorageTrueAndShopIsNull(long id);
    //Means that this product pack is at storage, but is in deliver to shop
    public List<ProductsInfo> findAllByAtStorageTrueAndShopNotNull();
    public List<ProductsInfo> findByProductIdAndAtStorageTrueAndShopNotNull(long id);
    //Find products in current shop by id
    public List<ProductsInfo> findAllByShopId (long id);

    //Can be added new
}
