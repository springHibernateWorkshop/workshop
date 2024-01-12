package spring.workshop.expenses.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Shop;

@Aspect
@Component
public class ShopServiceAspect {

    @Before(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(shop)")
    public void beforeAddNewShop(JoinPoint joinPoint, Shop shop) {
        System.out.println("Before method:" + joinPoint.getSignature());

        System.out.println("Creating Shop with name - " + shop.getName() + " and address - " + shop.getAddress());
    }

    @After(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(shop)")
    public void afterAddNewShop(JoinPoint joinPoint, Shop shop) {
        System.out.println("After method:" + joinPoint.getSignature());

        System.out.println(
                "Successfully created Shop with name - " + shop.getName() + " and address - " + shop.getAddress());
    }

    @Before(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(shop, id)")
    public void beforeUpdateShop(JoinPoint joinPoint, Shop shop, Long id) {
        System.out.println("Before method:" + joinPoint.getSignature());

        System.out.println("updating Shop with id - " + id);
    }

    @After(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(shop, id)")
    public void afterUpdateShop(JoinPoint joinPoint, Shop shop, Long id) {
        System.out.println("After method:" + joinPoint.getSignature());

        System.out.println(
                "Successfully updated Shop with id " + id + " and new Name - " + shop.getName() + " and new address - "
                        + shop.getAddress());
    }

    @Before(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(id)")
    public void beforeDeleteShop(JoinPoint joinPoint, Long id) {
        System.out.println("Before method:" + joinPoint.getSignature());

        System.out.println("deleting Shop with id - " + id);
    }

    @After(value = "execution(* spring.workshop.expenses.services.ShopService.*(..)) and args(id)")
    public void afterdeleteShop(JoinPoint joinPoint, Long id) {
        System.out.println("After method:" + joinPoint.getSignature());

        System.out.println(
                "Successfully deleted Shop with id " + id);
    }

}
