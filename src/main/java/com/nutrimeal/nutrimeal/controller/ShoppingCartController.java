package com.nutrimeal.nutrimeal.controller;

import com.nutrimeal.nutrimeal.model.User;
import com.nutrimeal.nutrimeal.service.OrderBasketService;
import com.nutrimeal.nutrimeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final UserService userService;
    private final OrderBasketService orderBasketService;

    @PostMapping("/basket/add/{cid}")
    public String addProductToBasket(@PathVariable("cid") Integer comboId,
                                     Principal principal) {
        if (principal == null) {
            return "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng";
        }
        User user;
        if (principal instanceof OAuth2AuthenticationToken && principal != null) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            OAuth2User oauthUser = token.getPrincipal();
            user = userService.findByEmail(oauthUser.getAttribute("email"));
        } else {
            user = userService.findByUsername(principal.getName());
        }

        if (user == null) return "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng";

        Integer addedQuantity = orderBasketService.addComboToBasket(comboId, user);

        return addedQuantity > 1 ? addedQuantity + "sản phẩm đã được thêm trong giỏ hàng của bạn, vui lòng kiểm tra giỏ hàng"
                : addedQuantity + "sản phẩm này đã được thêm mới vào giỏ hàng của bạn";
    }

    @PostMapping("/basket/update/{cid}/{qty}")
    public String updateQuantity(@PathVariable("cid") Integer comboId,
                                 @PathVariable("qty") Integer quantity,
                                 Principal principal) {
        if (principal == null) {
            return "Bạn cần đăng nhập để cập nhật số lượng";
        }
        User user;
        if (principal instanceof OAuth2AuthenticationToken && principal != null) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            OAuth2User oauthUser = token.getPrincipal();
            user = userService.findByEmail(oauthUser.getAttribute("email"));
        } else {
            user = userService.findByUsername(principal.getName());
        }

        if (user == null) return "Bạn cần đăng nhập để cập nhật số lượng";

        int subtotal = orderBasketService.updateQuantity(quantity, comboId, user);

        return String.valueOf(subtotal);
    }

    @PostMapping("/basket/remove/{cid}")
    public String removeProductFromBasket(@PathVariable("cid") Integer comboId,
                                          Principal principal) {
        if (principal == null) {
            return "Bạn cần đăng nhập để xóa sản phẩm";
        }
        User user;
        if (principal instanceof OAuth2AuthenticationToken && principal != null) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            OAuth2User oauthUser = token.getPrincipal();
            user = userService.findByEmail(oauthUser.getAttribute("email"));
        } else {
            user = userService.findByUsername(principal.getName());
        }

        if (user == null) return "Bạn cần đăng nhập để xóa sản phẩm";

        orderBasketService.removeComboFromBasket(comboId, user);

        return "Sản phẩm đã được xóa khỏi giỏ hàng của bạn.";
    }
}
