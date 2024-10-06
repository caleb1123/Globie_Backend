package com.product.globie.service.impl;

import com.product.globie.config.Util;
import com.product.globie.entity.ShippingAddress;
import com.product.globie.payload.DTO.ReportDTO;
import com.product.globie.payload.DTO.ShippingAddressDTO;
import com.product.globie.payload.request.ShippingAddressRequest;
import com.product.globie.repository.ShippingAddressRepository;
import com.product.globie.service.ShippingAddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Util util;


    @Override
    public List<ShippingAddressDTO> getAllShippingAddressOfUser() {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findShippingAddressByUser(util.getUserFromAuthentication().getUserId());

        List<ShippingAddressDTO> shippingAddressDTOS = shippingAddresses.stream()
                .map(shippingAddress -> {
                    ShippingAddressDTO shippingAddressDTO = mapper.map(shippingAddress, ShippingAddressDTO.class);

                    if (shippingAddress.getUser() != null) {
                        shippingAddressDTO.setUserId(shippingAddress.getUser().getUserId());
                    }

                    return shippingAddressDTO;
                })
                .collect(Collectors.toList());

        return shippingAddressDTOS.isEmpty() ? null : shippingAddressDTOS;
    }

    @Override
    public ShippingAddressDTO createShippingAddress(ShippingAddressRequest shippingAddressRequest) {
        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.setAddress(shippingAddressRequest.getAddress());
        shippingAddress.setPhone(shippingAddressRequest.getPhone());
        shippingAddress.setUser(util.getUserFromAuthentication());

        if (shippingAddressRequest.isStatus()) {
            List<ShippingAddress> shippingAddresses = shippingAddressRepository
                    .findShippingAddressByUser(util.getUserFromAuthentication().getUserId());

            for (ShippingAddress address : shippingAddresses) {
                if (!address.equals(shippingAddress)) {
                    address.setStatus(false);
                    shippingAddressRepository.save(address);
                }
            }

            shippingAddress.setStatus(true);
        } else {
            shippingAddress.setStatus(false);
        }

        ShippingAddress savedAddress = shippingAddressRepository.save(shippingAddress);

        ShippingAddressDTO shippingAddressDTO = mapper.map(savedAddress, ShippingAddressDTO.class);
        shippingAddressDTO.setUserId(savedAddress.getUser().getUserId());

        return shippingAddressDTO;
    }

    @Override
    public ShippingAddressDTO updateShippingAddress(ShippingAddressRequest shippingAddressRequest, int sID) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(sID)
                .orElseThrow(() -> new RuntimeException("Shipping address not found with Id: " + sID));
        String homeAddress = shippingAddress.getAddress();
        String phone = shippingAddress.getPhone();
        // Kiểm tra và cập nhật address nếu request không null
        if (shippingAddressRequest.getAddress() != null && !shippingAddressRequest.getAddress().trim().isEmpty()) {
            shippingAddress.setAddress(shippingAddressRequest.getAddress());
        }

        // Kiểm tra và cập nhật phone nếu request không null
        if (shippingAddressRequest.getPhone() != null && !shippingAddressRequest.getPhone().trim().isEmpty()) {
            shippingAddress.setPhone(shippingAddressRequest.getPhone());
        }

        if (shippingAddressRequest.isStatus()) {
            List<ShippingAddress> shippingAddresses = shippingAddressRepository
                    .findShippingAddressByUser(shippingAddress.getUser().getUserId());

            for (ShippingAddress address : shippingAddresses) {
                if (!address.equals(shippingAddress)) {
                    address.setStatus(false);
                    shippingAddressRepository.save(address);
                }
            }

            shippingAddress.setStatus(true);
        } else {
            shippingAddress.setStatus(false);
        }

        ShippingAddress savedAddress = shippingAddressRepository.save(shippingAddress);

        ShippingAddressDTO shippingAddressDTO = mapper.map(savedAddress, ShippingAddressDTO.class);
        shippingAddressDTO.setUserId(savedAddress.getUser().getUserId());

        return shippingAddressDTO;
    }

    @Override
    public void deleteShippingAddress(int sId) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(sId)
                .orElseThrow(() -> new RuntimeException("Shipping address not found with Id: " + sId));

        shippingAddressRepository.delete(shippingAddress);
    }
}
