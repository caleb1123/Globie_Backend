package com.product.globie.service;

import com.product.globie.entity.ShippingAddress;
import com.product.globie.payload.DTO.ShippingAddressDTO;
import com.product.globie.payload.request.ShippingAddressRequest;

import java.util.List;

public interface ShippingAddressService {
    List<ShippingAddressDTO> getAllShippingAddressOfUser();

    ShippingAddressDTO createShippingAddress(ShippingAddressRequest shippingAddressRequest);

    ShippingAddressDTO updateShippingAddress(ShippingAddressRequest shippingAddressRequest, int sID);

    void deleteShippingAddress(int sId);
}
